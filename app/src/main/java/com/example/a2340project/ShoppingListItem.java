package com.example.a2340project;

public class ShoppingListItem {
    private String name;
    private int quantity;
    private int calories;
    private boolean checked;

    public ShoppingListItem() {

    }
    public ShoppingListItem(String name, int quantity, int calories) {
        this.name = name;
        this.quantity = quantity;
        this.checked = false;
        this.calories = calories;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public String getName() {
        return this.name;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public boolean getChecked() {
        return this.checked;
    }
    public int getCalories() {
        return this.calories;
    }
}
