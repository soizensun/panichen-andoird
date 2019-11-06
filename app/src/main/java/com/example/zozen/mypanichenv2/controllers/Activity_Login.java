package com.example.zozen.mypanichenv2.controllers;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zozen.mypanichenv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity {
    private EditText emailEDT, passwordEDT;
    private Button loginBTN, registerBTN;
    private ProgressBar waitingForLoginPB;
    private FirebaseAuth firebaseAuth;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();

        emailEDT = findViewById(R.id.emailLoginEDT);
        passwordEDT = findViewById(R.id.passwordLoginEDT);
        loginBTN = findViewById(R.id.loginBTN);
        registerBTN = findViewById(R.id.registerLoginBTN);
        waitingForLoginPB = findViewById(R.id.waitingForLoginPB);
        waitingForLoginPB.setVisibility(View.INVISIBLE);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_Register.class));
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEDT.getText().toString().trim();
                String password  = passwordEDT.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    if(email.isEmpty()) {
                        emailEDT.setError("please enter your email");
                        return;
                    }
                    if(password.isEmpty()) {
                        passwordEDT.setError("please enter your password");
                        return;
                    }
                }
                else {
                    waitingForLoginPB.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Activity_Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    waitingForLoginPB.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(Activity_Login.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(Activity_Login.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}
