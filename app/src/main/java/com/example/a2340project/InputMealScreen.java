package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Class for the placeholder page for Inputting meals
 */
public class InputMealScreen extends AppCompatActivity {
    private EditText mealInputText;
    private EditText calorieInputText;
    private Button addMealButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);
        //generate data structure buttons
        Button dailyIntakeDailyGoal = findViewById(R.id.dailyIntakeDailyGoal);
        Button dailyIntakeOverMonth = findViewById(R.id.dailyIntakeOverMonth);
        dailyIntakeDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        dailyIntakeOverMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        mealInputText = findViewById(R.id.inputMealName);
        calorieInputText = findViewById(R.id.inputCalorieEstimate);
        TextView error = findViewById(R.id.Error);


        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_meals);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                startActivity(new Intent(InputMealScreen.this, RecipeScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                startActivity(new Intent(InputMealScreen.this, ShoppingListScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(InputMealScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_profile) {
                startActivity(new Intent(InputMealScreen.this, ProfileScreen.class));
                return true;
            } else {
                return buttonID == R.id.bottom_meals;
            }
        });

        // add meal button WIP
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meal = mealInputText.getText().toString();
                String calories = calorieInputText.getText().toString();
                if (meal == null || calories == null
                        || meal.contains("\\S+") || calories.contains("\\S+")
                        || meal.equals("") || calories.equals("")) {
                    meal = "";
                    calories = "";
                    error.setVisibility(View.VISIBLE);
                } else {
                    // add to database
                }
            }
        });
    }
}
