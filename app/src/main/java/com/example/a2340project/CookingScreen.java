package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CookingScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String userId;

    private DatabaseReference mDatabase;

    private String recipeName;

    private Map<String, Double> ingredientsMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);
        Bundle extras = getIntent().getExtras();
        String ingredientsStr = null;
        if (extras != null) {
            ingredientsStr = extras.getString("ingredients");
            ingredientsStr = ingredientsStr.substring(1, ingredientsStr.length() - 1);
            recipeName = extras.getString("recipe");
        }
        Button backButton = findViewById(R.id.backToRecipe);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(CookingScreen.this, RecipeScreen.class));
        });

        ListView ingredients = findViewById(R.id.listView2);
        ArrayList<String> viewArray = new
                ArrayList<String>(Arrays.asList(ingredientsStr.split(",")));
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, viewArray);
        ingredients.setAdapter(adapter);
        Button cookButton = findViewById(R.id.cook);
        cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Double> ingMap = new HashMap<>();
                for (String strings : viewArray) {
                    ArrayList<String> ingList = new
                            ArrayList<String>(Arrays.asList(strings.split("-")));
                    Log.v("INGLIST", ingList.get(0));
                    ingMap.put(ingList.get(0).replace(" ", ""), Double.parseDouble(ingList.get(1)));
                }
                Recipe currRecipe = new Recipe(recipeName, ingMap);
                if (Calendar.HOUR >= 15) {
                    onCookButtonClick(currRecipe, "dinner");
                } else {
                    onCookButtonClick(currRecipe, "breakfast");
                }
                startActivity(new Intent(CookingScreen.this, RecipeScreen.class));
            }
        });
    }
    private void onCookButtonClick(Recipe recipe, String mealType) {
        MealPrep meal = MealFactory.createMeal(recipe, mealType);
        meal.cook();
    }
}
