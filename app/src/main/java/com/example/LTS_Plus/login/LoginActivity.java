package com.example.LTS_Plus.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private EditText logEmail, logPassword;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        TextView openReg = findViewById(R.id.openReg);
        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView openForgetPassword = findViewById(R.id.openForgetPassword);

        // Initialize Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        // Set up listeners
        openReg.setOnClickListener(view -> navigateToSignUp());
        loginBtn.setOnClickListener(view -> validateData());
        openForgetPassword.setOnClickListener(view -> navigateToForgetPassword());
    }

    private void validateData() {
        String email = logEmail.getText().toString().trim();
        String password = logPassword.getText().toString().trim();

        // Validate email format
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toasty.error(this, "Please enter a valid email address", Toasty.LENGTH_SHORT).show();
            return;
        }

        // Validate password
        if (password.isEmpty() || password.length() < 6) {
            Toasty.error(this, "Password must be at least 6 characters long", Toasty.LENGTH_SHORT).show();
            return;
        }

        // Proceed to login
        loginUser(email, password);
    }

    private void loginUser(String email, String password) {
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toasty.success(this, "Login successful!", Toasty.LENGTH_SHORT).show();
                        navigateToMain();
                    } else {
                        handleAuthError(task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toasty.error(this, "Error: " + e.getMessage(), Toasty.LENGTH_SHORT).show();
                });
    }

    private void handleAuthError(Exception exception) {
        String errorMessage = "Login failed. Please try again.";
        if (exception != null) {
            String exceptionMessage = exception.getMessage();
            assert exceptionMessage != null;
            if (exceptionMessage.contains("password")) {
                errorMessage = "Incorrect password. Please try again.";
            } else if (exceptionMessage.contains("no user")) {
                errorMessage = "No account found with this email.";
            }
        }
        Toasty.error(this, errorMessage, Toasty.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }

    private void navigateToForgetPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
}
