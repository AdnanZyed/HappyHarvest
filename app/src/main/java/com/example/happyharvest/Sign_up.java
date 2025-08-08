package com.example.happyharvest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.happyharvest.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
    private String ePasswordIn, ePasswordIn1;
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
        FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .setAppVerificationDisabledForTesting(false);

        activitySignUpBinding.eUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });

        activitySignUpBinding.ePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });

        activitySignUpBinding.ePassword1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });
        activitySignUpBinding.Phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.Phone.setBackgroundResource(R.drawable.shap_selected);
                activitySignUpBinding.name.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });
        activitySignUpBinding.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activitySignUpBinding.eUser.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.shape_non_selected);
                activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.shape_non_selected);
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
            activitySignUpBinding.ePassword1.setText(sharedPreferences.getString("password11", ""));
            activitySignUpBinding.name.setText(sharedPreferences.getString("name1", ""));
            activitySignUpBinding.Phone.setText(sharedPreferences.getString("phone1", ""));
            // activitySignUpBinding.checkBox.setChecked(true);
        }

        activitySignUpBinding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eUserIn = activitySignUpBinding.eUser.getText().toString().trim();
                ePasswordIn = activitySignUpBinding.ePassword.getText().toString().trim();
                ePasswordIn1 = activitySignUpBinding.ePassword1.getText().toString().trim();
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
                if (nameIn.isEmpty()) {
                    activitySignUpBinding.name.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.name.setError("Name required");
                    return;
                }

                if (eUserIn.isEmpty()) {
                    activitySignUpBinding.eUser.setError("Username required");
                    activitySignUpBinding.eUser.setBackgroundResource(R.drawable.errore);

                    return;
                }


                if (eUserIn.length() < 3 || eUserIn.length() > 30) {
                    activitySignUpBinding.eUser.setError("Username must be between 3 and 20 characters");
                    activitySignUpBinding.eUser.setBackgroundResource(R.drawable.errore);
                    return;
                }

                if (!eUserIn.matches("^[a-zA-Z0-9@#$%^&+=!_]+$")) {
                    activitySignUpBinding.eUser.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.eUser.setError("The user name must contain only letters and numbers");
                    return;
                }
                if (ePasswordIn.isEmpty()) {
                    activitySignUpBinding.ePassword.setError("Password required");
                    activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.errore);

                    return;
                }
                if (ePasswordIn.length() < 8) {
                    activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.ePassword.setError("Password must be at least 8 characters");
                    return;
                }

                String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
                if (!ePasswordIn.matches(passwordPattern)) {
                    activitySignUpBinding.ePassword.setError("Password must be at least 8 characters");
                    activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.ePassword.setError("The password must contain an uppercase and lowercase letter, a number, and a special symbol\n");
                    return;
                }


                if (PhoneIn.isEmpty()) {
                    activitySignUpBinding.Phone.setError("Phone number required");
                    activitySignUpBinding.Phone.setBackgroundResource(R.drawable.errore);

                    return;
                }


                if (!PhoneIn.matches("^[0-9]+$")) {
                    activitySignUpBinding.Phone.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.Phone.setError("Please enter a valid phone number");
                    return;
                }

                if (PhoneIn.length() != 10) {
                    activitySignUpBinding.Phone.setBackgroundResource(R.drawable.errore);

                    activitySignUpBinding.Phone.setError("The phone number must consist of 10 digits");
                    return;
                }

                if (!activitySignUpBinding.ePassword.getText().toString().equals(activitySignUpBinding.ePassword1.getText().toString())) {

                    activitySignUpBinding.ePassword1.setError("كلمة المرور غير متطابقة");
                    activitySignUpBinding.ePassword1.setBackgroundResource(R.drawable.errore);

                }

                int phoneIn = Integer.parseInt(PhoneIn);
                farmer = new Farmer(eUserIn, ePasswordIn, phoneIn, 1234, nameIn, null, "");

                uploadFarmer(farmer);
