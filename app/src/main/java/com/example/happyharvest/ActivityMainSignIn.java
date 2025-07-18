package com.example.happyharvest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.happyharvest.databinding.ActivityMainSignInBinding;

public class ActivityMainSignIn extends AppCompatActivity {
    ActivityMainSignInBinding binding;
    String EUserIn;
    String EPasswordIn;
    My_View_Model myViewModel;
    String farmer_name_user;
    String farmer_password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainSignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isRemembered = sharedPreferences.getBoolean("rememberMe", false);
        if (isRemembered) {
            binding.eUserIn.setText(sharedPreferences.getString("username", ""));
            binding.ePasswordIn.setText(sharedPreferences.getString("password", ""));
            binding.checkBoxIn.setChecked(true);
        }




//        myViewModel.getAllCrops().observe(this, crops -> {
//            Toast.makeText(this, crops.toString(), Toast.LENGTH_SHORT).show();
//        });

     // myViewModel.syncWithFirebase();



        binding.Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.getAllFarmerByUser(binding.eUserIn.getText().toString()).observe(ActivityMainSignIn.this, farmers -> {
                    if (farmers != null && !farmers.isEmpty() && !binding.eUserIn.getText().toString().isEmpty() && binding.eUserIn.getText().toString() != null && binding.eUserIn.getText().toString() != "") {

                        Intent intent = new Intent(ActivityMainSignIn.this, ForgetPassword1.class);
                        intent.putExtra("USER", binding.eUserIn.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ActivityMainSignIn.this, "This user does not exist!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        binding.eUserIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.eUserIn.setBackgroundResource(R.drawable.shap_selected);
                binding.ePasswordIn.setBackgroundResource(R.drawable.shape_non_selected);

            }
        }); binding.ePasswordIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.ePasswordIn.setBackgroundResource(R.drawable.shap_selected);
                binding.eUserIn.setBackgroundResource(R.drawable.shape_non_selected);

            }
        });



        binding.SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EUserIn = binding.eUserIn.getText().toString().trim();
                EPasswordIn = binding.ePasswordIn.getText().toString().trim();


                if (EUserIn.isEmpty()) {
                    binding.eUserIn.setError("Please enter your username!");
                    binding.eUserIn.requestFocus();
                    return;
                }

                if (EPasswordIn.isEmpty()) {
                    binding.ePasswordIn.setError("Please enter password");
                    binding.ePasswordIn.requestFocus();
                    return;
                }

                if (EUserIn.equals("admin") && EPasswordIn.equals("admin")) {
                    Intent intent = new Intent(ActivityMainSignIn.this, MainActivity_Admin.class);
                    startActivity(intent);

                }
                myViewModel.getAllFarmerByUser(EUserIn).observe(ActivityMainSignIn.this, farmers -> {
                    if (farmers != null && !farmers.isEmpty()) {
                        Farmer farmer = farmers.get(0);
                        farmer_name_user = farmer.getFarmer_user_name().toString();
                        farmer_password = farmer.getFarmer_Password().toString();


                        if (EUserIn.equals(farmer_name_user) && EPasswordIn.equals(farmer_password)) {


                            Intent intent = new Intent(ActivityMainSignIn.this, MainActivity_Main.class);
                            intent.putExtra("USER_NAME2", EUserIn);

                            startActivity(intent);
                        } else {

                            Toast.makeText(ActivityMainSignIn.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();


                        }
                    } else {

                        Toast.makeText(ActivityMainSignIn.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();


                    }

                });
            }

        });


        binding.backIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMainSignIn.this, Sign_up.class);
                startActivity(intent);
            }


        });


        binding.checkBoxIn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String username1 = binding.eUserIn.getText().toString();
                String password1 = binding.ePasswordIn.getText().toString();

                if (!username1.isEmpty() && !password1.isEmpty()) {
                    editor.putBoolean("rememberMe", true);
                    editor.putString("username", username1);
                    editor.putString("password", password1);
                    editor.apply();
                } else {
                    binding.checkBoxIn.setChecked(false);
                }
            } else {
                editor.clear();
                editor.apply();
            }
        });
    }


}



