package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * to code or not to code.  I am bored.
 */
public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        //login and create account buttons
        Button accountButton = findViewById(R.id.accountCreationButton);
        accountButton.setOnClickListener(item -> {
            startActivity(new Intent(HomeScreen.this, AccountCreationScreen.class));
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(item -> {
            startActivity(new Intent(HomeScreen.this, LoginScreen.class));
        });

    }
}