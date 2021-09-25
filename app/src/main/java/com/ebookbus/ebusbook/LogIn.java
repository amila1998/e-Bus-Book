package com.ebookbus.ebusbook;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class LogIn extends AppCompatActivity {
    private Button mLoginBtn, mSignInBtn;
    private EditText mEmail,mPassword;
    private TextView mForgetPassword;
    private FirebaseAuth fAuth;
    private ProgressBar mprogressBar ;


    private boolean isAdded = false; // Determines if the details have been added.
    public boolean isLoggedIn = false; // Determines if the user is logged in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        this.mSignInBtn = findViewById(R.id.signIn);
        this.mLoginBtn = findViewById(R.id.login);
        this.mEmail = findViewById(R.id.emailAddress);
        this.mPassword = findViewById(R.id.password);
        this.mForgetPassword = findViewById(R.id.forgetpassword);
        this.mprogressBar = findViewById(R.id.progressBar2);
        this.fAuth = FirebaseAuth.getInstance();


        this.mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            }
        });

        this.mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Address is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                mprogressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("TAG", "signInWithCustomToken:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LogIn.this, "Error !", Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



    }
}