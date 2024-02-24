package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * twenty one pilots is releasing new music on the 29th apparently so that is hype
 */
public class ShoppingListScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_shopping);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int button_id = item.getItemId();
            if (button_id == R.id.bottom_meals) {
                startActivity(new Intent(ShoppingListScreen.this, InputMealScreen.class));
                return true;
            } else if (button_id == R.id.bottom_recipes) {
                startActivity(new Intent(ShoppingListScreen.this, RecipeScreen.class));
                return true;
            } else if (button_id == R.id.bottom_shopping) {
                return true;
            } else if (button_id == R.id.bottom_ingredients) {
                startActivity(new Intent(ShoppingListScreen.this, IngredientScreen.class));
                return true;
            } else {
                return false;
            }
        });
    }
}
