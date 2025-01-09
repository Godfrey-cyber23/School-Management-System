package com.example.LTS_Plus.phone_Auth;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.example.LTS_Plus.login.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LandingScreen extends AppCompatActivity {

    private static final String TAG = "LandingScreen";
    private static final int SPLASH_DELAY = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Set the layout for the landing screen
        setContentView(R.layout.activity_landing_screen);

        // Log initialization
        Log.d(TAG, "onCreate: Firebase initialized and layout set.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Entered LandingScreen");

        if (!isNetworkConnected()) {
            Toast.makeText(this, "No internet connection. Please check your network.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "onStart: No internet connection.");
            return;
        }

        Log.d(TAG, "onStart: Network connected. Proceeding with authentication check.");

        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() == null) {
                Log.d(TAG, "onStart: User not authenticated. Redirecting to Login.");
                Toast.makeText(this, "User not authenticated. Redirecting to Login.", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            } else {
                Log.d(TAG, "onStart: User authenticated. Redirecting to Main.");
                Toast.makeText(this, "Welcome back, user!", Toast.LENGTH_SHORT).show();
                navigateToMain();
            }
        }, SPLASH_DELAY);
    }


    /**
     * Checks if the device is connected to the internet.
     *
     * @return true if connected, false otherwise
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Navigate to the LoginActivity.
     */
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish LandingScreen so it doesn't remain in the back stack
    }

    /**
     * Navigate to the MainActivity.
     */
    private void navigateToMain() {
        Log.d(TAG, "Navigating to MainActivity.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish LandingScreen so it doesn't remain in the back stack
    }
}
