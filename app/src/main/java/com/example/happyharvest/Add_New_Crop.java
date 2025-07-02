package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.PrimaryKey;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_New_Crop extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_A = 1;
    private static final int REQUEST_CODE_IMAGE_C = 2;
    private EditText WateringFrequencyDays, FertilizingFrequencyDays,
            wateringInstructions, FertilizingInstructions, etCropName,
            etDescription, etPrice, etExpertUsername, etImageUrl,
            et_image_urlC, MinArea, Season, PlantingMethod, CropProblems, Pruning_and_guidance,
            LearnMore, OptimalHumidity, OptimalTemperature, LightRequirements, Number_Plant_per_dunum,
            ChemicalPerPlant, OrganicPerPlant, Preparing_irrigation_tools_P, Preparing_irrigation_tools_A,
            Preparing_irrigation_tools_F, Soil_preparation_allowed, Weight_seeds_per_dunum, SeedSpecifications,
            SeedlingPreparation, PlantingDistance, PlantingDepth,
            InitialIrrigation, DaysToMaturity, High, Mid, Low, TemperatureTolerance, HumidityTolerance;
    private Spinner spCategory, PreferredSoil, AllowedSoil, ForbiddenSoil, PreferredHumidity, AllowedHumidity,
            PreferredIrrigation, AllowedIrrigation, ForbiddenIrrigation, OrganicFertilizer, ChemicalFertilizer,
            ForbiddenHumidity, PreferredTemp, AllowedTemp, ForbiddenTemp,
            PreferredAbundance, AllowedAbundance, ForbiddenAbundance, Previous_crop_preferred,
            Previous_crop_allowed, Previous_crop_forbidden, Soil_preparation_Favorite;
    private ImageView ivCropImage, iv_crop_imageC;
    private Button btnSelectImage, btn_select_imageC, btnSaveCrop, btnLoadImageFromUrl, btn_load_image_from_urlC;
    private byte[] cropImageBytes;
    private byte[] cropImageBytesC;
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_crop);

        etCropName = findViewById(R.id.et_Crop_nameA);
        spCategory = findViewById(R.id.sp_categoryA);
        etDescription = findViewById(R.id.et_Crop_descriptionA);
        // etPrice = findViewById(R.id.et_priceA);
        etExpertUsername = findViewById(R.id.et_expert_usernameA);
        PreferredSoil = findViewById(R.id.sp_preferredSoil);
        AllowedSoil = findViewById(R.id.sp_allowedSoil);
        ForbiddenSoil = findViewById(R.id.sp_forbiddenSoil);
        PreferredIrrigation = findViewById(R.id.sp_forbiddenSoil);
        AllowedIrrigation = findViewById(R.id.sp_allowedIrrigation);
        ForbiddenIrrigation = findViewById(R.id.sp_forbiddenIrrigation);
        MinArea = findViewById(R.id.minArea);
        Season = findViewById(R.id.season);
        PreferredHumidity = findViewById(R.id.sp_preferredHumidity);
        AllowedHumidity = findViewById(R.id.sp_allowedHumidity);
        ForbiddenHumidity = findViewById(R.id.sp_forbiddenHumidity);
        PreferredTemp = findViewById(R.id.sp_preferredTemp);
        AllowedTemp = findViewById(R.id.sp_allowedTemp);
        ForbiddenTemp = findViewById(R.id.sp_forbiddenTemp);
        WateringFrequencyDays = findViewById(R.id.wateringFrequencyDays);
        FertilizingFrequencyDays = findViewById(R.id.fertilizingFrequencyDays);
        wateringInstructions = findViewById(R.id.wateringInstructions);
        wateringInstructions = findViewById(R.id.wateringInstructions);
        FertilizingInstructions = findViewById(R.id.fertilizingInstructions);
        PlantingMethod = findViewById(R.id.plantingMethod);
        CropProblems = findViewById(R.id.cropProblems);
        Pruning_and_guidance = findViewById(R.id.pruning_and_guidance);
        PreferredAbundance = findViewById(R.id.sp_preferredAbundance);
        AllowedAbundance = findViewById(R.id.sp_allowedAbundance);
        ForbiddenAbundance = findViewById(R.id.sp_forbiddenAbundance);
        LearnMore = findViewById(R.id.learnMore);
        OptimalHumidity = findViewById(R.id.optimalHumidity);
        OptimalTemperature = findViewById(R.id.optimalTemperature);
        LightRequirements = findViewById(R.id.lightRequirements);
        Number_Plant_per_dunum = findViewById(R.id.number_Plant_per_dunum);
        OrganicFertilizer = findViewById(R.id.sp_organicFertilizer);
        ChemicalPerPlant = findViewById(R.id.chemicalPerPlant);
        ChemicalFertilizer = findViewById(R.id.chemicalFertilizer);
        OrganicPerPlant = findViewById(R.id.organicPerPlant);
        Previous_crop_preferred = findViewById(R.id.sp_previous_crop_preferred);
        Previous_crop_allowed = findViewById(R.id.sp_previous_crop_allowed);
        Previous_crop_forbidden = findViewById(R.id.sp_Previous_crop_forbidden);
        Preparing_irrigation_tools_P = findViewById(R.id.Preparing_irrigation_tools_P);
        Preparing_irrigation_tools_A = findViewById(R.id.Preparing_irrigation_tools_A);
        Preparing_irrigation_tools_F = findViewById(R.id.Preparing_irrigation_tools_F);
        Soil_preparation_allowed = findViewById(R.id.soil_preparation_allowed);


        btnSaveCrop = findViewById(R.id.btn_save_CropA);
        btnLoadImageFromUrl = findViewById(R.id.btn_load_image_from_urlA);
        etImageUrl = findViewById(R.id.et_image_urlA);
        ivCropImage = findViewById(R.id.iv_Crop_imageA);
        btnSelectImage = findViewById(R.id.btn_select_imageA);
        btn_load_image_from_urlC = findViewById(R.id.btn_load_image_from_urlC);
        et_image_urlC = findViewById(R.id.et_image_urlC);
        iv_crop_imageC = findViewById(R.id.iv_Crop_imageC);
        btn_select_imageC = findViewById(R.id.btn_select_imageC);






        Weight_seeds_per_dunum = findViewById(R.id.weight_seeds_per_dunum);
        SeedSpecifications = findViewById(R.id.seedSpecifications);
        SeedlingPreparation = findViewById(R.id.seedlingPreparation);
        PlantingDistance = findViewById(R.id.plantingDistance);
        PlantingDepth = findViewById(R.id.plantingDepth);
        InitialIrrigation = findViewById(R.id.initialIrrigation);
        DaysToMaturity = findViewById(R.id.daysToMaturity);
        High = findViewById(R.id.high);
        Mid = findViewById(R.id.mid);
        Low = findViewById(R.id.low);
        TemperatureTolerance = findViewById(R.id.temperatureTolerance);
        HumidityTolerance = findViewById(R.id.humidityTolerance);

        Soil_preparation_Favorite = findViewById(R.id.sp_soil_preparation_Favorite);
        Soil_preparation_Favorite = findViewById(R.id.sp_soil_preparation_Favorite);




        FertilizingInstructions = findViewById(R.id.fertilizingInstructions);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        btnSelectImage.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_A));
        btn_select_imageC.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_C));
        btnLoadImageFromUrl.setOnClickListener(v -> loadImageFromUrl(etImageUrl, ivCropImage, true));
        btn_load_image_from_urlC.setOnClickListener(v -> loadImageFromUrl(et_image_urlC, iv_crop_imageC, false));
        btnSaveCrop.setOnClickListener(v -> saveCrop());
    }

