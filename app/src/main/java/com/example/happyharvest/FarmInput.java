package com.example.happyharvest;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FarmInput extends AppCompatActivity {
    private Spinner spinnerGreenhouseType, spinnerVentilation, spinnerSoilType, spinnerIrrigationType, spinnerWaterAvailability, spinnerFertilizerType, spinner_hydroponic_system, spinner_previous_crop, spinner_organic_fertilizer, spinner_chemical_fertilizer, germination_method;
    private String now_typpe, currentDate, result1, farmerUserName, userValue, greenhouse, open_field;
    private double avgTemp1, avgHumidity1, lat, lon;
    private My_View_Model myViewModel;
    private Button btnSaveSettings, btnEvaluate;
    private TextView tvResult, edit_nutrient_solution_TV, Type_of_organic_fertilizer, Type_of_chemical_fertilizer, spinner_hydroponic_system_TV, soiltype, textViewResult, textViewTemp, textViewHumidity;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String> locationPermissionLauncher;
    private Farmer_Crop_Dao farmerCropsDao;
    private int CropId;
    private WeatherResponse currentWeather;
    private List<Crop> selectedCrop;

    private EditText space, edit_nutrient_solution;
    private List<Integer> selectedCropIds = new ArrayList<>();
    private CheckBox checkboxLighting, checkboxHeating, checkboxCooling, checkboxCO2;
    private Switch switchAutomation;
    private Location currentLocation;

    private static final String WEATHER_PREFS = "WeatherPrefs";
    private static final String GREENHOUSE_PREFS = "GreenhousePrefs";
    private static final String API_KEY = "9e269c7c20355e9e8bba48b0ad2cd52c";
    private ImageView searchIcon;
    private LocationCallback locationCallback;

    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_input);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        searchIcon = findViewById(R.id.searchIcon);
        tvResult = findViewById(R.id.tv_result);
        spinnerFertilizerType = findViewById(R.id.spinnerFertilizerType);
        germination_method = findViewById(R.id.germination_method);
        spinner_organic_fertilizer = findViewById(R.id.spinner_organic_fertilizer);
        spinner_chemical_fertilizer = findViewById(R.id.spinner_chemical_fertilizer);
        Type_of_organic_fertilizer = findViewById(R.id.Type_of_organic_fertilizer);
        Type_of_chemical_fertilizer = findViewById(R.id.Type_of_chemical_fertilizer);
        spinnerSoilType = findViewById(R.id.spinner_soil_type);
        spinnerIrrigationType = findViewById(R.id.spinner_irrigation);
        spinner_previous_crop = findViewById(R.id.spinner_previous_crop);
        spinnerWaterAvailability = findViewById(R.id.spinner_water);
        btnEvaluate = findViewById(R.id.btn_evaluate);
        space = findViewById(R.id.space);
        soiltype = findViewById(R.id.SoilType);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        spinnerGreenhouseType = findViewById(R.id.spinner_house_type);
        spinnerVentilation = findViewById(R.id.edit_ventilation);
        checkboxLighting = findViewById(R.id.checkbox_lighting);
        checkboxHeating = findViewById(R.id.checkbox_heating);
        checkboxCooling = findViewById(R.id.checkbox_cooling);
        checkboxCO2 = findViewById(R.id.checkbox_co2);
        switchAutomation = findViewById(R.id.switch_automation);
        btnSaveSettings = findViewById(R.id.btn_Plant_now);
        textViewResult = findViewById(R.id.textViewResult);
        textViewTemp = findViewById(R.id.text_temp);
        textViewHumidity = findViewById(R.id.text_humidity);
        spinner_hydroponic_system_TV = findViewById(R.id.spinner_hydroponic_system_TV);
        spinner_hydroponic_system = findViewById(R.id.spinner_hydroponic_system);
        edit_nutrient_solution_TV = findViewById(R.id.edit_nutrient_solution_TV);
        edit_nutrient_solution = findViewById(R.id.edit_nutrient_solution);


        CropId = getIntent().getIntExtra("ID", 1);

        farmerUserName = getIntent().getStringExtra("USER");

        userValue = getIntent().getStringExtra("resultText");

        if (getIntent().hasExtra("crop")) {
            selectedCropIds.add(getIntent().getIntExtra("crop", 1));

        } else if (getIntent().hasExtra("crops")) {
            selectedCropIds = getIntent().getIntegerArrayListExtra("crops");
        }
        if (getIntent().hasExtra("farming_method")) {
            greenhouse = getIntent().getStringExtra("farming_method");
            if (greenhouse.equals("greenhouse")) {
                spinner_hydroponic_system_TV.setVisibility(View.GONE);
                spinner_hydroponic_system.setVisibility(View.GONE);
                edit_nutrient_solution_TV.setVisibility(View.GONE);
                edit_nutrient_solution.setVisibility(View.GONE);
                now_typpe = "greenhouse";

            }
        }
        if (getIntent().hasExtra("farming_method")) {
            open_field = getIntent().getStringExtra("farming_method");
            if (open_field.equals("open_field") || !userValue.isEmpty()) {
                spinnerGreenhouseType.setVisibility(View.GONE);
                spinnerVentilation.setVisibility(View.GONE);
                checkboxLighting.setVisibility(View.GONE);
                checkboxHeating.setVisibility(View.GONE);
                checkboxCooling.setVisibility(View.GONE);
                checkboxCO2.setVisibility(View.GONE);
                switchAutomation.setVisibility(View.GONE);
                spinner_hydroponic_system_TV.setVisibility(View.GONE);
                spinner_hydroponic_system.setVisibility(View.GONE);
                edit_nutrient_solution_TV.setVisibility(View.GONE);
                edit_nutrient_solution.setVisibility(View.GONE);
                now_typpe = "open_field";
            }
        }
        setupSpinner(spinnerFertilizerType, R.array.fertilizer_types);
        setupSpinner(germination_method, R.array.Germination_method);
        setupSpinner(spinnerSoilType, R.array.soil_types);
        setupSpinner(spinnerIrrigationType, R.array.irrigation_types);
        setupSpinner(spinnerWaterAvailability, R.array.water_availability);
        setupSpinner(spinnerGreenhouseType, R.array.greenhouse_types);
        setupSpinner(spinnerVentilation, R.array.ventilation_types);
        setupSpinner(spinner_organic_fertilizer, R.array.Organic_fertilizer_array);
        setupSpinner(spinner_chemical_fertilizer, R.array.Chemical_fertilizer_array);
