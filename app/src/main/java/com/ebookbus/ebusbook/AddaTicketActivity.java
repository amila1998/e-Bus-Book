package com.ebookbus.ebusbook;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;


import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AddaTicketActivity extends AppCompatActivity {
    Button getCurrLocBtn, GenTickBtn, mpayBtn , mdelBtn;
    EditText currLocatiointext, noplate, endLoc, fullQty, Half;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView mtextView3;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID ;
    float amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_ticket);

        this.getCurrLocBtn = findViewById(R.id.getcurrLocBtn);
        this.currLocatiointext = findViewById(R.id.cuurLocTxt);
        this.GenTickBtn = findViewById(R.id.denMyTickBtn);
        this.noplate = findViewById(R.id.noplate);
        this.endLoc = findViewById(R.id.endLoc);
        this.fullQty = findViewById(R.id.fullQty);
        this.Half = findViewById(R.id.halfQty);
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.mdelBtn= findViewById(R.id.delBtn);
        this.mpayBtn = findViewById(R.id.payBtn);
        this.mtextView3 = findViewById(R.id.textView3);

        GenTickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noPlate = noplate.getText().toString().trim();
                String currLocatioin = currLocatiointext.getText().toString().trim();
                String endLocation = endLoc.getText().toString().trim();
                String fullqty = fullQty.getText().toString().trim();
                String Halfqty = Half.getText().toString().trim();


                if (TextUtils.isEmpty(noPlate)) {
                    noplate.setError("First Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(currLocatioin)) {
                    currLocatiointext.setError("Last Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(endLocation)) {
                    endLoc.setError("Email Address is Required");
                    return;
                }
                if (TextUtils.isEmpty(fullqty)) {
                    fullQty.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(Halfqty)) {
                    Half.setText("0");
                    return;
                }

                userID = fAuth.getCurrentUser().getUid();

                amount = setamount( fullqty, Halfqty);
                Ticket ticket = new Ticket(noPlate, currLocatioin, endLocation, fullqty, Halfqty, amount);
                CollectionReference userRef = fStore.collection("users");
                userRef.document(userID).collection("my tickets").add(ticket).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddaTicketActivity.this, "Ticket is Genarated", Toast.LENGTH_SHORT).show();
                        mpayBtn.setVisibility(View.VISIBLE);
                        mdelBtn.setVisibility(View.VISIBLE);
                        mtextView3.setText("Total is Rs."+amount);
                        mtextView3.setVisibility(View.VISIBLE);
                        GenTickBtn.setVisibility(View.GONE);
                        noplate.setEnabled(false);
                        currLocatiointext.setEnabled(false);
                        fullQty.setEnabled(false);
                        endLoc.setEnabled(false);
                        Half.setEnabled(false);
                        String vnoPlate = ticket.getNoPlate();
                        userRef.document(userID).collection("my tickets").whereEqualTo("noPlate",vnoPlate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                    ticket.setDocumentId(documentSnapshot.getId());
                                }

                            }
                        });





                        mdelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String TID = ticket.getDocumentId();
                                userRef.document(userID).collection("my tickets").document(TID).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(AddaTicketActivity.this, "Ticket is Deleted", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),GetATicketActivity.class));
                                            }
                                        });
                            }
                        });



                        mpayBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO: Navigate the payment
                            }
                        });


                    }

                });
            }
        });
    }

    private float setamount(String fullqty, String halfqty) {
        int fullQty = Integer.parseInt(fullqty);
        int halfQty = Integer.parseInt(halfqty);
        int destinationPrice = 12;
        int total;

        return  total = (fullQty * destinationPrice )+ ((destinationPrice/2)*halfQty);
    }


}



