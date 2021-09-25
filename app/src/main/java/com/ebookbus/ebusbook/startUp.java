package com.ebookbus.ebusbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class startUp extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button mgetStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);


        fAuth = FirebaseAuth.getInstance();
        mgetStart = findViewById(R.id.getStart);

        mgetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }else{
                    startActivity(new Intent(getApplicationContext(),LogIn.class));
                    finish();
                }
            }
        });


    }
}