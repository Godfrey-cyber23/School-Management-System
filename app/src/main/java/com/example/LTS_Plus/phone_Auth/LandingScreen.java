package com.example.LTS_Plus.phone_Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.LTS_Plus.R;
import com.example.LTS_Plus.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LandingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already authenticated
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Redirect to login or sign-up screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // If authenticated, stay in MainActivity
    }
}
