package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyharvest.databinding.ActivityMainAdminBinding;


public class MainActivity_Admin extends AppCompatActivity {
     ActivityMainAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.Card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Admin.this, Add_New_Crop.class);
                startActivity(intent);
            }
        });

        binding.Card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Admin.this, Delete_Crop.class);
                startActivity(intent);
            }
        });
        binding.Card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Admin.this, UpdateCrop1.class);
                startActivity(intent);
            }
        });


    }
}