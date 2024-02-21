package com.example.a2340project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.provider.FirebaseInitProvider;
import com.google.firebase.auth.GoogleAuthCredential;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}


