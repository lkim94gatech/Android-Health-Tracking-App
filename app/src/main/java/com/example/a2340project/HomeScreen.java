package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        if(currentUser == null){
            startActivity(new Intent(HomeScreen.this, LoginScreen.class));
        }
        //log out button
        Button logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeScreen.this, LoginScreen.class));
            }
        });

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
    }
}