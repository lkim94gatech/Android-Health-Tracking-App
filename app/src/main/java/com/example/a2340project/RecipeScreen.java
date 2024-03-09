package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Class for the placeholder page for listing recipes
 */
public class RecipeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_recipes);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                startActivity(new Intent(RecipeScreen.this, InputMealScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                startActivity(new Intent(RecipeScreen.this, ShoppingListScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(RecipeScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_profile) {
                startActivity(new Intent(RecipeScreen.this, ProfileScreen.class));
                return true;
            } else {
                return buttonID == R.id.bottom_recipes;
            }
        });
    }
}
