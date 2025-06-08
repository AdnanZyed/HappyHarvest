package com.example.happyharvest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happyharvest.databinding.ActivityMainForgotBinding;


public class MainActivity_Forgot extends AppCompatActivity {
     ActivityMainForgotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}