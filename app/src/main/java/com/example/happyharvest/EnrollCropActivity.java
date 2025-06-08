package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class EnrollCropActivity extends AppCompatActivity {
    private My_View_Model myViewModel;
    String userName;
    int price;
    private boolean is;
    private boolean is2;
    int cropd;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_crop);
        Button btn_buy = findViewById(R.id.bt_buy2);
        ImageView imageView = findViewById(R.id.back_icon_enrollCrop);
        Button btn_cart = findViewById(R.id.add_cart);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        userName = getIntent().getStringExtra("USER");
        price = getIntent().getIntExtra("PRICE", -1);
        cropd = getIntent().getIntExtra("COURSE_ID", -1);
//        myViewModel.isFarmerCropExists(userName, cropd, true).observe((EnrollCropActivity.this), isHad -> {
//            is = isHad;
//        });
        myViewModel.isFarmerCropExistsC(userName, cropd, true).observe((EnrollCropActivity.this), isHadc -> {
            if (isHadc) {
                btn_cart.setBackgroundResource(R.drawable.delete_btn);
                btn_cart.setTextColor(R.color.white);
                btn_cart.setText("Delete Card");
            }


        });
//        if (is) {
//            btn_cart.setVisibility(View.GONE);
//            btn_buy.setVisibility(View.GONE);
//
//        }


        btn_buy.setText("Enroll Crop - $" + price);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollCropActivity.this, EnrollCodeActivity.class);
                intent.putExtra("USER", userName);
                intent.putExtra("COURSE_ID", cropd);
                intent.putExtra("PRICE", price);
                startActivity(intent);

            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                myViewModel.isFarmerCropExistsC(userName, cropd, true).observe((EnrollCropActivity.this), isHadc -> {
                    myViewModel.isFarmerCropExistsB(userName, cropd, true).observe((EnrollCropActivity.this), isHadb -> {
                        myViewModel.isFarmerCropExists(userName, cropd, true).observe((EnrollCropActivity.this), isHad -> {

                            if (!isHadc && !isHadb && !isHad) {
                             //   Farmer_Crops farmerCrop = new Farmer_Crops(userName, cropd, false, false, true, 0);
                               // myViewModel.insertFarmerCrop(farmerCrop);
                                btn_cart.setBackgroundResource(R.drawable.delete_btn);
                                btn_cart.setTextColor(R.color.white);
                                btn_cart.setText("Delete Card");
                            } else if (isHadb && !isHadc) {
                              //  Farmer_Crops farmerCrop = new Farmer_Crops(userName, cropd, true, false, true, 0);
                                btn_cart.setBackgroundResource(R.drawable.delete_btn);
                                btn_cart.setTextColor(R.color.white);
                                btn_cart.setText("Delete Card");
                           //     myViewModel.updateCropFarmer(farmerCrop);
                            } else if (isHadc && !isHadb && !isHad) {

                               // Farmer_Crops farmerCrop = new Farmer_Crops(userName, cropd, false, false, false, 0);
                               // myViewModel.deleteFarmerCrop(farmerCrop);
                                btn_cart.setBackgroundResource(R.drawable.btn_cart);
                                btn_cart.setTextColor(R.color.green);
                                btn_cart.setText("Add New Card");

                            }

                        });
                    });
                });
            }
        });
    }
}