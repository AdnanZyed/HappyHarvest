package com.example.happyharvest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class EnrollCodeActivity extends AppCompatActivity {
    private EditText otpDigit1, otpDigit2, otpDigit3, otpDigit4;
    private String expert_USER_Name,userName;
    private int otpCodeInt,price,cropId,card_num;
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_code);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        otpDigit1 = findViewById(R.id.otp_digit_1);
        otpDigit2 = findViewById(R.id.otp_digit_2);
        ImageView imageView = findViewById(R.id.back_icon_enrollCoude);
        otpDigit3 = findViewById(R.id.otp_digit_3);
        otpDigit4 = findViewById(R.id.otp_digit_4);

        otpDigit1.addTextChangedListener(new OTPTextWatcher(otpDigit1, otpDigit2));
        otpDigit2.addTextChangedListener(new OTPTextWatcher(otpDigit2, otpDigit3));
        otpDigit3.addTextChangedListener(new OTPTextWatcher(otpDigit3, otpDigit4));
        otpDigit4.addTextChangedListener(new OTPTextWatcher(otpDigit4, null));

        Button continueButton = findViewById(R.id.bt_buy1);
        userName = getIntent().getStringExtra("USER");
        cropId = getIntent().getIntExtra("COURSE_ID", -1);
        price = getIntent().getIntExtra("PRICE", -1);

        continueButton.setText("Enroll Crop - $" + price);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        continueButton.setOnClickListener(v -> {
            String digit1 = otpDigit1.getText().toString().trim();
            String digit2 = otpDigit2.getText().toString().trim();
            String digit3 = otpDigit3.getText().toString().trim();
            String digit4 = otpDigit4.getText().toString().trim();


            if (digit1.isEmpty() || digit2.isEmpty() || digit3.isEmpty() || digit4.isEmpty()) {
                Toast.makeText(this, "Please enter all 4 digits of the OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            String otpCode = digit1 + digit2 + digit3 + digit4;
            otpCodeInt = Integer.parseInt(otpCode);

            if (otpCode.length() != 4) {
                Toast.makeText(this, "OTP Code must be 4 digits.", Toast.LENGTH_SHORT).show();
                return;
            }
            myViewModel.getAllFarmerByUser(userName).observe(this, farmers -> {
                if (farmers != null && !farmers.isEmpty()) {
                    card_num = farmers.get(0).getCard_Number();

                    if (otpCodeInt == card_num) {

                            showCustomDialog();


                        myViewModel.addNotification("Credit Card Connected!", "Credit Card has been linked!", R.drawable.connected_card);

                    } else {
                        Toast.makeText(this, "The number does not match" + otpCode, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No farmer data found.", Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);


        ImageView dialogImage = dialog.findViewById(R.id.dialog_image);
        TextView mainText = dialog.findViewById(R.id.dialog_main_text);
        TextView secondaryText = dialog.findViewById(R.id.dialog_secondary_text);
        Button viewCropButton = dialog.findViewById(R.id.btn_view_Crop);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel);

        mainText.setText("Enroll Crop Successful!");
        secondaryText.setText("You have successfully made payment and enrolled the crop");
        dialogImage.setImageResource(R.drawable.unnamed);


        myViewModel.getAllCropsById(cropId).observe(this, crops -> {
            expert_USER_Name = crops.get(0).getExpert_USER_Name();


        });
        viewCropButton.setOnClickListener(v -> {

            myViewModel.isFarmerCropExists(userName, cropId, true).observe((this), isHad -> {
                myViewModel.isFarmerCropExistsC(userName, cropId, true).observe((this), isHadC -> {
                    myViewModel.isFarmerCropExistsB(userName, cropId, true).observe((this), isHadb -> {
                        if (!isHadC && !isHadb) {
                      //      Farmer_Crops farmerCrop = new Farmer_Crops(userName, cropId, isHadb, true, false, 0);
                           // myViewModel.insertFarmerCrop(farmerCrop);
                            Farmer_Expert farmerExpert = new Farmer_Expert(0, userName, expert_USER_Name);
                            myViewModel.insertFarmerExpert(farmerExpert);
//                            Intent intent=new Intent(EnrollCodeActivity.this,StepsActivity.class);
//                            intent.putExtra("COURSE_ID",cropId);
//                            intent.putExtra("USER",userName);
//
//                            startActivity(intent);
                            Toast.makeText(this, "Purchase completed successfully.", Toast.LENGTH_SHORT).show();
                            myViewModel.addNotification("Payment Successful!", "You have made a crop payment", R.drawable.connected_card);


                        } else if (isHadC || isHadb) {
                      //      Farmer_Crops farmerCrop = new Farmer_Crops(userName, cropId, isHadb, true, false, 0);
                          //  myViewModel.updateCropFarmer(farmerCrop);
                            myViewModel.addNotification("Payment Successful!", "You have made a crop payment", R.drawable.connected_card);

                        }

                    });


                    otpDigit1.setText("");
                    otpDigit2.setText("");
                    otpDigit3.setText("");
                    otpDigit4.setText("");
                    dialog.dismiss();
                });
            });
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);

        }
        dialog.show();
    }

    private class OTPTextWatcher implements TextWatcher {
        private final EditText currentEditText;
        private final EditText nextEditText;

        public OTPTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
