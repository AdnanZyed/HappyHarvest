package com.example.happyharvest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExpertProfileActivity extends AppCompatActivity {
    String name;
    String username;
    String reviews;
    String farmerUser;
    String education;
    private My_View_Model myViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);
        TabLayout tab = findViewById(R.id.tab);
        ViewPager2 Pager = findViewById(R.id.Pager);

        ImageView imageView = findViewById(R.id.imag_profile);
        ImageView imageView2 = findViewById(R.id.back_icon);
        TextView Name = findViewById(R.id.expert_name);
        TextView cropsCount = findViewById(R.id.textView1);
        TextView farmersCount = findViewById(R.id.textView2);
        TextView reviewsCount = findViewById(R.id.textView3);
        TextView Magor = findViewById(R.id.magor);
        Bundle bundle = getIntent().getExtras();
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        if (bundle != null) {
            name = bundle.getString("TEACHER_NAME_TEXT_VIEW");
            username = bundle.getString("TEACHER_USER_NAME_TEXT_VIEW");
            education = bundle.getString("EDUCATION_TEXT_VIEW");
            farmerUser = bundle.getString("STUDENT_USER");


            Name.setText(name);
            Magor.setText(education);

            byte[] bitmapBytes = bundle.getByteArray("BITMAP");
            if (bitmapBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                imageView.setImageBitmap(bitmap);
            }

        }
        myViewModel.getFarmersByExpert(username).observe(this, farmers -> {
            int farmerSize = farmers.size();
            String farmerSizeS = farmerSize + "";
            farmersCount.setText(farmerSizeS);
        });
        myViewModel.getAllReviewsByCropIdT(username).observe(this, reviews -> {

            int reviewsSise = reviews.size();
            String farmerSizeS = reviewsSise + "";
            reviewsCount.setText(farmerSizeS);
        });

        myViewModel.getAllcropsByExpert_USER_Name(username).observe(this, crops -> {
            int cropsSize = crops.size();
            String cropsSizeS = cropsSize + "";
            cropsCount.setText(cropsSizeS);
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        Tab_profile_Adapter adapter = new Tab_profile_Adapter(this, username, farmerUser);
        Pager.setAdapter(adapter);

        new TabLayoutMediator(tab, Pager, (tabL, position) -> {
            switch (position) {
                case 0:
                    tabL.setText("Crops");
                    break;
                case 1:
                    tabL.setText("Farmers");
                    break;
                case 2:
                    tabL.setText("Reviews");
                    break;
            }
        }).attach();


    }

}