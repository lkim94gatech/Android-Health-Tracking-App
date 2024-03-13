package com.example.a2340project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
import java.util.Calendar;

=======
>>>>>>> 8edd78854e0754011bbef7250ebc7edfe44185bc
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD

import com.anychart.chart.common.dataentry.BoxDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Box;
import com.anychart.core.lineargauge.pointers.Bar;
import com.anychart.math.CoordinateObject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
=======
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
>>>>>>> 8edd78854e0754011bbef7250ebc7edfe44185bc
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.anychart.chart.common.dataentry.ValueDataEntry;

<<<<<<< HEAD
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;

import org.checkerframework.checker.units.qual.A;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
=======
//import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
//import java.util.Date;
>>>>>>> 8edd78854e0754011bbef7250ebc7edfe44185bc
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
    private int dailyCalorieGoal;
    private int dailyCalorieIntake;
    private int totalMonth;

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
        TextView dailyGoal = findViewById(R.id.tvDailyGoal);
        addMealButton = findViewById(R.id.addMealButton);
        viewModel = InputMealViewModel.getInstance();
        mealInputText = findViewById(R.id.inputMealName);
        calorieInputText = findViewById(R.id.inputCalorieEstimate);
        TextView dailyCalories = findViewById(R.id.tvDailyCalorieIntake);
        TextView error = findViewById(R.id.Error);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        dailyIntakeDailyGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);


                List<DataEntry> list = new ArrayList<>();
                String meal = mealInputText.getText().toString();
                String calories = calorieInputText.getText().toString();

                list.add(new ValueDataEntry("honen", Integer.parseInt(calories)));
                list.add(new ValueDataEntry("" + meal + "", Integer.parseInt(calories)));
=======
                BarChart bar = findViewById(R.id.chart);
                bar.getAxisRight().setDrawLabels(false);
                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(1, (float) dailyCalorieGoal));
                entries.add(new BarEntry(3, (float) dailyCalorieIntake));
                YAxis yAxis = bar.getAxisLeft();
                yAxis.setAxisMinimum(0f);
                yAxis.setAxisMaximum(Math.max(dailyCalorieGoal, dailyCalorieIntake) + 200f);
                yAxis.setAxisLineWidth(2f);
                yAxis.setAxisLineColor(Color.BLACK);
                yAxis.setLabelCount(5);
                BarDataSet dataSet = new BarDataSet(entries, "Calories");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                BarData barData = new BarData(dataSet);
                bar.setData(barData);
                bar.getDescription().setEnabled(false);
                bar.invalidate();
                List<String> list = Arrays.asList("Daily Calorie Goal", "Daily Calorie Intake");
                bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(list));
                bar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                bar.getXAxis().setGranularity(1f);
                bar.getXAxis().setGranularityEnabled(true);
>>>>>>> 8edd78854e0754011bbef7250ebc7edfe44185bc
            }
        });
        dailyIntakeOverMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);

=======
                BarChart bar = findViewById(R.id.graph);
                String userID = currentUser.getUid();
                DatabaseReference userRef = mDatabase.child("users").child(userID);
                DatabaseReference userMealsRef = userRef.child("meal");
                Calendar cal = Calendar.getInstance();
                long endOfMonth = cal.getTimeInMillis();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                // Resetting time to start of the day for accurate comparison
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.DAY_OF_MONTH, 0);
                long startOfMonth = cal.getTimeInMillis();
                userMealsRef.orderByChild("date/time")
                        .startAt(startOfMonth)
                        .endAt(endOfMonth)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                totalMonth = 0;
                                for (DataSnapshot data: snapshot.getChildren()) {
                                    Meal meal = data.getValue(Meal.class);
                                    if (meal != null) {
                                        totalMonth += meal.getCalories();
                                    }
                                }
                                bar.getAxisRight().setDrawLabels(false);
                                ArrayList<BarEntry> entries = new ArrayList<>();
                                entries.add(new BarEntry(1, (float) (dailyCalorieGoal * day)));
                                entries.add(new BarEntry(3, (float) totalMonth));
                                YAxis yAxis = bar.getAxisLeft();
                                yAxis.setAxisMinimum(0f);
                                yAxis.setAxisMaximum(
                                        Math.max((dailyCalorieGoal * day), totalMonth) + 200f);
                                yAxis.setAxisLineWidth(2f);
                                yAxis.setAxisLineColor(Color.BLACK);
                                yAxis.setLabelCount(5);
                                BarDataSet dataSet = new BarDataSet(entries, "Calories");
                                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                BarData barData = new BarData(dataSet);
                                bar.setData(barData);
                                bar.getDescription().setEnabled(false);
                                bar.invalidate();
                                List<String> list = Arrays.asList(
                                        "Monthly Calorie Goal", "Monthly Calorie Intake");
                                bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(list));
                                bar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                                bar.getXAxis().setGranularity(1f);
                                bar.getXAxis().setGranularityEnabled(true);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
>>>>>>> 8edd78854e0754011bbef7250ebc7edfe44185bc
            }
        });
        mealInputText = findViewById(R.id.inputMealName);
        calorieInputText = findViewById(R.id.inputCalorieEstimate);
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
                        int calorie = Integer.parseInt(calories);
                        userRef.child("meal").push().setValue(new Meal(meal, calorie));
                        mealInputText.setText("");
                        calorieInputText.setText("");
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
                    setDailyCalorieGoal(viewModel, dailyGoal);
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
        DatabaseReference userMealsRef = userRef.child("meal");
        Calendar cal = Calendar.getInstance();
        // Resetting time to start of the day for accurate comparison
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startOfDay = cal.getTimeInMillis();
        cal.add(Calendar.DATE, 1);
        long startOfNextDay = cal.getTimeInMillis();
        userMealsRef.orderByChild("date/time")
                .startAt(startOfDay)
                .endAt(startOfNextDay)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int dailyIntake = 0;
                        for (DataSnapshot data: snapshot.getChildren()) {
                            Meal meal = data.getValue(Meal.class);
                            if (meal != null) {
                                dailyIntake += meal.getCalories();
                            }
                        }
                        dailyCalories.setText("Daily Calorie Intake: " + dailyIntake);
                        dailyCalorieIntake = dailyIntake;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void setDailyCalorieGoal(InputMealViewModel viewModel, TextView dailyGoal) {
        int feet = viewModel.getUserData().getHeightFeet();
        int inches = (feet * 12) + viewModel.getUserData().getHeightInches();
        int height = (int) (inches * 2.54);
        int weight = viewModel.getUserData().getWeight();
        String gender = viewModel.getUserData().getGender();
        int goal;
        switch (gender) {
        case "Male": goal = (int) ((13.397 * weight) + (4.799 * height) - 25.178);
            break;
        case "Female": goal = (int) ((9.247 * weight) + (3.098 * height) - 360.963);
            break;
        default: goal = (int) ((11.322 * weight) + (3.949 * height) - 193.071);
        }
        dailyGoal.setText("Daily Calorie Goal: " + goal);
        dailyCalorieGoal = goal;
    }

    // For Test Unit purposes
    public int calculateTotalCalorieIntake(int currentIntake, int mealCalories) {
        return currentIntake + mealCalories;
    }
    public int calculateDailyCalorieDeficit(int dailyGoal, int dailyIntake) {
        return dailyGoal - dailyIntake;
    }
    public double calculateCalorieIntakePercentage(int dailyIntake, int dailyGoal) {
        if (dailyGoal == 0) {
            return 0; // Prevent division by zero
        }
        return (double) dailyIntake / dailyGoal * 100;
    }


}