//
//        locationCallback = new LocationCallback() {
//
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    Toast.makeText(FarmInput.this, "لا يمكن الحصول على الموقع", Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
//                for (Location location : locationResult.getLocations()) {
//                    currentLocation = location;
//                    evaluateFarm(location);
////                    if (now_typpe.equals("open_field") ) {
////                        evaluateFarm(location);
////                    } else if (now_typpe.equals("greenhouse") ) {
////                        evaluateGreenhouse(location);
////
////                    }
//
//                }
//                fusedLocationClient.removeLocationUpdates(this);
//            }
//        };


//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    Toast.makeText(FarmInput.this, "لا يمكن الحصول على الموقع", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    currentLocation = location;
//                    lat = location.getLatitude();
//                    lon = location.getLongitude();
//                    fetchWeatherDataWithCheck(lat, lon);
//                    fetchHistoricalWeather(lat, lon);
////                    Log.e("Adnan",lat+lon+"");
////                    Toast.makeText(FarmInput.this, lat+lon+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
//
//
////                    if (now_typpe.equals("open_field") ) {
////                        evaluateFarm(location);
////                    } else if (now_typpe.equals("greenhouse") ) {
////                        evaluateGreenhouse(location);
////
////                    }
//
//                }
//                fusedLocationClient.removeLocationUpdates(this);
//            }
//        };


        checkCompatibility();

        soiltype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmInput.this, Soil.class);
                startActivity(intent);
            }
        });

        if (userValue != null && !userValue.isEmpty()) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerSoilType.getAdapter();
            if (adapter != null) {
                int position = adapter.getPosition(userValue);
                if (position >= 0) {
                    spinnerSoilType.setSelection(position - 1);
                }
            }
        }
        if (userValue != null && !userValue.isEmpty()) {
            setSpinnerSelection(spinnerSoilType, userValue);
        }

