package com.example.a2340project;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountCreationScreen extends AppCompatActivity {
    private AccountCreationViewModel viewModel;
    private EditText newUsernameText;
    private EditText newPasswordText;
    private FirebaseDatabase myDatabase;
    private DatabaseReference myDatabaseReference;

    // onCreate to initialize username, password, buttons, and view model
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        viewModel = AccountCreationViewModel.getInstance();
        newUsernameText = findViewById(R.id.createUsernameText);
        newPasswordText = findViewById(R.id.createPasswordText);
        Button createNewAccountButton = findViewById(R.id.createNewAccountButton);
        Button exitAccountCreationButton = findViewById(R.id.exitAccountCreation);

        // button for creating new account, will add username and password to database and then continue to menu screen.
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationScreen.this, HomeScreen.class);

                // i'm not 100% sure how these grab the text box data but it worked in 0.5
                String newUsername = viewModel.getAccountCreationData().getUsername();
                String newPassword = viewModel.getAccountCreationData().getPassword();

                // adds in our firebase database
                myDatabase = FirebaseDatabase.getInstance();
                myDatabaseReference = myDatabase.getReference("Users");

                // creates a User object with the username and password
                storeUser(newUsername, newPassword);

                // continues to menu screen
                startActivity(intent);
            }
            // helper method to add users to database
            private void storeUser(String username, String password) {
                String userId = myDatabaseReference.push().getKey();
                User user = new User(username, password);
                myDatabaseReference.child(userId).setValue(user);
            }
        });

        // button to exit back to login screen
        exitAccountCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}
