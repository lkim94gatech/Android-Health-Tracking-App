package com.example.a2340project;

import java.util.Map;

// Also uses SOLID cause it is a recipe object
public class Recipe {

    private String name;
    private Map<Ingredient, String> ingredientMap; // Ingredient and Amount

    public Recipe(String name, Map<Ingredient, String> ingredientsMap) {
        this.name = name;
        this.ingredientMap = ingredientsMap;
    }

    public String getName() {
        return name;
    }

    public Map<Ingredient, String> getIngredientMap() {
        return ingredientMap;
    }
}
