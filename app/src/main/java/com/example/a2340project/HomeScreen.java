package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
=======
import com.google.android.material.bottomnavigation.BottomNavigationView;
>>>>>>> 627c65982d5ca83de98cd7a2d35229168a50249c

/**
 * to code or not to code.  I am bored.
 */
public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

<<<<<<< HEAD
=======
        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int button_id = item.getItemId();
            if (button_id == R.id.bottom_meals) {
                startActivity(new Intent(HomeScreen.this, InputMealScreen.class));
                return true;
            } else if (button_id == R.id.bottom_recipes) {
                startActivity(new Intent(HomeScreen.this, RecipeScreen.class));
                return true;
            } else if (button_id == R.id.bottom_shopping) {
                startActivity(new Intent(HomeScreen.this, ShoppingListScreen.class));
                return true;
            } else if (button_id == R.id.bottom_ingredients) {
                startActivity(new Intent(HomeScreen.this, IngredientScreen.class));
                return true;
            } else {
                return false;
            }
        });
>>>>>>> 627c65982d5ca83de98cd7a2d35229168a50249c
    }
}