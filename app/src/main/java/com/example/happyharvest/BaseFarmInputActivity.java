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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class BaseFarmInputActivity extends AppCompatActivity {
    // Common Views
    protected Spinner spinnerSoilType, spinnerIrrigation;
    protected Button btnEvaluate, btnSaveSettings;
    protected TextView textViewResult, textViewTemp, textViewHumidity;
    protected FusedLocationProviderClient fusedLocationClient;
    protected ActivityResultLauncher<String> locationPermissionLauncher;
    protected Farmer_Crop_Dao farmerCropsDao;
    protected int CropId;
    protected List<Crop> selectedCrop;
    protected String farmerUserName;
    protected My_View_Model myViewModel;
    protected Location currentLocation;
    protected static final String WEATHER_PREFS = "WeatherPrefs";
    protected static final String API_KEY = "9e269c7c20355e9e8bba48b0ad2cd52c";
    protected LocationCallback locationCallback;
    protected float currentTemp;
    protected float currentHumidity;

    // Common Methods
    protected abstract void initSpecificViews();
    protected abstract void evaluateFarmConditions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_input);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        getCommonIntentData();
        initCommonViews();
        initSpecificViews();
        setupLocationServices();
        setupCommonListeners();
    }

    private void getCommonIntentData() {
        CropId = getIntent().getIntExtra("ID", 1);
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

    private void initCommonViews() {
        spinnerSoilType = findViewById(R.id.spinner_soil_type);
        spinnerIrrigation = findViewById(R.id.spinner_irrigation);
        btnEvaluate = findViewById(R.id.btn_evaluate);
        btnSaveSettings = findViewById(R.id.btn_Plant_now);
        textViewResult = findViewById(R.id.textViewResult);
        textViewTemp = findViewById(R.id.text_temp);
        textViewHumidity = findViewById(R.id.text_humidity);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    protected void setupLocationServices() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    fetchWeatherData(location.getLatitude(), location.getLongitude());
                    evaluateFarmConditions();
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

    protected void setupCommonListeners() {
        btnEvaluate.setOnClickListener(v -> checkLocationPermission());
    }

    @SuppressLint("MissingPermission")
    protected void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLocation = location;
                            fetchWeatherData(location.getLatitude(), location.getLongitude());
                            evaluateFarmConditions();
                        } else {
                            requestNewLocationData();
                        }
                    });
        }
    }

    protected void requestNewLocationData() {
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

    protected void checkLocationPermission() {
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

    protected boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    protected void showLocationSettingsDialog() {
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

    protected void fetchWeatherData(double lat, double lon) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Implement actual weather API call here
        // This is a mock implementation
        currentTemp = 25.0f;
        currentHumidity = 60.0f;

        updateWeatherUI();
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    protected void updateWeatherUI() {
        runOnUiThread(() -> {
            textViewTemp.setText(String.format(Locale.getDefault(), "%.1f°C", currentTemp));
            textViewHumidity.setText(String.format(Locale.getDefault(), "%.0f%%", currentHumidity));
        });
    }

    protected void setupSpinner(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    protected double evaluateSoil(String soilType, Crop crop) {
        if (soilType.equals(crop.getPreferredSoil())) return 1.0;
        if (soilType.equals(crop.getAllowedSoil())) return 0.8;
        return 0.5;
    }

    protected double evaluateIrrigation(String irrigationType, Crop crop) {
        if (irrigationType.equals(crop.getPreferredIrrigation())) return 1.0;
        if (irrigationType.equals(crop.getAllowedIrrigation())) return 0.8;
        return 0.5;
    }

    protected String getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        if (month >= 3 && month <= 5) return "ربيع";
        else if (month >= 6 && month <= 8) return "صيف";
        else if (month >= 9 && month <= 11) return "خريف";
        else return "شتاء";
    }

    protected void saveEvaluationResult(int successRate, boolean isSuitable) {
        if (selectedCrop == null || selectedCrop.isEmpty() || currentLocation == null) {
            Toast.makeText(this, "بيانات غير مكتملة", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put("farmerUserName", farmerUserName);
        values.put("cropId", selectedCrop.get(0).getCrop_ID());
        values.put("evaluationDate", currentDate);
        values.put("location", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
        values.put("successRate", successRate);
        values.put("isSuitable", isSuitable);

        new Thread(() -> {
            try {
                SQLiteDatabase db = openOrCreateDatabase("HappyHarvestDB", MODE_PRIVATE, null);
                db.insert("farm_evaluations", null, values);
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
}