//
        locationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) {
                        if (isLocationEnabled()) {
                            fetchDataAndEvaluate();
                        } else {
                            showLocationSettingsDialog();
                        }
                    } else {
                        Toast.makeText(this, "يجب منح إذن الموقع للمتابعة", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        myViewModel.getAllCropsById(CropId).observe(this, crop -> {
            String previousCrop = spinner_previous_crop.getSelectedItem().toString();

            result1 = determineCompatibility(previousCrop, crop.get(0).getPrevious_crop_preferred(),
                    crop.get(0).getPrevious_crop_allowed(), crop.get(0).getPrevious_crop_forbidden());
            if (crop != null && !crop.isEmpty()) {
                selectedCrop = new ArrayList<>(crop);
                //    setupComparisonUI();
            } else {
                Toast.makeText(this, "بيانات المحصول غير متوفرة", Toast.LENGTH_SHORT).show();
            }
        });


        btnEvaluate.setOnClickListener(v -> {
            if (space.getText() == null || space.getText().isEmpty()) {
                space.setError("يجب ادخال قيمة مساحة الارض");


            } else {

                checkLocationPermission();

            }
        });
        SelectPrevious();


    }

    private void setupSpinner(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void SelectPrevious() {

        List<Crops> crops = prepareCropsList();

        CropPrevious adapter1 = new CropPrevious(this, crops);
        spinner_previous_crop.setAdapter(adapter1);

        spinner_previous_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Crops selected = (Crops) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "اخترت: " + selected.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "اختر محصولك السابق(الموسم السابق): ", Toast.LENGTH_SHORT).show();


            }
        });
        // التحكم في ظهور أيقونة البحث
        spinner_previous_crop.setOnTouchListener((v, event) -> {
            searchIcon.setVisibility(View.VISIBLE);
            searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.green));

            return false;
        });

        spinner_previous_crop.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                searchIcon.setVisibility(View.GONE);
            }
        });
        spinner_previous_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchIcon.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchIcon.setVisibility(View.GONE);
            }
        });
    }

    private void checkCompatibility() {

        myViewModel.getAllCropsById(CropId).observe(this, crops -> {
            if (crops == null || crops.isEmpty()) {
                tvResult.setText("لا توجد بيانات لهذا المحصول");
                return;
            }

            String preferred = crops.get(0).getPrevious_crop_preferred();
            String allowed = crops.get(0).getPrevious_crop_allowed();
            String forbidden = crops.get(0).getPrevious_crop_forbidden();

            String previousCrop = spinner_previous_crop.getSelectedItem().toString();

            String result = determineCompatibility(previousCrop, preferred, allowed, forbidden);

            tvResult.setText("النتيجة: " + result);

            switch (result) {
                case "مرفوض":
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    break;
                case "مسموح":
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                    break;
                case "مفضل":
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    break;
                default:
                    tvResult.setTextColor(getResources().getColor(android.R.color.black));
                    break;
            }
        });
    }

    private List<Crops> prepareCropsList() {
        List<Crops> crops = new ArrayList<>();

        crops.add(new Crops("لا شيئ للموسم السابق", R.drawable.unnamed, 0));
        crops.add(new Crops("الخس", R.drawable.unnamed, 0));
        crops.add(new Crops("السبانخ", R.drawable.unnamed, 0));
        crops.add(new Crops("الجرجير", R.drawable.unnamed, 0));
        crops.add(new Crops("البقدونس", R.drawable.unnamed, 0));
        crops.add(new Crops("الكزبرة", R.drawable.unnamed, 0));
        crops.add(new Crops("النعناع", R.drawable.unnamed, 0));
        crops.add(new Crops("البرسيم", R.drawable.unnamed, 0));
        crops.add(new Crops("الحلبة", R.drawable.unnamed, 0));

        crops.add(new Crops("الذرة", R.drawable.unnamed, 1));
        crops.add(new Crops("القمح", R.drawable.unnamed, 1));
        crops.add(new Crops("الشعير", R.drawable.unnamed, 1));
        crops.add(new Crops("الشوفان", R.drawable.unnamed, 1));
        crops.add(new Crops("العدس", R.drawable.unnamed, 1));
        crops.add(new Crops("الفول", R.drawable.unnamed, 1));
        crops.add(new Crops("الحمص", R.drawable.unnamed, 1));
        crops.add(new Crops("الفاصوليا", R.drawable.unnamed, 1));

        crops.add(new Crops("الثوم", R.drawable.unnamed, 2));
        crops.add(new Crops("الكراث", R.drawable.unnamed, 2));
        crops.add(new Crops("البصل", R.drawable.unnamed, 2));
        crops.add(new Crops("البطاطا", R.drawable.unnamed, 2));
        crops.add(new Crops("البطاطس", R.drawable.unnamed, 2));
        crops.add(new Crops("الفجل", R.drawable.unnamed, 2));
        crops.add(new Crops("اللفت", R.drawable.unnamed, 2));
        crops.add(new Crops("الجزر", R.drawable.unnamed, 2));

        return crops;
    }

    private String determineCompatibility(String previousCrop, String preferred, String allowed, String forbidden) {
        if (preferred != null && preferred.contains(previousCrop)) {
            return "مفضل";
        } else if (allowed != null && allowed.contains(previousCrop)) {
            return "مسموح";
        } else if (forbidden != null && forbidden.contains(previousCrop)) {
            return "مرفوض";
        } else {
            return "لا توجد بيانات لهذا المحصول";
        }
    }

    private void evaluateFarm(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();
        fetchWeatherDataWithCheck(lat, lon);
        fetchHistoricalWeather(lat, lon);

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String season = getSeasonBasedOnDate(currentDate);

        String soil = spinnerSoilType.getSelectedItem().toString();
        String irrig = spinnerIrrigationType.getSelectedItem().toString();
        String water = spinnerWaterAvailability.getSelectedItem().toString();
        //String GreenhouseType = spinnerWaterAvailability.getSelectedItem().toString();

        float avgTemp = 30;
        double avgMoist = 50 / 100.0;

        if (currentWeather != null) {
            avgTemp = currentWeather.getMain().getTemp();
            avgMoist = currentWeather.getMain().getHumidity() / 100.0;
        }
        double scoreSoil = getPoint(soil, selectedCrop.get(0).getPreferredSoil(), selectedCrop.get(0).getAllowedSoil(), selectedCrop.get(0).getForbiddenSoil());
        double scoreIrr = getPoint(irrig, selectedCrop.get(0).getPreferredIrrigation(), selectedCrop.get(0).getAllowedIrrigation(), selectedCrop.get(0).getForbiddenIrrigation());
        double scoreWater = getPoint(water, selectedCrop.get(0).getPreferredAbundance(), selectedCrop.get(0).getAllowedAbundance(), selectedCrop.get(0).getForbiddenAbundance());
        double scoreTemp = getPoint(categorizeTemp(avgTemp), selectedCrop.get(0).getPreferredTemp(), selectedCrop.get(0).getAllowedTemp(), selectedCrop.get(0).getForbiddenTemp());
        double scoreMoisture = getPoint(categorizeHumidity(avgMoist), selectedCrop.get(0).getPreferredHumidity(), selectedCrop.get(0).getAllowedHumidity(), selectedCrop.get(0).getForbiddenHumidity());


        spinnerGreenhouseType.setVisibility(View.GONE);
        spinnerVentilation.setVisibility(View.GONE);
        checkboxLighting.setVisibility(View.GONE);
        checkboxHeating.setVisibility(View.GONE);
        checkboxCooling.setVisibility(View.GONE);
        checkboxCO2.setVisibility(View.GONE);
        switchAutomation.setVisibility(View.GONE);


        double avgScore = (scoreSoil + scoreIrr + scoreWater + scoreTemp + scoreMoisture) / 5.0;
        int successRate = (int) (avgScore * 100);
        boolean accepted = successRate > 49;


        updateUI(water, irrig, soil, lat, lon, currentDate, season, avgTemp, avgMoist, successRate, accepted);
        // updateResultUI(successRate, accepted);


    }

    private void updateUI(String water, String irrig, String soil1, double lat, double lon, String currentDate, String season,
                          float avgTemp, double avgMoist, int successRate, boolean accepted) {
        runOnUiThread(() -> {
            String locationStr = String.format(Locale.getDefault(), "%.4f, %.4f", lat, lon);
            String tempStr = String.format(Locale.getDefault(), "%.1f°C", avgTemp);
            String moistureStr = String.format(Locale.getDefault(), "%d", (int) (avgMoist * 100)); // بدون %%
            String acceptanceStr = accepted ? "مقبول ✅" : "مرفوض ❌";
            String successStr = successRate + "%";
            String resultText = accepted ? "الظروف مناسبة للزراعة" : "الظروف غير مناسبة للزراعة";

            String weatherDesc = "غير متوفر";
            if (currentWeather != null && !currentWeather.getWeather().isEmpty()) {
            }
            String Previues = result1;
            EvaluationResultDialog dialog = new EvaluationResultDialog(
                    FarmInput.this,
                    locationStr,
                    currentDate,
                    season,
                    tempStr,
                    moistureStr,
                    acceptanceStr,
                    successStr,
                    resultText,
                    accepted
                    , CropId
                    , farmerUserName
                    , Previues
            );
// moistureStr tempStr season  Previues

            dialog.setOnPlantNowListener(() -> {
//                if (!accepted) {
//                    btn_Plant_now.setVisibility(VISIBLE);
//
//
//                }
                //   saveGreenhouseSettings();

                new Thread(() -> {
                    try {

                        Farmer_Crops record = new Farmer_Crops();
                        record.setFarmer_user_name(farmerUserName);
                        record.setCrop_ID(CropId);
                        record.setStartDate(currentDate);
                        record.setLongitude(lon);
                        record.setLatitude(lat);
                        record.setSeason(season);
                        record.setAverage_humidity(Double.parseDouble(moistureStr));
                        record.setPrevious_crop(spinner_previous_crop.getSelectedItem().toString());
                        record.setLand_area(Double.parseDouble(space.getText().toString()));
                        record.setAverageTemperature(avgTemp);
                        record.setSoilType(soil1);
                        record.setIrrigationType(irrig);
                        record.setWaterAvailability(water);
                        record.setSuccessRate(successRate);
                        record.setAccepted(true);
                        record.setChemicalFertilizer(spinner_chemical_fertilizer.getSelectedItem().toString());
                        record.setOrganicFertilizer(spinner_organic_fertilizer.getSelectedItem().toString());
                        record.setSelecting_seeds_or_seedlings(germination_method.getSelectedItem().toString());
                        record.setPriority_previous_crop(result1);
                        myViewModel.insertFarmerCrop(record);

                        // ✅ ثم الرفع إلى Firebase
                        FirebaseDatabaseHelper.getInstance().addFarmerCrop(record, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(boolean success, Exception exception) {
                                runOnUiThread(() -> {
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "✅ تم رفع التقييم إلى Firebase", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "❌ فشل رفع التقييم: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        //  saveRecordInDatabase(record);
                        Thread.sleep(4000);
                        runOnUiThread(() -> {
                            Intent intent = new Intent(FarmInput.this, CropDetailsActivity1.class);
                            intent.putExtra("USER", farmerUserName);
                            intent.putExtra("ID", CropId);
                            startActivity(intent);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                dialog.showProgressBar(true);
                // saveFarmRecord(lat, lon, currentDate, season, avgTemp, avgMoist, successRate);
            });
            dialog.show();
        });
    }


    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            } else {
                Log.w(TAG, "Value not found in spinner: " + value);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }


    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                fetchDataAndEvaluate();
            } else {
                showLocationSettingsDialog();
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("إذن الموقع مطلوب")
                        .setMessage("نحتاج إلى إذن الموقع لتقييم مزرعتك بدقة")
                        .setPositiveButton("موافق", (dialog, which) ->
                                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION))
                        .setNegativeButton("إلغاء", (dialog, which) ->
                                Toast.makeText(this, "لا يمكن التقييم بدون الموقع", Toast.LENGTH_SHORT).show())
                        .show();
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showLocationSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("تفعيل خدمة الموقع")
                .setMessage("يجب تفعيل خدمة الموقع للمتابعة")
                .setPositiveButton("فتح الإعدادات", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    @SuppressLint("MissingPermission")
    private void fetchDataAndEvaluate() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLocation = location;

                            evaluateFarm(location);
//                            if (now_typpe.equals("open_field")) {
//                                evaluateFarm(location);
//                            } else if (now_typpe.equals("greenhouse")) {
//                                evaluateGreenhouse(location);
//
//                            }

                        } else {
                            Toast.makeText(this, "جاري الحصول على الموقع الحالي...", Toast.LENGTH_SHORT).show();
                            requestNewLocationData();
                        }
                    })
                    .addOnFailureListener(this, e -> {
                        Log.e("LocationError", "Error getting location", e);
                        Toast.makeText(this, "خطأ في الحصول على الموقع: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        requestNewLocationData();
                    });
        } catch (SecurityException e) {
            Log.e("LocationError", "Security exception", e);
            Toast.makeText(this, "خطأ أمني في الوصول إلى الموقع", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestNewLocationData() {
        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .setMaxUpdateDelayMillis(10000)
                .build();

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        } catch (SecurityException e) {
            Log.e("LocationError", "Security exception", e);
            Toast.makeText(this, "خطأ أمني في الوصول إلى الموقع", Toast.LENGTH_SHORT).show();
        }
    }

    //استخدمها
    private void fetchHistoricalWeather(double lat, double lon) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        long timestamp = calendar.getTimeInMillis() / 1000;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi service = retrofit.create(WeatherApi.class);
        Call<HistoricalWeatherResponse> call = service.getHistoricalWeather(
                lat, lon, timestamp, API_KEY, "metric", "ar");

        call.enqueue(new Callback<HistoricalWeatherResponse>() {
            @Override
            public void onResponse(Call<HistoricalWeatherResponse> call,
                                   Response<HistoricalWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HistoricalWeatherResponse historicalWeather = response.body();
                    processHistoricalWeatherForCurrentSeason(historicalWeather);
                }
            }

            @Override
            public void onFailure(Call<HistoricalWeatherResponse> call, Throwable t) {
                Log.e("HistoricalWeather", "Failed to fetch historical data", t);
            }
        });
    }


    private void processHistoricalWeatherForCurrentSeason(HistoricalWeatherResponse historicalWeather) {
        if (historicalWeather == null || historicalWeather.getData() == null) return;

        String currentDateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String currentSeason = getSeasonBasedOnDate(currentDateStr);

        double totalTemp = 0;
        double totalHumidity = 0;
        int count = 0;

        for (HistoricalWeatherResponse.HistoricalWeatherData data : historicalWeather.getData()) {

            long timestamp = data.getTimestamp();
            String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp * 1000));

            String season = getSeasonBasedOnDate(dateStr);

            if (season.equalsIgnoreCase(currentSeason)) {
                totalTemp += data.getTemp();
                totalHumidity += data.getHumidity();
                count++;
            }
        }

        if (count > 0) {
            avgTemp1 = totalTemp / count;
            avgHumidity1 = totalHumidity / count;

            Toast.makeText(
                    this,
                    "Avg " + currentSeason + " Temp: " + avgTemp1 + "°C | Humidity: " + avgHumidity1 + "%",
                    Toast.LENGTH_LONG
            ).show();

            SharedPreferences prefs = getSharedPreferences(WEATHER_PREFS, MODE_PRIVATE);
            prefs.edit()
                    .putFloat("avg_historical_temp", (float) avgTemp1)
                    .putFloat("avg_historical_humidity", (float) avgHumidity1)
                    .apply();
        }
    }


//    private double getHistoricalAdjustedScore(double currentScore, float currentTemp,
//                                              float currentHumidity) {
//        SharedPreferences prefs = getSharedPreferences(WEATHER_PREFS, MODE_PRIVATE);
//        float avgHistoricalTemp = prefs.getFloat("avg_historical_temp", currentTemp);
//        float avgHistoricalHumidity = prefs.getFloat("avg_historical_humidity", currentHumidity);
//
//        double tempAdjustment = 1 - (Math.abs(currentTemp - avgHistoricalTemp) / 20);
//        double humidityAdjustment = 1 - (Math.abs(currentHumidity - avgHistoricalHumidity) / 50);
//
//        return currentScore * (0.8 + 0.2 * (tempAdjustment + humidityAdjustment) / 2);
//    }


    private String getSeasonBasedOnDate(String dateStr) {
        int month = Integer.parseInt(dateStr.substring(5, 7));
        if (month >= 3 && month <= 5) return "spring";
        else if (month >= 6 && month <= 8) return "summer";
        else if (month >= 9 && month <= 11) return "autumn";
        else return "winter";
    }


    private String categorizeTemp(float temp) {
        if (temp < 5f) return "Cool";
        else if (temp <= 30f) return "Mild";
        else return "Hot";
    }

    private String categorizeHumidity(double humidity) {
        if (humidity < 0.3) return "low";
        else if (humidity <= 0.7) return "moderate";
        else return "High";
    }

    private double getPoint(String input, String pref, String allowed, String forbidden) {
        if (input.equalsIgnoreCase(pref)) return 1.0;
        if (input.equalsIgnoreCase(allowed)) return 0.5;
        if (input.equalsIgnoreCase(forbidden)) return 0.0;
        return 0.0;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void fetchWeatherDataWithCheck(double lat, double lon) {
        if (!isNetworkAvailable()) {
            WeatherResponse cached = getCachedWeatherData();
            if (cached != null) {
                updateWeatherUI(cached);
                showToast("بيانات مخزنة - قد لا تكون حديثة");
            } else {
                showToast("لا يوجد اتصال بالإنترنت");
            }
            return;
        }
        fetchWeatherData(lat, lon);
    }


    private void handleSuccessfulWeatherResponse(WeatherResponse weather) {
        currentWeather = weather;

        cacheWeatherData(weather);

        updateWeatherUI(weather);


//        if (currentLocation != null && selectedCrop != null) {
//            evaluateFarmWithWeather(
//                    currentLocation.getLatitude(),
//                    currentLocation.getLongitude(),
//                    weather.getMain().getTemp(),
//                    weather.getMain().getHumidity() / 100.0
//            );
//        }
    }

    private void fetchWeatherData(double lat, double lon) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi service = retrofit.create(WeatherApi.class);

        Call<WeatherResponse> call = service.getCurrentWeather(
                lat, lon, API_KEY, "metric", "ar");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (!response.isSuccessful()) {
                    handleWeatherApiError(response);
                    return;
                }

                WeatherResponse weather = response.body();
                if (isValidWeatherData(weather)) {
                    handleSuccessfulWeatherResponse(weather);
                } else {
                    showToast("بيانات الطقس غير صالحة");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "فشل الاتصال: " + t.getMessage());
                showToast("تعذر الاتصال بخادم الطقس");
                tryFallbackToCachedData();
            }
        });
    }

    private void handleWeatherApiError(Response<WeatherResponse> response) {
        try {
            String error = response.errorBody() != null ?
                    response.errorBody().string() : "Unknown error";

            Log.e(TAG, "خطأ في API: " + response.code() + " - " + error);

            switch (response.code()) {
                case 401:
                    showToast("مفتاح API غير صالح");
                    break;
                case 404:
                    showToast("الموقع غير موجود");
                    break;
                case 429:
                    showToast("تجاوز حد الطلبات المسموح بها");
                    break;
                default:
                    showToast("خطأ في الخادم: " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "خطأ في قراءة رسالة الخطأ", e);
        }
    }

    private void tryFallbackToCachedData() {
        WeatherResponse cached = getCachedWeatherData();
        if (cached != null) {
            updateWeatherUI(cached);
            showToast("يتم استخدام بيانات مخزنة");
        }
    }


    private void updateWeatherUI(WeatherResponse weather) {
        runOnUiThread(() -> {
            try {
                TextView tempView = findViewById(R.id.text_temp);
                TextView humidityView = findViewById(R.id.text_moisture);
                TextView descView = findViewById(R.id.text_weather_desc);
                ImageView iconView = findViewById(R.id.weather_icon_i);

                if (weather == null || weather.getMain() == null || weather.getWeather() == null || weather.getWeather().isEmpty()) {
                    Log.w(TAG, "بيانات الطقس غير متوفرة أو ناقصة");
                    return;
                }

                if (tempView != null) {
                    tempView.setText(String.format(Locale.getDefault(), "%.1f°C", weather.getMain().getTemp()));
                }

                if (humidityView != null) {
                    humidityView.setText(String.format(Locale.getDefault(), "رطوبة: %d%%", (int) weather.getMain().getHumidity()));
                }

                if (descView != null && !weather.getWeather().isEmpty()) {
                    descView.setText(weather.getWeather().get(0).getDescription());
                }

                if (iconView != null && !weather.getWeather().isEmpty()) {
                    String iconCode = weather.getWeather().get(0).getIcon();
                    if (iconCode != null) {
                        int iconResId = getResources().getIdentifier("ic_" + iconCode, "drawable", getPackageName());
                        if (iconResId != 0) {
                            iconView.setImageResource(iconResId);
                        } else {
                            Log.w(TAG, "لم يتم العثور على أيقونة للرمز: " + iconCode);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "خطأ في تحديث واجهة الطقس", e);
            }
        });
    }

    private boolean isValidWeatherData(WeatherResponse weather) {
        return weather != null &&
                weather.getMain() != null &&
                weather.getWeather() != null &&
                !weather.getWeather().isEmpty();
    }

    private void cacheWeatherData(WeatherResponse weather) {
        SharedPreferences prefs = getSharedPreferences(WEATHER_PREFS, MODE_PRIVATE);
        prefs.edit()
                .putString("last_weather", new Gson().toJson(weather))
                .apply();
    }

    private WeatherResponse getCachedWeatherData() {
        SharedPreferences prefs = getSharedPreferences(WEATHER_PREFS, MODE_PRIVATE);
        String json = prefs.getString("last_weather", null);
        if (json != null) {
            return new Gson().fromJson(json, WeatherResponse.class);
        }
        return null;
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(FarmInput.this, message, Toast.LENGTH_SHORT).show());
    }


//
//


//    private void loadSavedPreferences() {
//        SharedPreferences prefs = getSharedPreferences(GREENHOUSE_PREFS, MODE_PRIVATE);
//        spinnerGreenhouseType.setSelection(prefs.getInt("greenhouse_type", 0));
//        spinnerVentilation.setSelection(prefs.getInt("ventilation_type", 0));
//        spinnerSoilType.setSelection(prefs.getInt("soil_type", 0));
//        spinnerIrrigationType.setSelection(prefs.getInt("irrigation_type", 0));
//        checkboxLighting.setChecked(prefs.getBoolean("has_lighting", false));
//        checkboxHeating.setChecked(prefs.getBoolean("has_heating", false));
//        checkboxCooling.setChecked(prefs.getBoolean("has_cooling", false));
//        checkboxCO2.setChecked(prefs.getBoolean("has_co2", false));
//        switchAutomation.setChecked(prefs.getBoolean("has_automation", false));
//    }
//
//    private void saveGreenhouseSettings() {
//        SharedPreferences.Editor editor = getSharedPreferences(GREENHOUSE_PREFS, MODE_PRIVATE).edit();
//        editor.putInt("greenhouse_type", spinnerGreenhouseType.getSelectedItemPosition());
//        editor.putInt("ventilation_type", spinnerVentilation.getSelectedItemPosition());
//        editor.putInt("soil_type", spinnerSoilType.getSelectedItemPosition());
//        editor.putInt("irrigation_type", spinnerIrrigationType.getSelectedItemPosition());
//        editor.putBoolean("has_lighting", checkboxLighting.isChecked());
//        editor.putBoolean("has_heating", checkboxHeating.isChecked());
//        editor.putBoolean("has_cooling", checkboxCooling.isChecked());
//        editor.putBoolean("has_co2", checkboxCO2.isChecked());
//        editor.putBoolean("has_automation", switchAutomation.isChecked());
//        editor.apply();
//
//        Toast.makeText(this, "تم حفظ إعدادات البيوت المحمية", Toast.LENGTH_SHORT).show();
//    }
//
//    private void showAutomationOptionsDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("إعدادات الأتمتة")
//                .setMessage("اختر الأنظمة التي تريد أتمتتها:")
//                .setMultiChoiceItems(new String[]{"الري", "التهوية", "التحكم بالحرارة", "الإضاءة"},
//                        new boolean[]{false, false, false, false},
//                        (dialog, which, isChecked) -> {
//                            // Handle checkbox selections
//                        })
//                .setPositiveButton("حفظ", null)
//                .setNegativeButton("إلغاء", null)
//                .show();
//    }
//
//    //هذي الدالة بدي انفذها اذا الانتنت بيت زجاجي بدي اعمل كمان دالة للبور
//    private void evaluateGreenhouse(Location location) {
//        lat = location.getLatitude();
//        lon = location.getLongitude();
//
//        fetchWeatherDataWithCheck(lat, lon);
//        fetchHistoricalWeather(lat, lon);
//
//        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//
//        // float avgTemp = (float) avgHistoricalTemp;
//        double avgMoist = avgHumidity1;
//
//
//        if (selectedCrop == null || selectedCrop.isEmpty()) {
//            Toast.makeText(this, "No crop selected", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String soilType = spinnerSoilType.getSelectedItem().toString();
//        String irrigation = spinnerIrrigationType.getSelectedItem().toString();
//        String Availability = spinnerWaterAvailability.getSelectedItem().toString();
//        String greenhouseType = spinnerGreenhouseType.getSelectedItem().toString();
//        String ventilation = spinnerVentilation.getSelectedItem().toString();
//
//        String FertilizerType = spinnerFertilizerType.getSelectedItem().toString();
//
//        if (FertilizerType.equals("عضوي")) {
//
//
//        } else {
//
//
//        }
//
//        //المحصول السابق معتمد عضويا
//
//        boolean hasLighting = checkboxLighting.isChecked();
//        boolean hasHeating = checkboxHeating.isChecked();
//        boolean hasCooling = checkboxCooling.isChecked();
//        boolean hasCO2 = checkboxCO2.isChecked();
//        boolean hasAutomation = switchAutomation.isChecked();
//        double scoreMoisture = getPoint(categorizeHumidity(avgMoist), selectedCrop.get(0).getPreferredHumidity(), selectedCrop.get(0).getAllowedHumidity(), selectedCrop.get(0).getForbiddenHumidity());
//
//        double scoreStructure = evaluateStructure(greenhouseType);
//        double availability = evaluateAvailability(Availability, selectedCrop.get(0));
//        double scoreVentilation = evaluateVentilation(ventilation);
//
//        double scoreSoil = evaluateSoil(soilType, selectedCrop.get(0));
//        double scoreIrrigation = evaluateIrrigation(irrigation, selectedCrop.get(0));
//        double scoreTempControl = evaluateTemperatureControl(hasHeating, hasCooling, (float) avgHumidity1, selectedCrop.get(0));
//        double scoreLighting = evaluateLighting(hasLighting, getSeasonBasedOnDate(currentDate), selectedCrop.get(0));
//        double scoreCO2 = hasCO2 ? 1.0 : 0.5;
//        double scoreAutomation = hasAutomation ? 1.0 : 0.7;
//
//
//        double totalScore = (scoreStructure * 0.15 +
//                scoreVentilation * 0.15 +
//                availability * 0.15 +
//                scoreSoil * 0.15 +
//                scoreIrrigation * 0.15 +
//                scoreTempControl * 0.15 +
//                scoreLighting * 0.1 +
//                scoreCO2 * 0.1 +
//                scoreAutomation * 0.05 +
//                scoreMoisture +
//                evaluatePreviues()
//        );
//
//        int successRate = (int) (totalScore * 100);
//        boolean isSuitable = successRate >= 55;
//
////        // Show result
////String locationStr,String  tempStr,String moistureStr,String acceptanceStr,String successStr ,String resultText ,String accepted) {
//
//        showEvaluationResult(successRate, isSuitable);
//
//
//        // Save evaluation
//        // saveEvaluationResult(successRate, isSuitable);
//    }

//    private void saveEvaluationResult(int successRate, boolean isSuitable) {
//        if (selectedCrop == null || selectedCrop.isEmpty() || currentLocation == null) {
//            Toast.makeText(this, "بيانات غير مكتملة", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//        Farmer_Crops values = new Farmer_Crops();
//        values.setFarmer_user_name(farmerUserName);
//        values.setCrop_ID(selectedCrop.get(0).getCrop_ID());
//        values.setStartDate(currentDate);
//        values.setLatitude(currentLocation.getLatitude());
//        values.setLongitude(currentLocation.getLongitude());
//        values.setVentilation(spinnerVentilation.getSelectedItem().toString());
//        values.setHasHeating(checkboxHeating.isChecked());
//        values.setHasCooling(checkboxCooling.isChecked());
//        values.setHasLighting(checkboxLighting.isChecked());
//        values.setHasCO2(checkboxCO2.isChecked());
//        values.setHasAutomation(switchAutomation.isChecked());
//        values.setGreenhouseType(spinnerGreenhouseType.getSelectedItem().toString());
//        values.setSoilType(spinnerSoilType.getSelectedItem().toString());
//        values.setIrrigationType(spinnerIrrigationType.getSelectedItem().toString());
//        values.setAverageTemperature((float) avgTemp1);
//        values.setAverage_humidity(avgHumidity1);
//        values.setSuccessRate(successRate);
//        values.setAccepted(isSuitable);
//        values.setSeason(getSeasonBasedOnDate(currentDate));
//        values.setRegister(true);
//        values.setLand_area(Double.parseDouble(space.getText().toString()));
//        values.setPrevious_crop(spinner_previous_crop.getSelectedItem().toString());
//
//        // ✅ أولاً الحفظ في قاعدة البيانات المحلية (Room)
//        myViewModel.insertFarmerCrop(values);
//
//
//        // ✅ ثم الرفع إلى Firebase
//        FirebaseDatabaseHelper.getInstance().addFarmerCrop(values, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(boolean success, Exception exception) {
//                runOnUiThread(() -> {
//                    if (success) {
//                        Toast.makeText(getApplicationContext(), "✅ تم رفع التقييم إلى Firebase", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "❌ فشل رفع التقييم: " + exception.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//
//        // ✅ الانتقال لواجهة التفاصيل
//        Intent intent = new Intent(getApplicationContext(), CropDetailsActivity1.class);
//        intent.putExtra("ID", selectedCrop.get(0).getCrop_ID());
//        intent.putExtra("USER", farmerUserName);
//        startActivity(intent);
//    }


//    private double evaluateStructure(String type) {
//        switch (type) {
//            case "زجاجي":
//                return 1.0;
//            case "بولي كربونيت":
//                return 1.0;
//            case "بلاستيكي مزدوج":
//                return 0.9;
//            case "بلاستيكي":
//                return 0.8;
//            default:
//                return 0.7;
//        }
//    }
//
//    private double evaluatePreviues() {
//        switch (result1) {
//
//            case "مفضل":
//                return 1.0;
//            case "مسموح":
//                return 0.9;
//            case "مرفوض":
//                return 0.8;
//            default:
//                return 0.7;
//        }
//    }
//
//    private double evaluateVentilation(String type) {
//        switch (type) {
//            case "جيدة جداً":
//                return 1.0;
//            case "جيدة":
//                return 0.9;
//            case "متوسطة":
//                return 0.7;
//            default:
//                return 0.5;
//        }
//    }
//
//    private double evaluateSoil(String soilType, Crop crop) {
//        if (soilType.equals(crop.getPreferredSoil())) return 1.0;
//        if (soilType.equals(crop.getAllowedSoil())) return 0.8;
//        return 0.5;
//    }
//
//    private double evaluateIrrigation(String irrigationType, Crop crop) {
//        if (irrigationType.equals(crop.getPreferredIrrigation())) return 1.0;
//        if (irrigationType.equals(crop.getAllowedIrrigation())) return 0.8;
//        return 0.5;
//    }
//
//    private double evaluateAvailability(String availability, Crop crop) {
//        if (availability.equals(crop.getPreferredAbundance())) return 1.0;
//        if (availability.equals(crop.getAllowedAbundance())) return 0.8;
//        return 0.5;
//    }
//
//    private double evaluateTemperatureControl(boolean hasHeating, boolean hasCooling, float currentTemp, Crop crop) {
//        float optimalTemp = crop.getOptimalTemperature();
//        float tempDiff = Math.abs(currentTemp - optimalTemp);
//
//        // فرق بسيط جدًا → ممتاز
//        if (tempDiff <= 2) return 1.0;
//
//        // حرارة مرتفعة عن المطلوب
//        if (currentTemp > optimalTemp) {
//            if (hasCooling) {
//                if (tempDiff <= 4) return 0.9;
//                if (tempDiff <= 6) return 0.75;
//                return 0.6;
//            } else {
//                return 0.4; // لا يوجد تبريد والحرارة مرتفعة
//            }
//        }
//
//        // حرارة منخفضة عن المطلوب
//        if (currentTemp < optimalTemp) {
//            if (hasHeating) {
//                if (tempDiff <= 4) return 0.9;
//                if (tempDiff <= 6) return 0.75;
//                return 0.6;
//            } else {
//                return 0.4; // لا يوجد تدفئة والجو بارد
//            }
//        }
//
//        return 0.5; // في حال حصل خطأ غير متوقع
//    }

//    private double evaluateTemperatureControl(boolean hasHeating, boolean hasCooling, float currentTemp, Crop crop) {
//        float optimalTemp = crop.getOptimalTemperature();
//        float tempDiff = Math.abs(currentTemp - optimalTemp);
//
//        if (tempDiff < 3) return 1.0;
//        if (tempDiff < 5 && (hasHeating || hasCooling)) return 0.8;
//        if (tempDiff < 8 && (hasHeating && hasCooling)) return 0.7;
//        return 0.4;
//    }

//    private double evaluateLighting(boolean hasArtificialLight, String season, Crop crop) {
//        int lightRequirements = crop.getLightRequirements(); // 1 = قليل، 2 = متوسط، 3 = مرتفع
//
//        if (hasArtificialLight) {
//            return 1.0; // الإضاءة ممتازة بوجود مصادر صناعية
//        }
//
//        season = season.toLowerCase();
//
//        switch (season) {
//            case "summer":
//                if (lightRequirements == 1) return 1.0;
//                if (lightRequirements == 2) return 0.9;
//                return 0.8; // للضوء المرتفع
//
//            case "spring":
//            case "autumn":
//                if (lightRequirements == 1) return 0.9;
//                if (lightRequirements == 2) return 0.75;
//                return 0.6;
//
//            case "winter":
//                if (lightRequirements == 1) return 0.8;
//                if (lightRequirements == 2) return 0.6;
//                return 0.4; // للضوء المرتفع بدون صناعي، الإضاءة ضعيفة
//
//            default:
//                return 0.5; // في حال الموسم غير معروف
//        }
//    }
//
//    //,String locationStr,String season,String weatherDesc,String tempStr,String moistureStr,String acceptanceStr,String successStr ,String resultText ,boolean accepted
//    private void showEvaluationResult(int successRate, boolean isSuitable) {
//        String resultMessage = "نسبة النجاح: " + successRate + "%\n";
//        resultMessage += isSuitable ? "البيت المحمي مناسب لهذا المحصول" : "البيت المحمي غير مناسب لهذا المحصول";
//
//        if (!isSuitable) {
//            resultMessage += "\n\nالتوصيات:";
//            if (successRate < 50) {
//                resultMessage += "\n- يحتاج إلى تعديلات كبيرة في الهيكل أو الأنظمة";
//            } else {
//                resultMessage += "\n- يحتاج إلى بعض التحسينات الطفيفة";
//            }
//        }
//
//        new AlertDialog.Builder(this)
//                .setTitle("نتيجة التقييم")
//                .setMessage(resultMessage)
//                .setPositiveButton("ازرع الان", (dialog, which) -> saveEvaluationResult(successRate, isSuitable))
//                .setNegativeButton("إلغاء", null)
//                .show();

//        EvaluationResultDialog dialog = new EvaluationResultDialog(
//                FarmInput.this,
//                locationStr,
//                currentDate,
//                season,
//                tempStr,
//                moistureStr,
//                acceptanceStr,
//                successStr,
//                resultText,
//                accepted // showPlantNow button فقط إذا لم يكن مقبولاً
//                , CropId
//                , farmerUserName
//                ,result        );


    //  }


}


// في FarmInput.java
//    private void updateResultUI(int successRate, boolean accepted) {
//        runOnUiThread(() -> {
//            textViewResult.setText("النتيجة: " + successRate + "% - " + (accepted ? "مقبولة" : "مرفوضة"));
//
//            // تغيير لون الخلفية حسب النسبة
//            int backgroundColor;
//            if (successRate >= 70) {
//                backgroundColor = ContextCompat.getColor(this, R.color.success_green);
//            } else if (successRate >= 50) {
//                backgroundColor = ContextCompat.getColor(this, R.color.warning_yellow);
//            } else {
//                backgroundColor = ContextCompat.getColor(this, R.color.error_red);
//            }
//
//            GradientDrawable drawable = (GradientDrawable) textViewResult.getBackground();
//            //  drawable.setColor(backgroundColor);
//
//            // إضافة أيقونة
//            int iconRes = accepted ? R.drawable.google : R.drawable.facebook;
//            textViewResult.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);
//            textViewResult.setCompoundDrawablePadding(16);
//        });
//    }

//    public double calculateSuccessRate(Location location, String soil, String irrig, String water, double avgTemp, double avgMoist) {
//        // قم بتنفيذ الحسابات هنا بناءً على المعطيات
//        double score = 0;
//
//        // على سبيل المثال، يمكنك استخدام معايير معينة لحساب النتيجة بناءً على المعطيات
//        if (soil.equals("idealSoil")) {
//            score += 0.5;
//        }
//        if (irrig.equals("adequate") ) {
//            score += 0.3;
//        }
//        if ( water.equals("sufficient")) {
//            score += 0.3;
//        }
//        if (avgTemp >= 20 && avgTemp <= 30) {
//            score += 0.1;
//        }
//        if (avgMoist >= 40 && avgMoist <= 60) {
//            score += 0.1;
//        }
//
//        return score;  // عودة النتيجة
//    }
//    private void evaluateFarmWithWeather(double lat, double lon, float temperature, double humidity) {
//        String season = getSeasonBasedOnDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//        double scoreTemp = getPoint(categorizeTemp(temperature), selectedCrop.get(0).getPreferredTemp(), selectedCrop.get(0).getAllowedTemp(), selectedCrop.get(0).getForbiddenTemp());
//        double scoreHumidity = getPointNumeric(categorizeHumidity(humidity), selectedCrop.get(0).getPreferredHumidity(), selectedCrop.get(0).getAllowedHumidity(), selectedCrop.get(0).getForbiddenHumidity());
//        double score = (scoreTemp + scoreHumidity) / 2.0;
//        int successRate = (int) (score * 100);
//        boolean accepted = successRate > 50;
//
//        Farmer_Crops record = new Farmer_Crops();
//        record.setFarmer_user_name(farmerUserName);
//        record.setCrop_ID(selectedCrop.get(0).getCrop_ID());
//        record.setStartDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//        record.setLocation(lat + "," + lon);
//        record.setSeason(season);
//        record.setAverageTemperature(temperature);
//        record.setMoistureLevel(humidity);
//        record.setSuccessRate(successRate);
//        record.setAccepted(accepted);
//        saveRecordInDatabase(record);
//    }
//    private void saveFarmRecord(double lat, double lon, String currentDate, String season,
//                                float avgTemp, double avgMoist, int successRate) {
//        new Thread(() -> {
//            try {
//                Farmer_Crops record = new Farmer_Crops();
//                record.setFarmer_user_name(farmerUserName);
//                record.setCrop_ID(selectedCrop.get(0).getCrop_ID());
//                record.setStartDate(currentDate);
//                record.setLocation(lat + "," + lon);
//                record.setSeason(season);
//                record.setAverageTemperature(avgTemp);
//                record.setMoistureLevel(avgMoist);
//                record.setSoilType(spinnerSoilType.getSelectedItem().toString());
//                record.setIrrigationType(spinnerIrrigationType.getSelectedItem().toString());
//                record.setWaterAvailability(spinnerWaterAvailability.getSelectedItem().toString());
//                record.setSuccessRate(successRate);
//                record.setAccepted(true);
//                record.setBookmark(false);
//                record.setAddCart(false);
//                record.setRating(0);
//
//                myViewModel.insertFarmerCrop(record);
//
//                runOnUiThread(() -> {
//                    Intent intent = new Intent(FarmInput.this, CropDetailsActivity1.class);
//                    intent.putExtra("USER", farmerUserName);
//                    intent.putExtra("ID", selectedCrop.get(0).getCrop_ID());
//                    startActivity(intent);
//                    finish();
//                });
//            } catch (Exception e) {
//                runOnUiThread(() ->
//                        Toast.makeText(this, "خطأ في حفظ البيانات", Toast.LENGTH_SHORT).show()
//                );
//            }
//        }).start();
//    }

//    private void saveRecordInDatabase(Farmer_Crops record) {
//        new Thread(() -> {
//            try {
//                // 1. استعلام عن السجل الموجود
//                Farmer_Crops existing = farmerCropsDao.getFarmerCrop(
//                        record.getFarmer_user_name(),
//                        record.getCrop_ID()
//                );
//                // 2. جهّز السجل الذي سترسله للـ ViewModel
//                Farmer_Crops toSave;
//                boolean isUpdate;
//                if (existing != null) {
//                    // نسخ البيانات إلى السجل الموجود
//                    existing.updateFrom(record);
//                    toSave = existing;
//                    isUpdate = true;
//                } else {
//                    toSave = record;
//                    isUpdate = false;
//                }
//
//                // 3. نفّذ استدعاء الـ ViewModel على الـ Main thread
//                runOnUiThread(() -> {
//                    if (isUpdate) {
//                        myViewModel.updateCropFarmer(toSave);
//                    } else {
//                        myViewModel.insertFarmerCrop(toSave);
//                    }
//                    // 4. حدّث واجهة المستخدم بالنتيجة
//                    textViewResult.setText(
//                            "النتيجة: " + toSave.getSuccessRate() + "% - " +
//                                    (toSave.isAccepted() ? "مقبولة" : "مرفوضة")
//                    );
//                });
//
//            } catch (Exception e) {
//                Log.e("Database", "Error saving record", e);
//                runOnUiThread(() ->
//                        Toast.makeText(FarmInput.this, "خطأ في حفظ البيانات", Toast.LENGTH_SHORT).show()
//                );
//            }
//        }).start();
//    }
//    public void showEvaluationResultDialog(Context context,
//                                           String location, String date, String season,
//                                           String weatherDesc, String temp, String moisture,
//                                           String acceptance, String success, String resultText,
//                                           boolean showPlantButton,
//                                           Runnable onPlantNow) {
//        Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_result);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//        // عناصر الواجهة
//        TextView textLocation = dialog.findViewById(R.id.text_location);
//        TextView textDate = dialog.findViewById(R.id.text_date);
//        TextView textSeason = dialog.findViewById(R.id.text_season);
//        TextView textWeatherDesc = dialog.findViewById(R.id.text_weather_desc);
//        TextView textTemp = dialog.findViewById(R.id.text_temp);
//        TextView textMoisture = dialog.findViewById(R.id.text_moisture);
//        TextView textAcceptance = dialog.findViewById(R.id.text_acceptance);
//        TextView textSuccess = dialog.findViewById(R.id.text_success);
//        TextView textViewResult = dialog.findViewById(R.id.textViewResult);
//        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
//        Button btnPlantNow = dialog.findViewById(R.id.btn_Plant_now);
//        Button btnClose = dialog.findViewById(R.id.btn_close);
//        // تعيين البيانات
//        textLocation.setText("الموقع: " + location);
//        textDate.setText("تاريخ الزراعة: " + date);
//        textSeason.setText("الموسم: " + season);
//        textWeatherDesc.setText(weatherDesc);
//        textTemp.setText("درجة الحرارة: " + temp);
//        textMoisture.setText("مستوى الرطوبة: " + moisture);
//        textAcceptance.setText("القبول: " + acceptance);
//        textSuccess.setText("نسبة النجاح: " + success);
//        textViewResult.setText(resultText);
//
//        // لون القبول
//        int color = acceptance.contains("مقبول") ?
//                ContextCompat.getColor(context, R.color.success_green) :
//                ContextCompat.getColor(context, R.color.error_red);
//                textAcceptance.setTextColor(color);
//
//        // إظهار أو إخفاء زر الزراعة
//        btnPlantNow.setVisibility(showPlantButton ? View.VISIBLE : View.GONE);
//
//        btnClose.setOnClickListener(v -> dialog.dismiss());
//
//        btnPlantNow.setOnClickListener(v -> {
//            if (onPlantNow != null) {
//                onPlantNow.run();
//            }
//            dialog.dismiss();
//        });
//
//        dialog.show();
//    }

//بيرجع بيانات اللوكيشن
// حساب النتيجة الأساسية
//        double basicScore = calculateSuccessRate(location, soil, irrig, water, avgTemp, avgMoist);
//
// تطبيق التعديلات التاريخية
//        double finalScore = getHistoricalAdjustedScore(basicScore, avgTemp, (float) avgMoist);
//        int successRate = (int) finalScore;
//  boolean accepted = successRate > 50;

//            showEvaluationResultDialog(
//                    this,
//                    locationStr, currentDate, season, weatherDesc, tempStr, moistureStr,
//                    acceptanceStr, successStr, resultText,
//                    true,
//                    () -> {
//                        // الإجراء عند الضغط على زر "ازرع الآن"
//                        Toast.makeText(this, "بدأت الزراعة!", Toast.LENGTH_SHORT).show();
//                    }
//            );


//    private void setupComparisonUI() {
//        // إضافة Spinner جديد لاختيار المحصول للمقارنة
//        //  Spinner spinnerCropSelection = findViewById(R.id.spinner_crop_selection);
//
//        List<String> cropNames = new ArrayList<>();
//        for (Crop crop : selectedCrop) {
//            cropNames.add(crop.getCrop_NAME());
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, cropNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

/// /        spinnerCropSelection.setAdapter(adapter);
/// /
/// /        spinnerCropSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
/// /            @Override
/// /            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/// /                if (currentLocation != null) {
/// /                    evaluateFarm(currentLocation);
/// /                }
/// /            }
/// /
/// /            @Override
/// /            public void onNothingSelected(AdapterView<?> parent) {
/// /
/// /            }
/// /        });
//    }
// جلب معلومات تاريخية

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String selectedFertilizer = parentView.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), "تم اختيار: " + selectedFertilizer, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // يمكن إضافة كود هنا إذا أردت التعامل مع الحالة هذه
//            }
//        });

