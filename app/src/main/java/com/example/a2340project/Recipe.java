package com.example.a2340project;

import java.util.Map;

// Also uses SOLID cause it is a recipe object
public class Recipe {

    private String name;
    private Map<String, Double> ingredientMap; // Ingredient and Amount

    public Recipe() {

    }

    public Recipe(String name, Map<String, Double> ingredientsMap) {
        this.name = name;
        this.ingredientMap = ingredientsMap;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getIngredientMap() {
        return ingredientMap;
    }
}
