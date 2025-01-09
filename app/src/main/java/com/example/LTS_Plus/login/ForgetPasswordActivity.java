package com.example.LTS_Plus.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputEditText txtEmail;
    private MaterialButton forgetBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Bind views from XML
        txtEmail = findViewById(R.id.forgetEmail);
        forgetBtn = findViewById(R.id.forgetBtn);
        TextView openReg = findViewById(R.id.openReg);

        // Set listener for the "Reset Password" button
        forgetBtn.setOnClickListener(v -> validateEmail());

        // Set listener for the "Back to Login" text
        openReg.setOnClickListener(v -> {
            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Validate email input and proceed to reset password if valid.
     */
    private void validateEmail() {
        String email = Objects.requireNonNull(txtEmail.getText()).toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
        } else if (!isValidEmail(email)) {
            txtEmail.setError("Enter a valid email");
            txtEmail.requestFocus();
        } else {
            sendPasswordResetEmail(email);
        }
    }

    /**
     * Send a password reset email through Firebase Authentication.
     *
     * @param email The user's email address.
     */
    private void sendPasswordResetEmail(String email) {
        forgetBtn.setEnabled(false); // Disable button during the process

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            forgetBtn.setEnabled(true); // Re-enable button

            if (task.isSuccessful()) {
                Toasty.success(
                        ForgetPasswordActivity.this,
                        "Password reset email sent. Check your inbox.",
                        Toasty.LENGTH_SHORT
                ).show();

                // Redirect to LoginActivity after successful email
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                String errorMessage = task.getException() != null ? task.getException().getMessage() : "An unknown error occurred.";
                Toasty.error(ForgetPasswordActivity.this, "Error: " + errorMessage, Toasty.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Validate email format using built-in Android utility.
     *
     * @param email The email to validate.
     * @return True if valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
