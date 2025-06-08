package com.example.happyharvest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.happyharvest.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class Sign_up extends AppCompatActivity {
    private EditText phoneEditText;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private String farmer_name_user;
    private Farmer farmer;
    private ArrayList<Farmer> farmers1;
    private String ePasswordIn;
    private String PhoneIn;
    private LiveData<List<Farmer>> farmerU;
    private ActivitySignUpBinding activitySignUpBinding;
    private My_View_Model myViewModel;
    private String nameIn;
    private String eUserIn;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());


        activitySignUpBinding.eUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });

        activitySignUpBinding.ePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });
        activitySignUpBinding.Phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });
        activitySignUpBinding.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shap_selected);

            }
        });
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isRemembered = sharedPreferences.getBoolean("rememberMe1", false);

        if (isRemembered) {
            activitySignUpBinding.eUser.setText(sharedPreferences.getString("username1", ""));
            activitySignUpBinding.ePassword.setText(sharedPreferences.getString("password1", ""));
            activitySignUpBinding.name.setText(sharedPreferences.getString("name1", ""));
            activitySignUpBinding.Phone.setText(sharedPreferences.getString("phone1", ""));
            activitySignUpBinding.checkBox.setChecked(true);
        }

        activitySignUpBinding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eUserIn = activitySignUpBinding.eUser.getText().toString().trim();
                ePasswordIn = activitySignUpBinding.ePassword.getText().toString().trim();
                PhoneIn = activitySignUpBinding.Phone.getText().toString().trim();
                nameIn = activitySignUpBinding.name.getText().toString().trim();
                farmerU = myViewModel.getAllFarmerByUser(eUserIn);



                activitySignUpBinding.eUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_password);
                        } else {
                            activitySignUpBinding.eUser.setBackgroundResource(R.drawable.edittext_background);
                        }
                    }
                });
                activitySignUpBinding.ePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_password);
                        } else {
                            activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.edittext_background);
                        }
                    }
                });


                if (eUserIn.isEmpty()) {
                    activitySignUpBinding.eUser.setError("Username required");
                    return;
                }

                if (ePasswordIn.isEmpty()) {
                    activitySignUpBinding.ePassword.setError("Password required");
                    return;
                }

                if (eUserIn.length() < 3 || eUserIn.length() > 30) {
                    activitySignUpBinding.eUser.setError("Username must be between 3 and 20 characters");
                    return;
                }

                if (!eUserIn.matches("^[a-zA-Z0-9@#$%^&+=!_]+$")) {
                    activitySignUpBinding.eUser.setError("The user name must contain only letters and numbers");
                    return;
                }

                if (ePasswordIn.length() < 8) {
                    activitySignUpBinding.ePassword.setError("Password must be at least 8 characters");
                    return;
                }

                String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
                if (!ePasswordIn.matches(passwordPattern)) {
                    activitySignUpBinding.ePassword.setError("The password must contain an uppercase and lowercase letter, a number, and a special symbol\n");
                    return;
                }

                if (nameIn.isEmpty()) {
                    activitySignUpBinding.name.setError("Name required");
                    return;
                }

                if (PhoneIn.isEmpty()) {
                    activitySignUpBinding.Phone.setError("Phone number required");
                    return;
                }


                if (!PhoneIn.matches("^[0-9]+$")) {
                    activitySignUpBinding.Phone.setError("Please enter a valid phone number");
                    return;
                }

                if (PhoneIn.length() != 10) {
                    activitySignUpBinding.Phone.setError("The phone number must consist of 10 digits");
                    return;
                }

                myViewModel.getAllFarmerByUser(eUserIn).observe(Sign_up.this, farmers -> {


                    if ( farmers.isEmpty()) {
                        int phoneIn = Integer.parseInt(PhoneIn);
                        farmer = new Farmer(eUserIn, ePasswordIn, phoneIn, 1234, nameIn, null, "");
                        myViewModel.insertFarmer(farmer);


                        Intent intent = new Intent(Sign_up.this, MainActivity_Main.class);
                        intent.putExtra("USER_NAME2", eUserIn);

                        startActivity(intent);
                        myViewModel.addNotification("Account Setup Successful!", "Your account has been created!", R.drawable.created);


                    }
                   else {
                        activitySignUpBinding.eUser.setError("The username has already been used");

                    }


                });

            }


        });

        activitySignUpBinding.bsckSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activitySignUpBinding.SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_up.this, ActivityMainSignIn.class);
                startActivity(intent);
            }
        });

        activitySignUpBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                String username2 = activitySignUpBinding.eUser.getText().toString();
                String password2 = activitySignUpBinding.ePassword.getText().toString();
                String phone2 = activitySignUpBinding.ePassword.getText().toString();
                String name2 = activitySignUpBinding.ePassword.getText().toString();

                if (!username2.isEmpty() && !password2.isEmpty() && !phone2.isEmpty() && !name2.isEmpty()) {
                    editor.putBoolean("rememberMe1", true);
                    editor.putString("username1", username2);
                    editor.putString("password1", password2);
                    editor.putString("phone1", phone2);
                    editor.putString("name1", name2);
                    editor.apply();
                } else {
                    activitySignUpBinding.checkBox.setChecked(false);
                }
            } else {
                editor.clear();
                editor.apply();
            }
        });

        activitySignUpBinding.icEyeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activitySignUpBinding.ePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    activitySignUpBinding.ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff.setBackgroundResource(R.drawable.ic_eye);
                } else {
                    activitySignUpBinding.ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff.setBackgroundResource(R.drawable.ic_eye_off);
                }

                activitySignUpBinding.ePassword.setSelection(activitySignUpBinding.ePassword.getText().length());
            }
        });

    }


}

