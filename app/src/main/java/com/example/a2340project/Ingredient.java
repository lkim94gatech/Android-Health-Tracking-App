package com.example.a2340project;

public class Ingredient {
    private String name;
    private double quantity;
    private double caloriesPerServing;
    private String expirationDate; // Optional

    // Default constructor required for calls to DataSnapshot.getValue(Ingredient.class)
    public Ingredient() {

    }

    public Ingredient(String name, double quantity, double caloriesPerServing,
                      String expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.caloriesPerServing = caloriesPerServing;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
