package com.example.a2340project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.health.connect.datatypes.MealType;
import android.os.Bundle;


import org.checkerframework.checker.units.qual.A;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, LoginScreen.class));
                }
            }
        };
        thread.start();
    }
}