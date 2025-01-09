package com.example.LTS_Plus.phone_Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.MainActivity;
import com.example.LTS_Plus.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class ManiPhone extends AppCompatActivity {

    EditText mgetphonenumber;
    android.widget.Button msendotp;
    CountryCodePicker mcountryCodePicker;
    String countrycode;
    String phonenumber;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressBarOTPmain;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mani_phone);

        mcountryCodePicker = findViewById(R.id.countryCodePicker);
        msendotp = findViewById(R.id.sendOTPButton);
        mgetphonenumber = findViewById(R.id.getPhoneNumber);
        mprogressBarOTPmain = findViewById(R.id.progressBarOTPmain);

        firebaseAuth = FirebaseAuth.getInstance();

        countrycode = mcountryCodePicker.getSelectedCountryCodeWithPlus();

        mcountryCodePicker.setOnCountryChangeListener(() -> countrycode = mcountryCodePicker.getSelectedCountryCodeWithPlus());

        msendotp.setOnClickListener(v -> {
            String number;
            number = mgetphonenumber.getText().toString();
            if (number.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Please enter your phone number", Toasty.LENGTH_SHORT).show();
            } else if (number.length() < 11) {
                Toasty.warning(getApplicationContext(), "Please enter a valid phone number", Toasty.LENGTH_SHORT).show();
            } else {
                mprogressBarOTPmain.setVisibility(View.VISIBLE);
                phonenumber = countrycode + number;

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phonenumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(ManiPhone.this)
                        .setCallbacks(mCallbacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Automatic callback function
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toasty.error(getApplicationContext(), "Verification failed. Please try again.", Toasty.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toasty.success(getApplicationContext(), "OTP has been sent successfully", Toasty.LENGTH_SHORT).show();
                mprogressBarOTPmain.setVisibility(View.VISIBLE);
                codesent = s;

                Intent intent = new Intent(ManiPhone.this, OtpAuthorization.class);
                intent.putExtra("otp", codesent);
                startActivity(intent);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(ManiPhone.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
