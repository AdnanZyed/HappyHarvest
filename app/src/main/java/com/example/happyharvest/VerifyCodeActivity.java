package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText verificationCodeEditText;
    private Button btnVerify;
    private FirebaseAuth mAuth;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_phone);

        mAuth = FirebaseAuth.getInstance();
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        btnVerify = findViewById(R.id.btnVerify);

        mVerificationId = getIntent().getStringExtra("verificationId");

        btnVerify.setOnClickListener(v -> {
            String code = verificationCodeEditText.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                verificationCodeEditText.setError("Please send verification code");
                return;
            }
            verifyCode(code);
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(VerifyCodeActivity.this, ActivityMainSignIn.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VerifyCodeActivity.this, "The verification code is invalid", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
