package com.example.LTS_Plus;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignatureActivity extends AppCompatActivity {

    private SignaturePad signaturePad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        signaturePad = findViewById(R.id.signaturePad);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnSave = findViewById(R.id.btnSave);

        // Clear SignaturePad
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        // Save Signature
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signaturePad.isEmpty()) {
                    Toast.makeText(SignatureActivity.this, "Please sign before saving!", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                    saveSignatureToGallery(signatureBitmap);
                }
            }
        });
    }

    private void saveSignatureToGallery(Bitmap signatureBitmap) {
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Signature_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream out = new FileOutputStream(photo)) {
            signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(this, "Signature saved to Gallery!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Unable to save signature.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

