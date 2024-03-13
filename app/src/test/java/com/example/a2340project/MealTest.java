package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Test;

public class MealTest {

    @Test
    public void getName() {
        Meal meal = new Meal("Pizza", 300);
        assertEquals("Pizza", meal.getName());
    }

    @Test
    public void getCalories() {
        Meal meal = new Meal("Pizza", 300);
        assertEquals(300, meal.getCalories());
    }

    @Test
    public void getDate() {
        Meal meal = new Meal("Pizza", 300);
        assertNotNull(meal.getDate());
        long timeDiff = System.currentTimeMillis() - meal.getDate().getTime();
        assertTrue("Date should be within the last 5 seconds", timeDiff < 5000);
    }
}