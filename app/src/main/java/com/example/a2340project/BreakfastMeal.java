package com.example.a2340project;

/**
 * A specific class that implements the MealPrep interface
 */
public class BreakfastMeal implements MealPrep {
    private Recipe recipe;

    public BreakfastMeal(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void cook() {
        // logic for cook
    }
    // logic for returning calculated calories
    @Override
    public int calculateCalories() {
        //return recipe.getCalories();
        return 0; // temp place holder
    }
}