//WeatherManager.getInstance().fetchWeather(requireContext(), new WeatherManager.WeatherCallback() {
//        @Override
//        public void onWeatherLoaded(WeatherResponse response) {
//            // ...
//        }
//
//        @Override
//        public void onFailure(Throwable t) {
//            // ...
//        }
//    });

    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void loadImageFromUrl(EditText urlField, ImageView imageView, boolean isMainImage) {
        String imageUrl = urlField.getText().toString().trim();
        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "Please enter the image link", Toast.LENGTH_SHORT).show();
            return;
        }

        Picasso.get()
                .load(imageUrl)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setDrawingCacheEnabled(true);
                        Bitmap bitmap = imageView.getDrawingCache();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        if (isMainImage) {
                            cropImageBytes = stream.toByteArray();
                        } else {
                            cropImageBytesC = stream.toByteArray();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Add_New_Crop.this, "Image failed to load", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                if (requestCode == REQUEST_CODE_IMAGE_A) {
                    ivCropImage.setImageURI(imageUri);
                    cropImageBytes = stream.toByteArray();
                } else if (requestCode == REQUEST_CODE_IMAGE_C) {
                    iv_crop_imageC.setImageURI(imageUri);
                    cropImageBytesC = stream.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveCrop() {
        String cropName = etCropName.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();
        String description = etDescription.getText().toString().trim();
        String expertUsername = etExpertUsername.getText().toString().trim();
        String preferredSoil = PreferredSoil.getSelectedItem().toString().trim();
        String allowedSoil = AllowedSoil.getSelectedItem().toString().trim();
        String forbiddenSoil = ForbiddenSoil.getSelectedItem().toString().trim();
        String preferredIrrigation = PreferredIrrigation.getSelectedItem().toString().trim();
        String allowedIrrigation = AllowedIrrigation.getSelectedItem().toString().trim();
        String forbiddenIrrigation = ForbiddenIrrigation.getSelectedItem().toString().trim();

        // التحقق من الحقول الأساسية
        if (cropName.isEmpty() || description.isEmpty() || expertUsername.isEmpty() || cropImageBytes == null) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Crop crop = new Crop();
        crop.setCrop_NAME(cropName);
        crop.setCategorie(category);
        crop.setDescription(description);
        crop.setExpert_USER_Name(expertUsername);
        crop.setPreferredSoil(preferredSoil);
        crop.setAllowedSoil(allowedSoil);
        crop.setForbiddenSoil(forbiddenSoil);
        crop.setPreferredIrrigation(preferredIrrigation);
        crop.setAllowedIrrigation(allowedIrrigation);
        crop.setForbiddenIrrigation(forbiddenIrrigation);

        // إضافة القيم من الحقول المتبقية
        crop.setMinArea(Double.parseDouble(MinArea.getText().toString().trim()));
        crop.setSeason(Season.getText().toString().trim());
        crop.setPreferredHumidity(PreferredHumidity.getSelectedItem().toString());
        crop.setAllowedHumidity(AllowedHumidity.getSelectedItem().toString());
        crop.setForbiddenHumidity(ForbiddenHumidity.getSelectedItem().toString());
        crop.setPreferredTemp(PreferredTemp.getSelectedItem().toString());
        crop.setAllowedTemp(AllowedTemp.getSelectedItem().toString());
        crop.setForbiddenTemp(ForbiddenTemp.getSelectedItem().toString());
        crop.setWateringFrequencyDays(Integer.parseInt(WateringFrequencyDays.getText().toString().trim()));
        crop.setFertilizingFrequencyDays(Integer.parseInt(FertilizingFrequencyDays.getText().toString().trim()));
        crop.setWateringInstructions(wateringInstructions.getText().toString().trim());
        crop.setFertilizingInstructions(FertilizingInstructions.getText().toString().trim());
        crop.setPlantingMethod(PlantingMethod.getText().toString().trim());
        crop.setCropProblems(CropProblems.getText().toString().trim());
        crop.setPruning_and_guidance(Pruning_and_guidance.getText().toString().trim());
        crop.setPreferredAbundance(PreferredAbundance.getSelectedItem().toString());
        crop.setAllowedAbundance(AllowedAbundance.getSelectedItem().toString());
        crop.setForbiddenAbundance(ForbiddenAbundance.getSelectedItem().toString());
        crop.setLearnMore(LearnMore.getText().toString().trim());
        crop.setOptimalTemperature(Float.parseFloat(OptimalTemperature.getText().toString().trim()));
        crop.setOptimalHumidity(Float.parseFloat(OptimalHumidity.getText().toString().trim()));
        crop.setLightRequirements(Integer.parseInt(LightRequirements.getText().toString().trim()));
        crop.setNumber_Plant_per_dunum(Integer.parseInt(Number_Plant_per_dunum.getText().toString().trim()));
        crop.setOrganicFertilizer(OrganicFertilizer.getSelectedItem().toString());
        crop.setOrganicPerPlant(Double.parseDouble(OrganicPerPlant.getText().toString().trim()));
        crop.setChemicalFertilizer(ChemicalFertilizer.getSelectedItem().toString());
        crop.setChemicalPerPlant(Double.parseDouble(ChemicalPerPlant.getText().toString().trim()));
        crop.setPrevious_crop_preferred(Previous_crop_preferred.getSelectedItem().toString());
        crop.setPrevious_crop_allowed(Previous_crop_allowed.getSelectedItem().toString());
        crop.setPrevious_crop_forbidden(Previous_crop_forbidden.getSelectedItem().toString());
        crop.setSoil_preparation_Favorite(Soil_preparation_Favorite.getSelectedItem().toString());
        crop.setPreparing_irrigation_tools_F(Preparing_irrigation_tools_F.getText().toString().trim());
        crop.setPreparing_irrigation_tools_P(Preparing_irrigation_tools_P.getText().toString().trim());
        crop.setPreparing_irrigation_tools_A(Preparing_irrigation_tools_A.getText().toString().trim());
        crop.setSoil_preparation_allowed(Soil_preparation_allowed.getText().toString().trim());
        crop.setWeight_seeds_per_dunum(Integer.parseInt(Weight_seeds_per_dunum.getText().toString().trim()));
        crop.setSeedSpecifications(SeedSpecifications.getText().toString().trim());
        crop.setSeedlingPreparation(SeedlingPreparation.getText().toString().trim());
        crop.setPlantingDistance(PlantingDistance.getText().toString().trim());
        crop.setPlantingDepth(PlantingDepth.getText().toString().trim());
        crop.setInitialIrrigation(InitialIrrigation.getText().toString().trim());
        crop.setDaysToMaturity(Integer.parseInt(DaysToMaturity.getText().toString().trim()));
        crop.setHigh(High.getText().toString().trim());
        crop.setMid(Mid.getText().toString().trim());
        crop.setLow(Low.getText().toString().trim());
        crop.setTemperatureTolerance(Float.parseFloat(TemperatureTolerance.getText().toString().trim()));
        crop.setHumidityTolerance(Float.parseFloat(HumidityTolerance.getText().toString().trim()));

        // حفظ الصورة (يمكن إضافة cropImageBytes و cropImageBytesC في الكائن Crop عندك إذا يدعم ذلك)
//        crop.setImage(cropImageBytes);
//        crop.setImageC(cropImageBytesC);

        myViewModel.insertCrop(crop);

        Toast.makeText(this, "The crop has been saved successfully", Toast.LENGTH_SHORT).show();
        myViewModel.addNotification("Today's Special Offers", "You get a special promo today!", R.drawable.offered);
        myViewModel.addNotification("New Category Crops!", "Now the 3D design crop is available", R.drawable.offered1);

        finish();
    }
}
