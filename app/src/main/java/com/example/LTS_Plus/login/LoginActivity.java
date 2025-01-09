package com.example.LTS_Plus.login;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        TextView openReg = findViewById(R.id.openReg);
        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPassword);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView openForgetPassword = findViewById(R.id.openForgetPassword);

        // Navigate to SignUp activity
        openReg.setOnClickListener(view -> navigateToSignUp());

        // Validate and login user
        loginBtn.setOnClickListener(view -> validateData());

        // Navigate to ForgetPasswordActivity
        openForgetPassword.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class))
        );
    }

    private void validateData() {
        String email = logEmail.getText().toString().trim();
        String password = logPassword.getText().toString().trim();

        if (email.isEmpty()) {
            Toasty.error(this, "Please enter your email", Toasty.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toasty.error(this, "Please enter your password", Toasty.LENGTH_SHORT).show();
            return;
        }

        loginUser(email, password);
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Navigate to MainActivity
                        navigateToMain();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toasty.error(this, "Error: " + error, Toasty.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toasty.error(this, "Error: " + e.getMessage(), Toasty.LENGTH_SHORT).show());
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close LoginActivity so user cannot go back
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
        finish(); // Close LoginActivity to avoid going back
    }
}
