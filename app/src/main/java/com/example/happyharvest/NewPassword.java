package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class NewPassword extends AppCompatActivity {
    EditText pass1;
    EditText pass2;
    Button Sign_in1;
    String userName;
    ImageView imageView;
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        userName = getIntent().getStringExtra("USER");
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        pass1 = findViewById(R.id.pass11);
        pass2 = findViewById(R.id.pass21);
        Sign_in1 = findViewById(R.id.Sign_in11);
        ImageView eye = findViewById(R.id.ic_eye_off1);
        ImageView eye1 = findViewById(R.id.ic_eye_off11);

        imageView=findViewById(R.id.back_in11);

        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pass1.setBackgroundResource(R.drawable.shap_selected);
                pass2.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });

        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pass1.setBackgroundResource(R.drawable.shape_non_selected);
                pass2.setBackgroundResource(R.drawable.shap_selected);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass1.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye.setBackgroundResource(R.drawable.ic_eye);
                } else {
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye.setBackgroundResource(R.drawable.ic_eye_off);
                }

                pass1.setSelection(pass1.getText().length());
            }
        });
        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    pass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye1.setBackgroundResource(R.drawable.ic_eye);
                } else {
                    // إخفاء كلمة المرور
                    pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye1.setBackgroundResource(R.drawable.ic_eye_off);
                }

                pass2.setSelection(pass2.getText().length());
            }
        });

        Sign_in1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass1.getText().toString().isEmpty()) {
                    pass1.setError("Password required");
                    return;
                }
                if (pass1.length() < 8) {
                    pass1.setError("Password must be at least 8 characters");
                    return;
                }

                // التحقق من قوة كلمة المرور
                String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
                if (!pass1.getText().toString().matches(passwordPattern)) {
                    pass1.setError("The password must contain an uppercase and lowercase letter, a number, and a special symbol\n");
                    return;
                }
                if (!pass1.getText().toString().equals(pass2.getText().toString())) {

                    Toast.makeText(NewPassword.this, "The two entries are not equal", Toast.LENGTH_SHORT).show();

                } else {
                    myViewModel.getAllFarmerByUser(userName).observe((NewPassword.this), farmers -> {

                        Farmer farmer = new Farmer(userName, pass1.getText().toString(), farmers.get(0).getPhone_nomber(), farmers.get(0).getCard_Number(), farmers.get(0).getS_name().toString(), farmers.get(0).getS_Image(), farmers.get(0).getBio());

                        myViewModel.updateFarmer(farmer);
//
                        Toast.makeText(NewPassword.this, "You've got a new password", Toast.LENGTH_SHORT).show();
//
                        Intent intent = new Intent(NewPassword.this, ActivityMainSignIn.class);
                        startActivity(intent);
                    });

                }

            }
        });

    }


}