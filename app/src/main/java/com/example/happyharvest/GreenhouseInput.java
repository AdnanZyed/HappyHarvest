package com.example.happyharvest;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GreenhouseInput extends AppCompatActivity {
    private Spinner spinnerGreenhouseType, spinnerVentilation, spinnerSoilType, spinnerIrrigation;
    private CheckBox checkboxLighting, checkboxHeating, checkboxCooling, checkboxCO2;
    private Switch switchAutomation;
    private Button btnEvaluate, btnSaveSettings;
    private TextView textViewResult, textViewTemp, textViewHumidity;
    private TextView spinner_hydroponic_system_TV;
    private Spinner spinner_hydroponic_system;
    private TextView edit_nutrient_solution_TV;
    private EditText edit_nutrient_solution;

    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String> locationPermissionLauncher;
    private Farmer_Crop_Dao farmerCropsDao;
    private int CropId;
    private List<Crop> selectedCrop;
    private String farmerUserName;
    private My_View_Model myViewModel;
    private Location currentLocation;
    private static final String GREENHOUSE_PREFS = "GreenhousePrefs";
    private static final String API_KEY = "9e269c7c20355e9e8bba48b0ad2cd52c";
    private LocationCallback locationCallback;
    private float currentTemp;

    private float currentHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_input);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        // Initialize Views
        initViews();

        // Get User Data from Intent
        getIntentData();

        // Setup Spinners
        setupSpinners();

        // Setup Location Services
        setupLocationServices();

        // Setup Listeners
        setupListeners();

        // Load Saved Preferences
        loadSavedPreferences();
    }

    private void initViews() {
        spinnerGreenhouseType = findViewById(R.id.spinner_house_type);
        spinnerVentilation = findViewById(R.id.edit_ventilation);
        spinnerSoilType = findViewById(R.id.spinner_soil_type);
        spinnerIrrigation = findViewById(R.id.spinner_irrigation);
        checkboxLighting = findViewById(R.id.checkbox_lighting);
        checkboxHeating = findViewById(R.id.checkbox_heating);
        checkboxCooling = findViewById(R.id.checkbox_cooling);
        checkboxCO2 = findViewById(R.id.checkbox_co2);
        switchAutomation = findViewById(R.id.switch_automation);


        btnEvaluate = findViewById(R.id.btn_evaluate);
        btnEvaluate = findViewById(R.id.btn_evaluate);
        btnSaveSettings = findViewById(R.id.btn_Plant_now);
        textViewResult = findViewById(R.id.textViewResult);
        textViewTemp = findViewById(R.id.text_temp);
        textViewHumidity = findViewById(R.id.text_humidity);

        spinner_hydroponic_system_TV = findViewById(R.id.spinner_hydroponic_system_TV);
        spinner_hydroponic_system = findViewById(R.id.spinner_hydroponic_system);
        edit_nutrient_solution_TV = findViewById(R.id.edit_nutrient_solution_TV);
        edit_nutrient_solution = findViewById(R.id.edit_nutrient_solution);

        spinner_hydroponic_system_TV.setVisibility(View.GONE);
        spinner_hydroponic_system.setVisibility(View.GONE);
        edit_nutrient_solution_TV.setVisibility(View.GONE);
        edit_nutrient_solution.setVisibility(View.GONE);
    }

    private void getIntentData() {
        CropId = getIntent().getIntExtra("crop", 1);
        farmerUserName = getIntent().getStringExtra("USER");

        if (getIntent().hasExtra("crop")) {
            List<Integer> selectedCropIds = new ArrayList<>();
            selectedCropIds.add(getIntent().getIntExtra("crop", 1));
            myViewModel.getMultipleCrops(selectedCropIds).observe(this, crops -> {
                if (crops != null && !crops.isEmpty()) {
                    selectedCrop = new ArrayList<>(crops);
                }
            });
        }
    }

    private void setupSpinners() {
        setupSpinner(spinnerGreenhouseType, R.array.greenhouse_types);
        setupSpinner(spinnerVentilation, R.array.ventilation_types);
        setupSpinner(spinnerSoilType, R.array.soil_types);
        setupSpinner(spinnerIrrigation, R.array.irrigation_types);
    }

    private void setupLocationServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    fetchWeatherData(location.getLatitude(), location.getLongitude());
                }
                fusedLocationClient.removeLocationUpdates(this);
            }
        };

        locationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) {
                        if (isLocationEnabled()) {
                            getCurrentLocation();
                        } else {
                            showLocationSettingsDialog();
                        }
                    } else {
                        Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupListeners() {
        btnEvaluate.setOnClickListener(v -> checkLocationPermission());

        btnSaveSettings.setOnClickListener(v -> saveGreenhouseSettings());

        switchAutomation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showAutomationOptionsDialog();
            }
        });
    }

    private void loadSavedPreferences() {
        SharedPreferences prefs = getSharedPreferences(GREENHOUSE_PREFS, MODE_PRIVATE);
        spinnerGreenhouseType.setSelection(prefs.getInt("greenhouse_type", 0));
        spinnerVentilation.setSelection(prefs.getInt("ventilation_type", 0));
        spinnerSoilType.setSelection(prefs.getInt("soil_type", 0));
        spinnerIrrigation.setSelection(prefs.getInt("irrigation_type", 0));
        checkboxLighting.setChecked(prefs.getBoolean("has_lighting", false));
        checkboxHeating.setChecked(prefs.getBoolean("has_heating", false));
        checkboxCooling.setChecked(prefs.getBoolean("has_cooling", false));
        checkboxCO2.setChecked(prefs.getBoolean("has_co2", false));
        switchAutomation.setChecked(prefs.getBoolean("has_automation", false));
    }

    private void saveGreenhouseSettings() {
        SharedPreferences.Editor editor = getSharedPreferences(GREENHOUSE_PREFS, MODE_PRIVATE).edit();
        editor.putInt("greenhouse_type", spinnerGreenhouseType.getSelectedItemPosition());
        editor.putInt("ventilation_type", spinnerVentilation.getSelectedItemPosition());
        editor.putInt("soil_type", spinnerSoilType.getSelectedItemPosition());
        editor.putInt("irrigation_type", spinnerIrrigation.getSelectedItemPosition());
        editor.putBoolean("has_lighting", checkboxLighting.isChecked());
        editor.putBoolean("has_heating", checkboxHeating.isChecked());
        editor.putBoolean("has_cooling", checkboxCooling.isChecked());
        editor.putBoolean("has_co2", checkboxCO2.isChecked());
        editor.putBoolean("has_automation", switchAutomation.isChecked());
        editor.apply();

        Toast.makeText(this, "تم حفظ إعدادات البيوت المحمية", Toast.LENGTH_SHORT).show();
    }

    private void showAutomationOptionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("إعدادات الأتمتة")
                .setMessage("اختر الأنظمة التي تريد أتمتتها:")
                .setMultiChoiceItems(new String[]{"الري", "التهوية", "التحكم بالحرارة", "الإضاءة"},
                        new boolean[]{false, false, false, false},
                        (dialog, which, isChecked) -> {
                            // Handle checkbox selections
                        })
                .setPositiveButton("حفظ", null)
                .setNegativeButton("إلغاء", null)
                .show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLocation = location;
                            fetchWeatherData(location.getLatitude(), location.getLongitude());
                        } else {
                            requestNewLocationData();
                        }
                    });
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
            Log.e(TAG, "Security exception", e);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                getCurrentLocation();
            } else {
                showLocationSettingsDialog();
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
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

    private void fetchWeatherData(double lat, double lon) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Implement actual weather API call here
        // This is a mock implementation
        currentTemp = 25.0f; // Replace with actual API call
        currentHumidity = 60.0f; // Replace with actual API call

        updateWeatherUI();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void updateWeatherUI() {
        runOnUiThread(() -> {
            textViewTemp.setText(String.format(Locale.getDefault(), "%.1f°C", currentTemp));
            textViewHumidity.setText(String.format(Locale.getDefault(), "%.0f%%", currentHumidity));
        });
    }

    private void evaluateGreenhouse() {
        if (selectedCrop == null || selectedCrop.isEmpty()) {
            Toast.makeText(this, "No crop selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Collect all data
        String greenhouseType = spinnerGreenhouseType.getSelectedItem().toString();
        String ventilation = spinnerVentilation.getSelectedItem().toString();
        String soilType = spinnerSoilType.getSelectedItem().toString();
        String irrigation = spinnerIrrigation.getSelectedItem().toString();
        boolean hasLighting = checkboxLighting.isChecked();
        boolean hasHeating = checkboxHeating.isChecked();
        boolean hasCooling = checkboxCooling.isChecked();
        boolean hasCO2 = checkboxCO2.isChecked();
        boolean hasAutomation = switchAutomation.isChecked();

        // Calculate scores
        double scoreStructure = evaluateStructure(greenhouseType);
        double scoreVentilation = evaluateVentilation(ventilation);
        double scoreSoil = evaluateSoil(soilType, selectedCrop.get(0));
        double scoreIrrigation = evaluateIrrigation(irrigation, selectedCrop.get(0));
        double scoreTempControl = evaluateTemperatureControl(hasHeating, hasCooling, currentTemp, selectedCrop.get(0));
        double scoreLighting = evaluateLighting(hasLighting, getCurrentSeason(), selectedCrop.get(0));
        double scoreCO2 = hasCO2 ? 1.0 : 0.5;
        double scoreAutomation = hasAutomation ? 1.0 : 0.7;

        // Calculate weighted total score
        double totalScore = (scoreStructure * 0.15 +
                scoreVentilation * 0.15 +
                scoreSoil * 0.15 +
                scoreIrrigation * 0.15 +
                scoreTempControl * 0.15 +
                scoreLighting * 0.1 +
                scoreCO2 * 0.1 +
                scoreAutomation * 0.05);

        int successRate = (int)(totalScore * 100);
        boolean isSuitable = successRate >= 70;

        // Show result
        showEvaluationResult(successRate, isSuitable);

        // Save evaluation
        saveEvaluationResult(successRate, isSuitable);
    }

    private double evaluateStructure(String type) {
        switch (type) {
            case "زجاجي":
                return 1.0;
            case "بلاستيكي مزدوج":
                return 0.9;
            case "بلاستيكي":
                return 0.8;
            default:
                return 0.7;
        }
    }

    private double evaluateVentilation(String type) {
        switch (type) {
            case "جيدة جداً":
                return 1.0;
            case "جيدة":
                return 0.9;
            case "متوسطة":
                return 0.7;
            default:
                return 0.5;
        }
    }

    private double evaluateSoil(String soilType, Crop crop) {
        if (soilType.equals(crop.getPreferredSoil())) return 1.0;
        if (soilType.equals(crop.getAllowedSoil())) return 0.8;
        return 0.5;
    }

    private double evaluateIrrigation(String irrigationType, Crop crop) {
        if (irrigationType.equals(crop.getPreferredIrrigation())) return 1.0;
        if (irrigationType.equals(crop.getAllowedIrrigation())) return 0.8;
        return 0.5;
    }

    private double evaluateTemperatureControl(boolean hasHeating, boolean hasCooling, float currentTemp, Crop crop) {
        float optimalTemp = crop.getOptimalTemperature();
        float tempDiff = Math.abs(currentTemp - optimalTemp);

        if (tempDiff < 3) return 1.0;
        if (tempDiff < 5 && (hasHeating || hasCooling)) return 0.8;
        if (tempDiff < 8 && (hasHeating && hasCooling)) return 0.7;
        return 0.4;
    }

    private double evaluateLighting(boolean hasArtificialLight, String season, Crop crop) {
        int lightRequirements = crop.getLightRequirements();

        if (hasArtificialLight) {
            return 1.0;
        } else {
            if (season.equals("صيف") && lightRequirements < 3) return 0.9;
            if (season.equals("شتاء") && lightRequirements < 2) return 0.7;
            return 0.5;
        }
    }

    private String getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        if (month >= 3 && month <= 5) return "ربيع";
        else if (month >= 6 && month <= 8) return "صيف";
        else if (month >= 9 && month <= 11) return "خريف";
        else return "شتاء";
    }

    private void showEvaluationResult(int successRate, boolean isSuitable) {
        String resultMessage = "نسبة النجاح: " + successRate + "%\n";
        resultMessage += isSuitable ? "البيت المحمي مناسب لهذا المحصول" : "البيت المحمي غير مناسب لهذا المحصول";

        if (!isSuitable) {
            resultMessage += "\n\nالتوصيات:";
            if (successRate < 50) {
                resultMessage += "\n- يحتاج إلى تعديلات كبيرة في الهيكل أو الأنظمة";
            } else {
                resultMessage += "\n- يحتاج إلى بعض التحسينات الطفيفة";
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("نتيجة التقييم")
                .setMessage(resultMessage)
                .setPositiveButton("حفظ النتيجة", (dialog, which) -> saveEvaluationResult(successRate, isSuitable))
                .setNegativeButton("إلغاء", null)
                .show();
    }

    private void saveEvaluationResult(int successRate, boolean isSuitable) {
        if (selectedCrop == null || selectedCrop.isEmpty() || currentLocation == null) {
            Toast.makeText(this, "بيانات غير مكتملة", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // إنشاء كائن التقييم بدون الحاجة لـ GreenhouseEvaluation
        ContentValues values = new ContentValues();
        values.put("farmerUserName", farmerUserName);
        values.put("cropId", selectedCrop.get(0).getCrop_ID());
        values.put("evaluationDate", currentDate);
        values.put("location", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
        values.put("greenhouseType", spinnerGreenhouseType.getSelectedItem().toString());
        values.put("soilType", spinnerSoilType.getSelectedItem().toString());
        values.put("irrigationType", spinnerIrrigation.getSelectedItem().toString());
        values.put("ventilationType", spinnerVentilation.getSelectedItem().toString());
        values.put("hasHeating", checkboxHeating.isChecked());
        values.put("hasCooling", checkboxCooling.isChecked());
        values.put("hasLighting", checkboxLighting.isChecked());
        values.put("hasCO2", checkboxCO2.isChecked());
        values.put("hasAutomation", switchAutomation.isChecked());
        values.put("temperature", currentTemp);
        values.put("humidity", currentHumidity);
        values.put("successRate", successRate);
        values.put("isSuitable", isSuitable);

        // الحفظ باستخدام SQLite مباشرة (بدون Room)
        new Thread(() -> {
            try {
                SQLiteDatabase db = openOrCreateDatabase("HappyHarvestDB", MODE_PRIVATE, null);
                db.insert("greenhouse_evaluations", null, values);
                db.close();

                runOnUiThread(() ->
                        Toast.makeText(this, "تم حفظ التقييم بنجاح", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                Log.e(TAG, "خطأ في الحفظ", e);
                runOnUiThread(() ->
                        Toast.makeText(this, "فشل في حفظ التقييم", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void setupSpinner(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}