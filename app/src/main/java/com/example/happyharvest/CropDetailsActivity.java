package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class CropDetailsActivity extends AppCompatActivity {
    private String userName;
    private TabPagerAdapter adapter;
    private int cropId;
    private ImageView cropImageView;
    private TextView Price_dep;

    private Button bt_Buy;
    private TextView cropNameTextView;
    private TextView cropNameTextView1;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String cropName;
    private TextView priceTextView;
    private String cropDescription;
    private String catigories;
    private String cropUserName;
    private String cropName1;
    private My_View_Model myViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        Price_dep = findViewById(R.id.price_dep);
        bt_Buy = findViewById(R.id.bt_buy);
        cropNameTextView = findViewById(R.id.Crop_name);
        priceTextView = findViewById(R.id.price);
        cropNameTextView1 = findViewById(R.id.Crop_name1);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager1);
        Price_dep.setPaintFlags(Price_dep.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        cropId = getIntent().getIntExtra("COURSE_ID", -1);
        cropUserName = getIntent().getStringExtra("TEACHER_USER_NAME");
        cropName = getIntent().getStringExtra("COURSE_NAME");
        catigories = getIntent().getStringExtra("COURSE_CATEGORIES");
        cropDescription = getIntent().getStringExtra("COURSE_DESCRIPTION");
        userName = getIntent().getStringExtra("USER");
        cropName1 = getIntent().getStringExtra("COURSE_NAME1");
//  cropPrice = getIntent().getIntExtra("COURSE_PRICE", 0);
        byte[] cropImage = getIntent().getByteArrayExtra("COURSE_IMAGE");
        String expertName = getIntent().getStringExtra("TEACHER_NAME");


        Bundle bundle = new Bundle();
        bundle.putInt("COURSE_ID1", cropId);


//        bt_Buy.setText("Enroll Crop - $" + cropPrice);
        myViewModel.isFarmerCropExists(userName, cropId, true).observe((this), isHad -> {
            if (isHad) {
                bt_Buy.setVisibility(View.GONE);

            }
        });


        Fragment targetFragment = new ReviewsFragment();
        targetFragment.setArguments(bundle);
        cropNameTextView.setText(cropName);
        cropNameTextView1.setText(catigories);


        adapter = new TabPagerAdapter(this, cropUserName, cropId, userName, cropDescription);
        viewPager.setAdapter(adapter);
        cropImageView = findViewById(R.id.img_Crop);


//        int rival = cropPrice + cropPrice / 4;
//        priceTextView.setText(String.format("$%d", cropPrice));
//        Price_dep.setText(String.format("$%d", rival));


        if (cropImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(cropImage, 0, cropImage.length);
            cropImageView.setImageBitmap(bitmap);
        }


        bt_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(cropId,userName);
                sheet.show(getSupportFragmentManager(), "FarmingMethodSheet");


//                Intent intent = new Intent(CropDetailsActivity.this, FarmInput.class);
//                intent.putExtra("USER", userName);
//                intent.putExtra("crop", cropId);
////                intent.putExtra("PRICE", cropPrice);
//                startActivity(intent);
            }
        });
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("About");
                    break;
                case 1:
                    tab.setText("Steps");
                    break;
                case 2:
                    tab.setText("Reviews");
                    break;
            }
        }).attach();

    }
}