package com.ebookbus.ebusbook;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class QRCodeGenaraterActivity extends AppCompatActivity {

    String userId;
    private FirebaseAuth fAuth;
    public final static int QRCodeWidth = 500;
    Bitmap bitmap;
    private Button download;

    private ImageView iv;
    @Override
    protected void onStart() {
        super.onStart();

        userId = fAuth.getCurrentUser().getUid();
        try {
            bitmap = textToImageEncode(userId);
            iv.setImageBitmap(bitmap);
            download.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_genarater);

        this.fAuth = FirebaseAuth.getInstance();

        download = findViewById(R.id.download);
        download.setVisibility(View.INVISIBLE);
        iv = findViewById(R.id.image);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner"
                        , null);
                Toast.makeText(QRCodeGenaraterActivity.this, "Saved to galary", Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }
    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}