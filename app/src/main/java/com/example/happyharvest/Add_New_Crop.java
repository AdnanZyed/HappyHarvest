package com.example.happyharvest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Add_New_Crop extends AppCompatActivity {
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_crop);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        ViewPager2 viewPager = findViewById(R.id.view_pager1);
        TabLayout tabLayout = findViewById(R.id.tabs1);

        Button btnSaveCrop = findViewById(R.id.btn_save_CropA);
        btnSaveCrop.setOnClickListener(v -> {
            BasicInfoFragment basicInfoFragment = (BasicInfoFragment) getSupportFragmentManager().findFragmentByTag("f0");
            SoilIrrigationFragment soilIrrigationFragment = (SoilIrrigationFragment) getSupportFragmentManager().findFragmentByTag("f1");
            EnvironmentFragment environmentFragment = (EnvironmentFragment) getSupportFragmentManager().findFragmentByTag("f2");
            CareFragment careFragment = (CareFragment) getSupportFragmentManager().findFragmentByTag("f3");
            ImagesFragment imagesFragment = (ImagesFragment) getSupportFragmentManager().findFragmentByTag("f4");

            if (basicInfoFragment == null || soilIrrigationFragment == null ||
                    environmentFragment == null || careFragment == null || imagesFragment == null) {
                Toast.makeText(this, "Error loading form data", Toast.LENGTH_SHORT).show();
                return;
            }

            Crop crop = new Crop();

            crop = basicInfoFragment.getBasicInfo(crop);
            crop = soilIrrigationFragment.getSoilIrrigationInfo(crop);
            crop = environmentFragment.getEnvironmentInfo(crop);
            crop = careFragment.getCareInfo(crop);

            byte[] cropImageBytes = imagesFragment.getCropImageBytes();
            byte[] cropImageBytesC = imagesFragment.getCropImageBytesC();

            if (crop.getCrop_NAME().isEmpty() || crop.getDescription().isEmpty() ||
                    crop.getExpert_USER_Name().isEmpty() || cropImageBytes == null) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            myViewModel.insertCrop(crop);
            Toast.makeText(this, "The crop has been saved successfully", Toast.LENGTH_SHORT).show();

            myViewModel.addNotification("Today's Special Offers", "You get a special promo today!", R.drawable.offered);
            myViewModel.addNotification("New Category Crops!", "Now the 3D design crop is available", R.drawable.offered1);

            finish();
        });
        CropPagerAdapter adapter = new CropPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Basic Info");
                            break;
                        case 1:
                            tab.setText("Soil & Irrigation");
                            break;
                        case 2:
                            tab.setText("Environment");
                            break;
                        case 3:
                            tab.setText("Care");
                            break;
                        case 4:
                            tab.setText("Images");
                            break;
                    }
                }).attach();
    }

    public My_View_Model getViewModel() {
        return myViewModel;
    }
}