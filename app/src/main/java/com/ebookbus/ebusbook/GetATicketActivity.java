package com.ebookbus.ebusbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetATicketActivity extends AppCompatActivity {
    Button qrCodScannerBtn, GenarateQRCodeBtn, ManuallyGenarateTikecketBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_aticket);

        qrCodScannerBtn = findViewById(R.id.scanQRbtn);
        GenarateQRCodeBtn = findViewById(R.id.genQRCodeBtn);
        ManuallyGenarateTikecketBtn = findViewById(R.id.manualgetTBtn);

        qrCodScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserQRCodeScannerActivity.class));
            }
        });

        GenarateQRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),QRCodeGenaraterActivity.class));
            }
        });

        ManuallyGenarateTikecketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddaTicketActivity.class));
            }
        });

    }
}