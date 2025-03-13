package com.example.LTS_Plus.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText logEmail, logPassword;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private FirebaseAuth auth;
    private MaterialButton loginBtn;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        TextView openReg = findViewById(R.id.openReg);
        TextView openForgetPassword = findViewById(R.id.openForgetPassword);
        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPassword);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        loginBtn = findViewById(R.id.loginBtn);
        rememberMe = findViewById(R.id.rememberMe);

        // Initialize SharedPreferences for "Remember Me"
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        // Check if "Remember Me" was enabled
        if (sharedPreferences.getBoolean("rememberMe", false)) {
            logEmail.setText(sharedPreferences.getString("email", ""));
            logPassword.setText(sharedPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

        // Set up listeners
        openReg.setOnClickListener(view -> navigateToSignUp());
        openForgetPassword.setOnClickListener(view -> navigateToForgetPassword());
        loginBtn.setOnClickListener(view -> validateData());

        // Add TextWatcher to the password field
        logPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable the password toggle when the user starts typing
                passwordInputLayout.setPasswordVisibilityToggleEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void validateData() {
        String email = Objects.requireNonNull(logEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(logPassword.getText()).toString().trim();

        // Reset errors
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);

        // Validate email format
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email address.");
            return;
        }

        // Validate password
        if (password.isEmpty() || password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters.");
            return;
        }

        // Proceed to login
        loginUser(email, password);
    }

    private void loginUser(String email, String password) {
        // Show progress indicator
        loginBtn.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // Hide progress indicator
                    loginBtn.setIconResource(0);
                    loginBtn.setEnabled(true);

                    if (task.isSuccessful()) {
                        // Save credentials if "Remember Me" is checked
                        if (rememberMe.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("rememberMe", true);
                            editor.apply();
                        } else {
                            // Clear saved credentials
                            sharedPreferences.edit().clear().apply();
                        }

                        Toasty.success(this, "Login successful!", Toasty.LENGTH_SHORT).show();
                        navigateToMain();
                    } else {
                        handleAuthError(task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    // Hide progress indicator
                    loginBtn.setIconResource(0);
                    loginBtn.setEnabled(true);

                    Toasty.error(this, "Error: " + e.getMessage(), Toasty.LENGTH_SHORT).show();
                });
    }

    private void handleAuthError(Exception exception) {
        String errorMessage = "Login failed. Please try again.";
        if (exception != null) {
            String exceptionMessage = exception.getMessage();
            if (exceptionMessage != null) {
                if (exceptionMessage.contains("password")) {
                    errorMessage = "Incorrect password. Please try again.";
                } else if (exceptionMessage.contains("no user")) {
                    errorMessage = "No account found with this email.";
                }
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