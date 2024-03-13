package com.example.a2340project;

import android.content.Intent;
import android.health.connect.datatypes.MealType;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.anychart.charts.Cartesian;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

<<<<<<< HEAD

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;
/**
 * Class for the placeholder page for Inputting meals
 */
=======
>>>>>>> 842eab04e1a3d85ca0923f3bab8f3d3465b21c43
public class InputMealScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private EditText mealInputText;
    private EditText calorieInputText;
    private Button addMealButton;
    private InputMealViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_input_meal);

        //generate data structure buttons
        Button dailyIntakeDailyGoal = findViewById(R.id.dailyIntakeDailyGoal);
        Button dailyIntakeOverMonth = findViewById(R.id.dailyIntakeOverMonth);
        viewModel = InputMealViewModel.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        dailyIntakeDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);

                Cartesian c = AnyChart.cartesian();

                List<DataEntry> list = new ArrayList<>();
                DatabaseReference userR = mDatabase.child().toString();
                list.add(new ValueDataEntry());
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
                return false;
            }
        });

        // add meal button WIP
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meal = mealInputText.getText().toString();
                String calories = calorieInputText.getText().toString();
                if (meal.contains("\\S+") || calories.contains("\\S+")
                        || meal.equals("") || calories.equals("")) {
                    meal = "";
                    calories = "";
                    error.setVisibility(View.VISIBLE);
                } else {
                    // logic for adding a meal and calories given the user is signed in
                    if (currentUser != null) {
                        String userID = currentUser.getUid();
                        DatabaseReference userRef = mDatabase.child("users").child(userID);
                        userRef.child("meal").setValue(meal);
                        userRef.child("calories").setValue(calories);
                    }
                }
            }
        });

        String userID = currentUser.getUid();
        DatabaseReference userRef = mDatabase.child("users").child(userID);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int feet = Integer.parseInt(snapshot.child("feet").getValue().toString());
                int inches = Integer.parseInt(snapshot.child("inches").getValue().toString());
                int weight = Integer.parseInt(snapshot.child("pounds").getValue().toString());
                String gender = snapshot.child("gender").getValue().toString();
                viewModel.updateData(feet, inches, weight, gender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
