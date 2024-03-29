package com.example.a2340project;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RecipeTest {
    @Test
    public void getName() {
        Map<String, Double> map = new HashMap<>();
        map.put("Milk", 2d);
        map.put("Cereal", 3d);
        Recipe recipe = new Recipe("Cereal", map);
        assertEquals("Cereal", recipe.getName());
    }
    @Test
    public void getMap() {
        Map<String, Double> map = new HashMap<>();
        map.put("Milk", 2d);
        map.put("Cereal", 3d);
        Recipe recipe = new Recipe("Cereal", map);
        assertEquals(map, recipe.getIngredientMap());
    }
    @Test
    public void getCanMake() {
        Map<String, Double> map = new HashMap<>();
        map.put("Milk", 2d);
        map.put("Cereal", 3d);
        Recipe recipe = new Recipe("Cereal", map);
        recipe.setCanMake(true);
        assertTrue(recipe.getCanMake());
    }
}
