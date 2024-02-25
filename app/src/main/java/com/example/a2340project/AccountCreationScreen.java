package com.example.a2340project;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference.CompletionListener;

public class AccountCreationScreen extends AppCompatActivity {
    private AccountCreationViewModel viewModel;
    private EditText newUsernameText;
    private EditText newPasswordText;

    private FirebaseAuth mAuth;
    private String username;
    private String password;
    private static final String TAG = AccountCreationScreen.class.getSimpleName();

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
        mAuth = FirebaseAuth.getInstance();

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
                username = newUsernameText.getText().toString();
                password = newPasswordText.getText().toString();
                /*
                more view model stuff with retrieving text from text boxes
                 */
                mAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(AccountCreationScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUser:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUser:failure", task.getException());
                                    Toast.makeText(AccountCreationScreen.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                //add data to firewall, I'll do later.
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
