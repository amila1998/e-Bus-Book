
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    EditText mfirstName,mlastName,memail,mpassword,mrePassword,mphone;

    Button mregisterBtn;
    TextView mgoToLogin;
    ProgressBar mprogressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore  fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        this.mfirstName = findViewById(R.id.firstname);
        this.mlastName = findViewById(R.id.lastname);
        this.memail = findViewById(R.id.emailAddress);
        this.mpassword = findViewById(R.id.password);
        this.mrePassword = findViewById(R.id.rePassword);
        this.mphone = findViewById(R.id.Phone);
        this.mregisterBtn = findViewById(R.id.register);
        this.mgoToLogin = findViewById(R.id.goLogin);
        this.mprogressBar = findViewById(R.id.progressBar);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),homeFragment.class));
            finish();
        }

        mgoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });

        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mfirstName.getText().toString().trim();
                String lastName = mlastName.getText().toString().trim();
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String repassword = mrePassword.getText().toString().trim();
                String phone = mphone.getText().toString().trim();


                if (TextUtils.isEmpty(firstName)) {
                    mfirstName.setError("First Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    mlastName.setError("Last Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email Address is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    mrePassword.setError("Conform Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mphone.setError("Telephone Number is Required");
                    return;
                }
                if (password.length() < 6) {
                    mpassword.setError("Password must be greater than to six characters");
                    return;
                }
                if (!password.equals(repassword)) {
                    mpassword.setError("Conform Password is not matched");
                    return;
                }

                mprogressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUser.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            // Create a new user with a first and last name
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName",firstName);
                            user.put("lName",lastName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("isUser","1");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "onSuccess: user Registration Successfull for "+ userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(RegisterUser.this, "Error !", Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }


}
