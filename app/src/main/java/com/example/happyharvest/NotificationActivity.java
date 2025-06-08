package com.example.happyharvest;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        ImageView imageView = findViewById(R.id.back_icon_enrollN);
        recyclerView = findViewById(R.id.rv_notifications);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        myViewModel.getAllNotifications().observe(this, notifications -> {
            adapter = new NotificationAdapter(notifications);
            recyclerView.setAdapter(adapter);
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}