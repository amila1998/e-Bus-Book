package com.ebookbus.ebusbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class startUp extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button mgetStart;
    String userId;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);


        fAuth = FirebaseAuth.getInstance();
        mgetStart = findViewById(R.id.getStart);
        fStore = FirebaseFirestore.getInstance();

        mgetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fAuth.getCurrentUser() != null){
                    userId = fAuth.getCurrentUser().getUid();
                    DocumentReference df =fStore.collection("users").document(userId);
                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                if(documentSnapshot.getString("isUser") != null){

                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                                if(documentSnapshot.getString("isBus") != null){

                                    startActivity(new Intent(getApplicationContext(),MainActivityBus.class));
                                }
                            }else{
                                startActivity(new Intent(getApplicationContext(),LogIn.class));
                                finish();
                            }
                        }
                    });

                }else{
                    startActivity(new Intent(getApplicationContext(),LogIn.class));
                    finish();
                }
            }
        });


    }
}