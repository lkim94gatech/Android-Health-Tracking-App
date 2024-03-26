package com.example.a2340project;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

// Use this class cause it uses SOLID principles cause it only operates on the cookbook

public class CookBook {

    private ArrayList<Recipe> cookBook;

    public CookBook (DatabaseReference ref) {
        this.cookBook = getCookBookDatabase(ref);
    }

    public ArrayList<Recipe> getCookBookDatabase(DatabaseReference ref){
        /*
            Make call to firebase here using the "CookBook Database" reference sent
            from recipe screen(could put this in a view model)
            then store on cookBook variable
         */
    }

    public ArrayList<Recipe> getCookBook(){
        return cookBook;
    }
}
