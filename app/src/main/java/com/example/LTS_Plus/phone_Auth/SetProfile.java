package com.example.LTS_Plus.phone_Auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SetProfile extends AppCompatActivity {


    private ImageView mgetuserimageview;
    private static final int PICK_IMAGE = 123;
    private Uri imagepath;

    private EditText mgetusername;

    private FirebaseAuth firebaseAuth;
    private String name;

    private StorageReference storageReference;

    private String ImageUriAccessToken;

    private FirebaseFirestore firebaseFirestore;

    ProgressBar mprogressBarSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setprofile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


        mgetusername = findViewById(R.id.getusername);
        CardView mgetuserimage = findViewById(R.id.getuserimage);
        mgetuserimageview = findViewById(R.id.getuserimageimageView);
        android.widget.Button msaveprofle = findViewById(R.id.saveProfile);
        mprogressBarSaveProfile = findViewById(R.id.progressBarSaveProfile);

        mgetuserimage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        msaveprofle.setOnClickListener(v -> {
            name = mgetusername.getText().toString();
            if (name.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Enter your name!", Toasty.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
            } else if (imagepath == null) {
                Toasty.warning(getApplicationContext(), "Upload your photo", Toasty.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
            } else {
                mprogressBarSaveProfile.setVisibility(View.VISIBLE);
                sendDataForNewUser();
                mprogressBarSaveProfile.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SetProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void sendDataForNewUser() {
        sendDataToRealTimeDatabase();

    }

    private void sendDataToRealTimeDatabase() {


        name = mgetusername.getText().toString().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Objects.requireNonNull(firebaseAuth.getUid()));

        UserProfile muserProfile = new UserProfile(name, firebaseAuth.getUid());
        databaseReference.setValue(muserProfile);
        Toasty.success(getApplicationContext(), "User profile successfully linked", Toasty.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "User Profile Added Successfully", Toast.LENGTH_SHORT).show();
        sendImagetoStorage();


    }

    private void sendImagetoStorage() {
        StorageReference imageref = storageReference.child("Images").child(Objects.requireNonNull(firebaseAuth.getUid())).child("Profile Photo");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
        } catch (IOException e) {
            Log.e("ImageLoadError", "Error loading image from path: " + imagepath, e);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        assert bitmap != null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        // putting image to storage

        UploadTask uploadTask = imageref.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageref.getDownloadUrl().addOnSuccessListener(uri -> {
                ImageUriAccessToken = uri.toString();
                Toasty.success(getApplicationContext(), "Successfully received URI", Toasty.LENGTH_SHORT).show();
                sendDataToCloudFirestore();
            }).addOnFailureListener(e -> Toasty.warning(getApplicationContext(), "URI failed", Toasty.LENGTH_SHORT).show());

            Toasty.success(getApplicationContext(), "Photo uploaded", Toasty.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toasty.error(getApplicationContext(), "Photo not uploaded!", Toasty.LENGTH_SHORT).show());

    }

    private void sendDataToCloudFirestore() {

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseAuth.getUid()));
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("name", name);
        userdata.put("image", ImageUriAccessToken);
        userdata.put("uid", firebaseAuth.getUid());
        userdata.put("status", "Online");

        documentReference.set(userdata).addOnSuccessListener(aVoid -> Toasty.success(getApplicationContext(), "Successfully sent data to Cloud Firestore.", Toasty.LENGTH_SHORT).show());


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            assert data != null;
            imagepath = data.getData();
            mgetuserimageview.setImageURI(imagepath);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}