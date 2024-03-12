package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;;

public class ProfileScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        Button saveButton = findViewById(R.id.saveButton);
        EditText heightFeet = findViewById(R.id.feetEditTextNumber);
        EditText heightInches = findViewById(R.id.inchesEditTextNumber);
        EditText weight = findViewById(R.id.weightEditTextNumber);
        Spinner genderSpinner = findViewById(R.id.genderSpinner);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int feet = Integer.parseInt(heightFeet.getText().toString());
                int inches = Integer.parseInt(heightInches.getText().toString());
                int pounds = Integer.parseInt(weight.getText().toString());
                String gender = genderSpinner.getSelectedItem().toString();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                startActivity(new Intent(ProfileScreen.this, InputMealScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                startActivity(new Intent(ProfileScreen.this, RecipeScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(ProfileScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                startActivity(new Intent(ProfileScreen.this, ShoppingListScreen.class));
                return true;
            } else {
                return buttonID == R.id.bottom_profile;
            }
        });
    }


}
