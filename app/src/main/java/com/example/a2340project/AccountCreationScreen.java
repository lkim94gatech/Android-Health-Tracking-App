package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class AccountCreationScreen extends AppCompatActivity {
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
        exitAccountCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationScreen.this, LoginScreen.class);
                startActivity(intent);
            }


        });
    }
}