//        // استقبال طريقة الزراعة
//        String farmingMethod = getIntent().getStringExtra("farming_method");
//
//        // استخدام القيمة حسب الحاجة
//        if (farmingMethod != null) {
//            switch (farmingMethod) {
//                case "greenhouse":
//                    // معالجة حالة الزراعة في بيت بلاستيكي
//                    break;
//                case "open_field":
//                    // معالجة حالة الزراعة البور
//                    break;
//                case "hydroponic":
//                    // معالجة حالة الزراعة بدون تربة
//                    break;
//                case "organic":
//                    // معالجة حالة الزراعة العضوية
//                    break;
//            }
//        }

//بيانات التربة
//  initializeCropCompatibility();
//
//    private void initializeCropCompatibility() {
//        // القواعد:
//        // - "مرفوض": نفس العائلة أو استنزاف التربة.
//        // - "مفضل": تحسن خصوبة التربة (مثل البقول بعد الذرة).
//        // - "مسموح": لا يوجد تعارض.
//
//        // طماطم (عائلة الباذنجانيات)
//        Map<String, String> tomatoRules = new HashMap<>();
//        tomatoRules.put("طماطم", "مرفوض");
//        tomatoRules.put("باذنجان", "مرفوض");
//        tomatoRules.put("خيار", "مسموح");
//        tomatoRules.put("فول", "مفضل");
//        tomatoRules.put("ذرة", "مسموح");
//        tomatoRules.put("بصل", "مسموح");
//        cropCompatibility.put("طماطم", tomatoRules);
//
//        // باذنجان (عائلة الباذنجانيات)
//        Map<String, String> eggplantRules = new HashMap<>();
//        eggplantRules.put("طماطم", "مرفوض");
//        eggplantRules.put("باذنجان", "مرفوض");
//        eggplantRules.put("خيار", "مسموح");
//        eggplantRules.put("فول", "مفضل");
//        cropCompatibility.put("باذنجان", eggplantRules);
//
//        // فول (بقوليات)
//        Map<String, String> beansRules = new HashMap<>();
//        beansRules.put("طماطم", "مسموح");
//        beansRules.put("ذرة", "مفضل");
//        cropCompatibility.put("فول", beansRules);
//
//        // يمكن إضافة المزيد من القواعد هنا...
//    }

