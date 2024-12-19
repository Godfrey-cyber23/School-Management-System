package com.example.LTS_Plus.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private EditText logEmail, logPassword;
    private String email, password;
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

        // Replacing anonymous View.OnClickListener with lambdas
        openReg.setOnClickListener(view -> openRegister());
        loginBtn.setOnClickListener(view -> validateData());
        openForgetPassword.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class))
        );
    }

    private void validateData() {
        email = logEmail.getText().toString().trim();
        password = logPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toasty.error(this, "Please provide all fields", Toasty.LENGTH_SHORT).show();
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        openMain();
                    } else {
                        Toasty.error(
                                LoginActivity.this,
                                "Error: " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toasty.LENGTH_SHORT
                        ).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toasty.error(
                                LoginActivity.this,
                                "Error: " + e.getMessage(),
                                Toasty.LENGTH_SHORT
                        ).show()
                );
    }

    private void openMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void openRegister() {
        startActivity(new Intent(LoginActivity.this, SignUp.class));
        finish();
    }
}
