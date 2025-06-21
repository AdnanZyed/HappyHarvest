package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pruning extends AppCompatActivity {

    String user;
    int cropId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruning);

        Intent intent=new Intent();
        user=intent.getStringExtra("USER");
        cropId=intent.getIntExtra("ID",-1);

    }
}