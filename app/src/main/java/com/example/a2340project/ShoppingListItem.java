package com.example.a2340project;

public class ShoppingListItem {
    private String name;
    private int quantity;
    private boolean checked;

    public ShoppingListItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.checked = false;
    }
    public ShoppingListItem() {
        this.name = "";
        this.quantity = 0;
        this.checked = false;

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
    public String getName() {
        return this.name;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public boolean getChecked() {
        return this.checked;
    }
}
