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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {

    private EditText regName, regEmail, regPassword;
    private String name, email, pass;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        Button register = findViewById(R.id.register);
        TextView openLog = findViewById(R.id.openLog);

        // Replaced anonymous OnClickListener with lambda
        register.setOnClickListener(v -> validateData());
        openLog.setOnClickListener(v -> openLog());
    }

    private void openLog() {
        startActivity(new Intent(SignUp.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            openMain();
        }
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void validateData() {
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        pass = regPassword.getText().toString();

        if (name.isEmpty()) {
            regName.setError("Required");
            regName.requestFocus();
        } else if (email.isEmpty()) {
            regEmail.setError("Required");
            regEmail.requestFocus();
        } else if (pass.isEmpty()) {
            regPassword.setError("Required");
            regPassword.requestFocus();
        } else {
            createUser();
        }
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        uploadData();
                    } else {
                        Toasty.error(SignUp.this,
                                "Error: " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toasty.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toasty.error(SignUp.this, "Error: " + e.getMessage(), Toasty.LENGTH_SHORT).show());
    }

    private void uploadData() {
        DatabaseReference dbRef = reference.child("users");
        String key = dbRef.push().getKey();

        if (key == null) {
            Toasty.error(SignUp.this, "Error: Key generation failed", Toasty.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> user = new HashMap<>();
        user.put("key", key);
        user.put("name", name);
        user.put("email", email);

        dbRef.child(key).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toasty.success(SignUp.this, "User created", Toasty.LENGTH_SHORT).show();
                        openMain();
                    } else {
                        Toasty.error(SignUp.this,
                                Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()),
                                Toasty.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toasty.error(SignUp.this, Objects.requireNonNull(e.getMessage()), Toasty.LENGTH_SHORT).show());
    }
}
