package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Class for the placeholder page for listing ingredients
 */
public class IngredientScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_ingredients);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int button_id = item.getItemId();
            if (button_id == R.id.bottom_meals) {
                startActivity(new Intent(IngredientScreen.this, InputMealScreen.class));
                return true;
            } else if (button_id == R.id.bottom_recipes) {
                startActivity(new Intent(IngredientScreen.this, RecipeScreen.class));
                return true;
            } else if (button_id == R.id.bottom_shopping) {
                startActivity(new Intent(IngredientScreen.this, ShoppingListScreen.class));
                return true;
            } else if (button_id == R.id.bottom_ingredients) {
                return true;
            } else {
                return false;
            }
        });
    }
}
