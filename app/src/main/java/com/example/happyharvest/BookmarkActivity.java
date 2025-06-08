package com.example.happyharvest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
public class BookmarkActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CropsAdapter adapter;
    String user;
    private List<CropStep> farmersList1 = new ArrayList<>();
    My_View_Model myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        recyclerView = findViewById(R.id.recycler_view);
        ImageView imageView = findViewById(R.id.back_icon_enrollB);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        user = getIntent().getStringExtra("USER");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myViewModel.getBookmarkedCropByFarmer(user).observe(this, bookmarkedCrops -> {
            if (bookmarkedCrops != null && !bookmarkedCrops.isEmpty()) {
                farmersList1.clear();

                for (Farmer_Crops farmerCrops : bookmarkedCrops) {
                    myViewModel.getStepsByCropId(farmerCrops.getCrop_ID()).observe(this, crops -> {
                        if (crops != null && !crops.isEmpty()) {
                            farmersList1.addAll(crops);

                            runOnUiThread(() -> {
                                adapter = new CropsAdapter((List<Crop>) this, (Context) farmersList1, user);
                                recyclerView.setAdapter(adapter);
                            });
                        }
                    });
                }

            } else {
                Toast.makeText(this, "No saved crop!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
