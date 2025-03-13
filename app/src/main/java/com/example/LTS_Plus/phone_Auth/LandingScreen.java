package com.example.LTS_Plus.phone_Auth;

import android.app.AlertDialog;
import android.app.AlertDialog;
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
    private static final int SPLASH_DELAY = 3000; // Delay in milliseconds (3 seconds)
    private static final int SPLASH_DELAY = 3000; // Delay in milliseconds (3 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
            Log.d(TAG, "onCreate: Firebase initialized successfully.");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Firebase initialization failed: " + e.getMessage());
            Toast.makeText(this, "Error initializing Firebase. Please restart the app.", Toast.LENGTH_LONG).show();
            finish(); // Exit the app if Firebase initialization fails
            return;
        }
        try {
            FirebaseApp.initializeApp(this);
            Log.d(TAG, "onCreate: Firebase initialized successfully.");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Firebase initialization failed: " + e.getMessage());
            Toast.makeText(this, "Error initializing Firebase. Please restart the app.", Toast.LENGTH_LONG).show();
            finish(); // Exit the app if Firebase initialization fails
            return;
        }

        // Set the layout for the landing screen
        setContentView(R.layout.activity_landing_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for network connection
        // Check for network connection
        if (!isNetworkConnected()) {
            showNetworkDialog();
            return;
        }

        // Delay to show splash screen
        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() == null) {
                navigateToLogin();
            } else {
                Log.d(TAG, "onStart: User authenticated. Redirecting to Main.");
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
        if (cm == null) {
            Log.e(TAG, "isNetworkConnected: ConnectivityManager is null.");
            return false;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Log.e(TAG, "isNetworkConnected: NetworkInfo is null.");
            return false;
        }
        return activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Show a dialog if there's no network connection.
     */
    private void showNetworkDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (isNetworkConnected()) {
                        onStart(); // Retry the process
                    } else {
                        showNetworkDialog(); // Show dialog again if still no connection
                    }
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Log.d(TAG, "showNetworkDialog: User chose to exit the app.");
                    finish(); // Exit the app
                })
                .setCancelable(false)
                .show();
    }
        if (cm == null) {
            Log.e(TAG, "isNetworkConnected: ConnectivityManager is null.");
            return false;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Log.e(TAG, "isNetworkConnected: NetworkInfo is null.");
            return false;
        }
        return activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Show a dialog if there's no network connection.
     */
    private void showNetworkDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (isNetworkConnected()) {
                        onStart(); // Retry the process
                    } else {
                        showNetworkDialog(); // Show dialog again if still no connection
                    }
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Log.d(TAG, "showNetworkDialog: User chose to exit the app.");
                    finish(); // Exit the app
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Navigate to the LoginActivity with a fade animation.
     * Navigate to the LoginActivity with a fade animation.
     */
    private void navigateToLogin() {
        Log.d(TAG, "navigateToLogin: Redirecting to LoginActivity.");
        Log.d(TAG, "navigateToLogin: Redirecting to LoginActivity.");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // Apply fade animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // Apply fade animation
        finish(); // Finish LandingScreen so it doesn't remain in the back stack
    }

    /**
     * Navigate to the MainActivity with a fade animation.
     * Navigate to the MainActivity with a fade animation.
     */
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // Apply fade animation
        finish();
    }
}
