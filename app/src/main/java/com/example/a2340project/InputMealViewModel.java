package com.example.a2340project;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

public class InputMealViewModel {
    private static InputMealViewModel instance;
    final private UserData userData;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private InputMealViewModel() {
        userData = new UserData();
        this.updateData(0, 0, 0, "");
    }

    public static synchronized InputMealViewModel getInstance() {
        if (instance == null) {
            instance = new InputMealViewModel();
        }
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public void updateData(int feet, int inches, int weight, String gender) {
        userData.setHeightFeet(feet);
        userData.setHeightInches(inches);
        userData.setWeight(weight);
        userData.setGender(gender);
    }
}
