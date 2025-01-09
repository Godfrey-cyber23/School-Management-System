package com.example.LTS_Plus.phone_Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class OtpAuthorization extends AppCompatActivity {

    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressBarVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_autheration);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mchangenumber = mverifyotp.findViewById(R.id.changeNumber);
        mverifyotp = mverifyotp.findViewById(R.id.verifyOtp);
        mgetotp = mverifyotp.findViewById(R.id.getotp);
        mprogressBarVerifyOtp = mverifyotp.findViewById(R.id.progressBarVerifyOtp);

        firebaseAuth = FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(view -> {
            Intent intent = new Intent(OtpAuthorization.this, MainActivity.class);
            startActivity(intent);

        });

        mverifyotp.setOnClickListener(view -> {
            enteredotp = mgetotp.getText().toString();
            if (enteredotp.isEmpty()){
                Toasty.success(getApplicationContext(), "Please enter your OTP first", Toasty.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Enter your OTP First", Toast.LENGTH_SHORT).show();
            }else {
                mprogressBarVerifyOtp.setVisibility(View.VISIBLE);
                String coderecieved = getIntent().getStringExtra("OTP");
                assert coderecieved != null;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(coderecieved,enteredotp);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                mprogressBarVerifyOtp.setVisibility(View.VISIBLE);
                Toasty.success(getApplicationContext(), "Welcome to the LTS School App", Toasty.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OtpAuthorization.this, SetProfile.class);
                startActivity(intent);
                finish();
            }else {
                if (task.getException() != null){
                    mprogressBarVerifyOtp.setVisibility(View.VISIBLE);
                    Toasty.warning(getApplicationContext(), "Your login has failed", Toasty.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}