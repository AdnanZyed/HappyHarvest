package com.example.happyharvest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Edit_Crop_Activity extends AppCompatActivity {
    
    private My_View_Model myViewModel;
    private DatabaseReference databaseReference;
    private String cropId;
    private Crop currentCrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_crop);
        int id = getIntent().getIntExtra("ID", -1);
        if (id == -1) {
            Toast.makeText(this, "Invalid crop ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        cropId = String.valueOf(id);
        databaseReference = FirebaseDatabase.getInstance().getReference("crops");
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        loadCurrentCropData();
        ViewPager2 viewPager = findViewById(R.id.view_pager1);
        TabLayout tabLayout = findViewById(R.id.tabs1);
        Button btnUpdateCrop = findViewById(R.id.btn_save_CropA);
        btnUpdateCrop.setText("Update Crop");
        btnUpdateCrop.setBackgroundResource(R.drawable.btn_update);
        btnUpdateCrop.setOnClickListener(v -> updateCrop());

        CropPagerAdapter_Edit adapter = new CropPagerAdapter_Edit(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("Basic Info"); break;
                        case 1: tab.setText("Soil & Irrigation"); break;
                        case 2: tab.setText("Environment"); break;
                        case 3: tab.setText("Care"); break;
                        case 4: tab.setText("Images"); break;
                    }
                }).attach();
    }

    private void loadCurrentCropData() {
        try {
            int id = Integer.parseInt(cropId);

            myViewModel.getAllCropsById(id).observe(this, crop -> {
                if (crop != null) {
                    this.currentCrop = (Crop) crop.get(0);
                    Log.d("EditCrop", "Crop loaded from DB: " + crop.toString());
                } else {
                    loadFromFirebase(id);
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid crop ID format", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadFromFirebase(int id) {
        databaseReference.child(cropId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                currentCrop = task.getResult().getValue(Crop.class);
                if (currentCrop != null) {
                    currentCrop.setCrop_ID(id);
                    myViewModel.insertCrop(currentCrop);
                    Log.d("EditCrop", "Crop loaded from Firebase: " + currentCrop.toString());
                }
            } else {
                Toast.makeText(this, "Failed to load crop data", Toast.LENGTH_SHORT).show();
                Log.e("EditCrop", "Error loading crop from Firebase", task.getException());
            }
        });
    }

    private void updateCrop() {
        BasicInfoFragmentEdit basicInfoFragment = (BasicInfoFragmentEdit) getSupportFragmentManager().findFragmentByTag("f0");
        SoilIrrigationFragmentEdit soilIrrigationFragment = (SoilIrrigationFragmentEdit) getSupportFragmentManager().findFragmentByTag("f1");
        EnvironmentFragmentEdit environmentFragment = (EnvironmentFragmentEdit) getSupportFragmentManager().findFragmentByTag("f2");
        CareFragmentEdit careFragment = (CareFragmentEdit) getSupportFragmentManager().findFragmentByTag("f3");
        ImagesFragmentEdit imagesFragment = (ImagesFragmentEdit) getSupportFragmentManager().findFragmentByTag("f4");

        if (basicInfoFragment == null || soilIrrigationFragment == null ||
                environmentFragment == null || careFragment == null || imagesFragment == null) {
            Toast.makeText(this, "Error loading form data", Toast.LENGTH_SHORT).show();
            return;
        }

        currentCrop = basicInfoFragment.getBasicInfo(currentCrop);
        currentCrop = soilIrrigationFragment.getSoilIrrigationInfo(currentCrop);
        currentCrop = environmentFragment.getEnvironmentInfo(currentCrop);
        currentCrop = careFragment.getCareInfo(currentCrop);

        byte[] cropImageBytes = imagesFragment.getCropImageBytes();
        byte[] cropImageBytesC = imagesFragment.getCropImageBytesC();

        if (currentCrop.getCrop_NAME().isEmpty() || currentCrop.getDescription().isEmpty() ||
                currentCrop.getExpert_USER_Name().isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(cropId).setValue(currentCrop)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        myViewModel.updateCrop(currentCrop);
                        Toast.makeText(this, "Crop updated successfully", Toast.LENGTH_SHORT).show();
                        Log.d("EditCrop", "Crop updated successfully: " + currentCrop.toString());
                        myViewModel.addNotification("Crop Updated",
                                "Crop " + currentCrop.getCrop_NAME() + " has been updated",
                                R.drawable.ic_calendar);
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to update crop", Toast.LENGTH_SHORT).show();
                        Log.e("EditCrop", "Error updating crop", task.getException());
                    }
                });
    }
}