package com.example.a2340project;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookBookTest {

    @Test
    public void testGetCookBook() {
        CookBook testCookBook = new CookBook();
        ArrayList<Recipe> test = new ArrayList<>();
        Map<String, Double> testIngredients = new HashMap<>();
        testIngredients.put("Fake Ingredient", 3.0);
        test.add(new Recipe("test", testIngredients));
        test.add(new Recipe("constructor", testIngredients));
        ArrayList<Recipe> gottenBook = testCookBook.getCookBook();
        assertEquals(gottenBook.get(0).getName(), test.get(0).getName());
        assertEquals(gottenBook.get(1).getName(), test.get(1).getName());
    }

    @Test
    public void testCookBookToRecipeNames() {
        CookBook testCookBook = new CookBook();
        ArrayList<String> recipeNames = new ArrayList<>();
        recipeNames.add("test");
        recipeNames.add("constructor");
        assertEquals(testCookBook.cookBookToRecipeNames(), recipeNames);
    }
}