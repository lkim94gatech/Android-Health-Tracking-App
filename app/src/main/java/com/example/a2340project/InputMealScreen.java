package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.anychart.chart.common.dataentry.BoxDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Box;
import com.anychart.core.lineargauge.pointers.Bar;
import com.anychart.math.CoordinateObject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;

import org.checkerframework.checker.units.qual.A;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Class for the placeholder page for Inputting meals
 */

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
        TextView heightText = findViewById(R.id.tvUserHeight);
        TextView weightText = findViewById(R.id.tvUserWeight);
        TextView genderText = findViewById(R.id.tvUserGender);
        addMealButton = findViewById(R.id.addMealButton);
        viewModel = InputMealViewModel.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        dailyIntakeDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);


                List<DataEntry> list = new ArrayList<>();
                String meal = mealInputText.getText().toString();
                String calories = calorieInputText.getText().toString();

                list.add(new ValueDataEntry("honen", Integer.parseInt(calories)));
                list.add(new ValueDataEntry("" + meal + "", Integer.parseInt(calories)));
            }
        });

        dailyIntakeOverMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);

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
                if (snapshot.child("feet").getValue() != null
                        || snapshot.child("inches").getValue() != null
                        || snapshot.child("pounds").getValue() != null
                        || snapshot.child("gender").getValue() != null) {
                    int feet = Integer.parseInt(snapshot.child("feet").getValue().toString());
                    int inches = Integer.parseInt(snapshot.child("inches").getValue().toString());
                    int weight = Integer.parseInt(snapshot.child("pounds").getValue().toString());
                    String gender = snapshot.child("gender").getValue().toString();
                    viewModel.updateData(feet, inches, weight, gender);
                    String height = String.format("%d\'%d\"", feet, inches);
                    heightText.setText("Height: " + height);
                    weightText.setText("Weight: " + weight);
                    genderText.setText("Gender: " + gender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
