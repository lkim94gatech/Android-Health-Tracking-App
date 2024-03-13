package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputMealScreenTest {

    @Test
    public void onCreate() {
//        InputMealScreen screen = new InputMealScreen();
//        assertEquals(300, screen.calculateTotalCalorieIntake(100, 200));
    }

    @Test
    public void calculateDailyCalorieDeficit() {
//        InputMealScreen screen = new InputMealScreen();
//        // Assuming the daily goal is 2000 calories and the actual intake is 1500 calories
//        int dailyGoal = 2000;
//        int dailyIntake = 1500;
//        int expectedDeficit = 500; // Expected calorie deficit
//
//        assertEquals(expectedDeficit, screen.calculateDailyCalorieDeficit(dailyGoal, dailyIntake));
    }

    @Test
    public void calculateCalorieIntakePercentage_calculatesPercentageCorrectly() {
//        InputMealScreen screen = new InputMealScreen();
//        int dailyIntake = 1500;
//        int dailyGoal = 2000;
//        double expectedPercentage = 75.0; // 1500 is 75% of 2000
//
//        // Use a small delta for comparison of double values
//        assertEquals(expectedPercentage, screen.calculateCalorieIntakePercentage(dailyIntake, dailyGoal), 0.01);
    }

    @Test
    public void calculateCalorieIntakePercentage_handlesZeroGoal() {
//        InputMealScreen screen = new InputMealScreen();
//        int dailyIntake = 1500;
//        int dailyGoal = 0; // Edge case where daily goal is zero
//        double expectedPercentage = 0.0; // Expected result is 0% since division by zero is handled
//
//        assertEquals(expectedPercentage, screen.calculateCalorieIntakePercentage(dailyIntake, dailyGoal), 0.01);
    }
}