package com.example.LTS_Plus;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
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
        SeekBar penWidthSeekBar = findViewById(R.id.penWidthSeekBar);

        // Set a default pen width that works well with fingers
        signaturePad.setMinWidth(15.0f); // A larger value for finger/thumb input

        // Set up the SeekBar for adjusting pen width
        penWidthSeekBar.setMax(50);
        penWidthSeekBar.setProgress(15);
        penWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Adjust the pen width dynamically based on the SeekBar value
                signaturePad.setMinWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Clear SignaturePad
        btnClear.setOnClickListener(v -> signaturePad.clear());

        // Save Signature
        btnSave.setOnClickListener(v -> {
            if (signaturePad.isEmpty()) {
                Toast.makeText(SignatureActivity.this, "Please sign before saving!", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                saveSignatureToGallery(signatureBitmap);
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
            Log.e("SignatureSaveError", "Error saving signature to gallery", e);
        }
    }
}
