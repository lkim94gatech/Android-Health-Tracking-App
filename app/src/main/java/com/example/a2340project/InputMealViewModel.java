package com.example.a2340project;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class InputMealViewModel extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        public void getInstance() {

        }

        public void updateData(int feet, int inches, int weight, String gender) {

        }
    }
}
=======
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
>>>>>>> 4f8db7fefa6de5d9745804f1cc04f8be5e764c36
