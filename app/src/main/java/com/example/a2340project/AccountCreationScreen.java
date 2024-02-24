package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountCreationScreen extends AppCompatActivity {
    private EditText newUsernameText;
    private EditText newPasswordText;
    private FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        /*
        view model stuff idk what goes here (?)
         */
        newUsernameText = findViewById(R.id.createUsernameText);
        newPasswordText = findViewById(R.id.createPasswordText);
        Button createNewAccountButton = findViewById(R.id.createNewAccountButton);
        Button exitAccountCreationButton = findViewById(R.id.exitAccountCreation);
        mAuth = FirebaseAuth.getInstance();

        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationScreen.this, HomeScreen.class);

                /*
                more view model stuff with retrieving text from text boxes
                 */

                //add data to firewall, I'll do later.
            }
        });
        exitAccountCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}
