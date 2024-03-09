package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * to code or not to code.  I am bored.
 */
public class HomeScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(HomeScreen.this, LoginScreen.class));
        }

        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                startActivity(new Intent(HomeScreen.this, InputMealScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                startActivity(new Intent(HomeScreen.this, RecipeScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                startActivity(new Intent(HomeScreen.this, ShoppingListScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(HomeScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_profile) {
                startActivity(new Intent(HomeScreen.this, ProfileScreen.class));
                return true;
            } else {
                return false;
            }
        });
    }
}