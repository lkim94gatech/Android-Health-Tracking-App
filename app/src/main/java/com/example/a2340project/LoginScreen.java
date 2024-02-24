package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
        view model stuff idk what goes here (?)
         */
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        Button loginButton = findViewById(R.id.loginButton);
        Button accountCreationButton = findViewById(R.id.accountCreationButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                /*
                more view model stuff with retrieving text from text boxes
                 */

                //if username && password match database -> startActivity(intent)
            }
        });
        accountCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, AccountCreationScreen.class);
                startActivity(intent);
            }
        });
    }
}
