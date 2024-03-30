package com.example.a2340project;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IngredientTest {

    private Ingredient ingredient;

    @Before
    public void setUp() {
        ingredient = new Ingredient("Tomato", 2.0, 20.0, "2023-12-31");
    }

    @Test
    public void getName() {
        assertEquals("Tomato", ingredient.getName());
    }

    @Test
    public void getQuantity() {
        assertEquals(2.0, ingredient.getQuantity(), 0.0);
    }

    @Test
    public void getCaloriesPerServing() {
        assertEquals(20.0, ingredient.getCaloriesPerServing(), 0.0);
    }

    @Test
    public void getExpirationDate() {
        assertEquals("2023-12-31", ingredient.getExpirationDate());
    }

    @Test
    public void setQuantity() {
        // Set new quantity
        ingredient.setQuantity(3.0);

        // Assert the quantity was updated
        assertEquals(3.0, ingredient.getQuantity(), 0.0);
    }
}
