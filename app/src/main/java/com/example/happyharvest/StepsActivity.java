package com.example.happyharvest;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        int cropId = getIntent().getIntExtra("COURSE_ID", -1);
        String user = getIntent().getStringExtra("USER");


        TabLayout tabLayout = findViewById(R.id.tabLayout_steps);
        ViewPager2 viewPager = findViewById(R.id.viewPager_steps);
        ImageView imageView = findViewById(R.id.back_icon_step);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewPagerAdapterSteps adapter = new ViewPagerAdapterSteps(this, cropId, user);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Stepe");
                    break;
                case 1:
                    tab.setText("Certificate");
                    break;
            }
        }).attach();

    }
}