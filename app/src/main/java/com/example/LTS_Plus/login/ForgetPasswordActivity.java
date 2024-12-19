package com.example.LTS_Plus.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText txtEmail;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        auth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.forgetEmail);
        Button forgetBtn = findViewById(R.id.forgetBtn);

        // Replaced anonymous OnClickListener with lambda
        forgetBtn.setOnClickListener(v -> validateData());
    }

    private void validateData() {
        email = txtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            txtEmail.setError("Required");
            txtEmail.requestFocus();
        } else {
            forgetPass();
        }
    }

    private void forgetPass() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toasty.warning(
                                ForgetPasswordActivity.this,
                                "Check your Email",
                                Toasty.LENGTH_SHORT
                        ).show();
                        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toasty.error(
                                ForgetPasswordActivity.this,
                                "Error: " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toasty.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}