//                    // استدعاء الدالة المبسطة
//                FirebaseDatabaseHelper.getInstance().addFarmer(farmer, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(boolean success, Exception exception) {
//                            runOnUiThread(() -> {
//                                if (success) {
//                                    Toast.makeText(Sign_up.this, "✅ تم تسجيل المزارع بنجاح", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(Sign_up.this, MainActivity_Main.class);
//                                    intent.putExtra("USER_NAME2", eUserIn);
//                                    startActivity(intent);
//                                } else {
//                                    if (exception instanceof FirebaseAuthWeakPasswordException) {
//                                        activitySignUpBinding.ePassword.setError("كلمة المرور ضعيفة");
//                                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
//                                        activitySignUpBinding.eUser.setError("اسم المستخدم مستخدم بالفعل");
//                                    } else {
//                                        Toast.makeText(Sign_up.this, "❌ فشل التسجيل: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    });

//                myViewModel.getAllFarmerByUser(eUserIn).observe(Sign_up.this, farmers -> {
//
//
//                    if ( farmers.isEmpty()) {
//                        int phoneIn1 = Integer.parseInt(PhoneIn);
//                        farmer = new Farmer(eUserIn, ePasswordIn, phoneIn1, 1234, nameIn, null, "");
//                        myViewModel.insertFarmer(farmer);
//
//
//                        Intent intent = new Intent(Sign_up.this, MainActivity_Main.class);
//                        intent.putExtra("USER_NAME2", eUserIn);
//
//                        startActivity(intent);
//                        myViewModel.addNotification("Account Setup Successful!", "Your account has been created!", R.drawable.created);
//
//
//                    }
//                   else {
//                        activitySignUpBinding.eUser.setError("The username has already been used");
//
//                    }
//
//
//                });

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

//        activitySignUpBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
//
//        {
//            if (isChecked) {
//                String username2 = activitySignUpBinding.eUser.getText().toString();
//                String password2 = activitySignUpBinding.ePassword.getText().toString();
//                String phone2 = activitySignUpBinding.ePassword.getText().toString();
//                String name2 = activitySignUpBinding.ePassword.getText().toString();
//
//                if (!username2.isEmpty() && !password2.isEmpty() && !phone2.isEmpty() && !name2.isEmpty()) {
//                    editor.putBoolean("rememberMe1", true);
//                    editor.putString("username1", username2);
//                    editor.putString("password1", password2);
//                    editor.putString("phone1", phone2);
//                    editor.putString("name1", name2);
//                    editor.apply();
//                } else {
//                    activitySignUpBinding.checkBox.setChecked(false);
//                }
//            } else {
//                editor.clear();
//                editor.apply();
//            }
//        });

        activitySignUpBinding.icEyeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activitySignUpBinding.ePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    activitySignUpBinding.ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff.setBackgroundResource(R.drawable.ic_eye_off);
                } else {
                    activitySignUpBinding.ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff.setBackgroundResource(R.drawable.eye);
                }

                activitySignUpBinding.ePassword.setSelection(activitySignUpBinding.ePassword.getText().length());
            }
        });
        activitySignUpBinding.icEyeOff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activitySignUpBinding.ePassword1.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    activitySignUpBinding.ePassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff1.setBackgroundResource(R.drawable.ic_eye_off);
                } else {
                    activitySignUpBinding.ePassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activitySignUpBinding.icEyeOff1.setBackgroundResource(R.drawable.eye);
                }

                activitySignUpBinding.ePassword1.setSelection(activitySignUpBinding.ePassword1.getText().length());
            }
        });

    }


    private void uploadFarmer(Farmer farmer) {


        FirebaseDatabaseHelper.getInstance().addFarmer(farmer, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {

            @Override
            public void onComplete(boolean success, Exception exception) {
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(Sign_up.this, "✅ تم تسجيل المزارع بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Sign_up.this, MainActivity_Main.class);
                        intent.putExtra("USER_NAME2", eUserIn);

                        myViewModel.insertFarmer(farmer);

                        startActivity(intent);
                    } else {
                        if (exception instanceof FirebaseAuthWeakPasswordException) {
                            activitySignUpBinding.ePassword.setError("كلمة المرور ضعيفة");
                            activitySignUpBinding.ePassword.setBackgroundResource(R.drawable.errore);

                        } else if (exception instanceof FirebaseAuthUserCollisionException) {
                            activitySignUpBinding.eUser.setError("اسم المستخدم مستخدم بالفعل");
                            activitySignUpBinding.eUser.setBackgroundResource(R.drawable.errore);

                        } else {
                            Toast.makeText(Sign_up.this, "❌ فشل التسجيل: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


}

