package com.example.a2340project;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;
    private FirebaseAuth mAuth;
    private String username;
    private String password;
    private static final String TAG = LoginScreen.class.getSimpleName();

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
        Button exitButton = findViewById(R.id.Exit);
        TextView error = findViewById(R.id.Error);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                /*
                more view model stuff with retrieving text from text boxes
                 */
                //if username && password match database -> startActivity(intent)
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if (username == null || password == null
                        || username.contains("\\S+") || password.contains("\\S+")
                        || username.equals("") || password.equals("")) {
                    username = "";
                    password = "";
                    error.setVisibility(View.VISIBLE);

                } else {
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success
                                        Log.d(TAG, "signIn:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails
                                        Log.w(TAG, "signIn:failure", task.getException());
                                        Toast.makeText(LoginScreen.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        accountCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, AccountCreationScreen.class);
                startActivity(intent);

            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
