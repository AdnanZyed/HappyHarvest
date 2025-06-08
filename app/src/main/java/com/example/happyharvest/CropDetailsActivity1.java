package com.example.happyharvest;

import static android.content.Intent.getIntent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CropDetailsActivity1 extends AppCompatActivity {

    private static final String TAG = "CropDetailsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 101;

    private TextView textCropName, textWeatherTemp;
    private ImageView weatherIcon;
    private WeatherResponse currentWeather;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Button btnAISearch, btnImageAnalysis, btnSendQuestion;
    private EditText etQuestion;
    private String farmerUserName;
    private LinearLayout layoutWaterNotification, layoutFertilizerNotification;
    private TextView textWaterNotification, textFertilizerNotification;
    private My_View_Model myViewModel;
    private int cropId;
    private List<Farmer_Crops> farmerCrop;
    private List<Crop> currentCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details1);
        Log.d(TAG, "onCreate: Activity started");

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        initViews();

        cropId = getIntent().getIntExtra("ID", 1);
         farmerUserName = getIntent().getStringExtra("USER");
        Log.d(TAG, "Received intent extras - ID: " + cropId + ", USER: " + farmerUserName);

        loadCropData();
        loadFarmerCropData(farmerUserName);
        setupTabs();
        setupAIComponents();
        setupExpertChat();
    }

    private void initViews() {
        textCropName = findViewById(R.id.text_crop_name);
        weatherIcon = findViewById(R.id.weather_icon_d);
        textWeatherTemp = findViewById(R.id.text_weather_temp);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        btnAISearch = findViewById(R.id.btn_ai_search);
        btnImageAnalysis = findViewById(R.id.btn_image_analysis);
        etQuestion = findViewById(R.id.et_question);
        btnSendQuestion = findViewById(R.id.btn_send_question);
        layoutWaterNotification = findViewById(R.id.layout_water_notification);
        layoutFertilizerNotification = findViewById(R.id.layout_fertilizer_notification);
        textWaterNotification = findViewById(R.id.text_water_notification);
        textFertilizerNotification = findViewById(R.id.text_fertilizer_notification);
    }

    private void loadCropData() {
        myViewModel.getAllCropsById(cropId).observe(this, crop -> {
            if (crop != null && !crop.isEmpty()) {
                currentCrop = crop;
                textCropName.setText(crop.get(0).getCrop_NAME());
              //  updateNotifications();
            }
        });
    }

    private void loadFarmerCropData(String farmerUserName) {
        myViewModel.getFarmersByCropAndFarmer(farmerUserName, cropId).observe(this, farmerCrop -> {
            if (farmerCrop != null && !farmerCrop.isEmpty()) {
                this.farmerCrop = farmerCrop;
              //  updateNotifications();
                fetchWeatherData();
            }
        });
    }

    private void setupTabs() {
        CropDetailsPagerAdapter pagerAdapter = new CropDetailsPagerAdapter(this, cropId,farmerUserName);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("الرعاية اليومية");
                    break;
                case 1:
                    tab.setText("معلومات الزراعة");
                    break;
                case 2:
                    tab.setText("المشاكل والحلول");
                    break;
                case 3:
                    tab.setText("معلومات قيمة");
                    break;
            }
        }).attach();
    }

    private void setupAIComponents() {
        btnAISearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, AISearchActivity.class);
            intent.putExtra("crop_id", cropId);
            startActivity(intent);
        });

        btnImageAnalysis.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            } else {
                showImageSourceDialog();
            }
        });
    }

    private void setupExpertChat() {
        btnSendQuestion.setOnClickListener(v -> {
            String question = etQuestion.getText().toString().trim();
            if (!question.isEmpty()) {
                sendQuestionToExpert(question);
                etQuestion.setText("");
                Toast.makeText(this, "تم إرسال سؤالك إلى المختص", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "الرجاء إدخال سؤال", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendQuestionToExpert(String question) {
        // TODO: إرسال السؤال إلى الخادم
    }

    private void showImageSourceDialog() {
        String[] options = {"التقاط صورة بالكاميرا", "اختيار صورة من المعرض"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("اختر مصدر الصورة")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    } else if (which == 1) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
                    }
                })
                .show();
    }

    private void fetchWeatherData() {
        if (farmerCrop == null || farmerCrop.isEmpty()) return;

        String longitude = String.valueOf(farmerCrop.get(0).getLongitude());
        String latitude = String.valueOf(farmerCrop.get(0).getLatitude());
       if (longitude==null || latitude==null) return;


        if (longitude.length() != 2||latitude.length() != 2) return;

        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherApi weatherApi = retrofit.create(WeatherApi.class);
            Call<WeatherResponse> call = weatherApi.getCurrentWeather(lat, lon, "9e269c7c20355e9e8bba48b0ad2cd52c", "metric", "ar");

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        updateWeatherUI(response.body());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Toast.makeText(CropDetailsActivity1.this, "فشل في جلب بيانات الطقس1", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error parsing coordinates", e);
        }
    }

    private void updateWeatherUI(WeatherResponse weatherResponse) {
        if (weatherResponse == null || weatherResponse.getWeather() == null || weatherResponse.getWeather().isEmpty())
            return;
        float tempKelvin = weatherResponse.getMain().getTemp();
        int humidity = weatherResponse.getMain().getHumidity();

        textWeatherTemp.setText(String.format(Locale.getDefault(), "%.1f°C | رطوبة: %d%%", tempKelvin, humidity));
        String condition = weatherResponse.getWeather().get(0).getMain().toLowerCase();

        int iconRes = R.drawable.weather_icon;
        switch (condition) {
            case "clear":
                iconRes = R.drawable.sun;
                break;
            case "clouds":
                iconRes = R.drawable.clouds;
                break;
            case "rain":
                iconRes = R.drawable.heavyrain;
                break;
        }
        weatherIcon.setImageResource(iconRes);
    }

//    private void updateNotifications() {
//        if (currentCrop == null || currentCrop.isEmpty() || farmerCrop == null || farmerCrop.isEmpty())
//            return;
//
//        Crop crop = currentCrop.get(0);
//        Farmer_Crops farmer = farmerCrop.get(0);
//
//        // نفترض مدة الري والتسميد (بالأيام)
//        int waterIntervalDays = 3;      // كل 3 أيام سقاية
//        int fertilizerIntervalDays = 7; // كل 7 أيام تسميد
//
//        // نجيب تاريخ آخر ري وتسميد من Farmer_Crops (نحتاج تضيف هذه القيم في الجدول)
//        String lastWaterDateStr = farmer.getLastWaterDate();         // لازم تكون بصيغة yyyy-MM-dd
//        String lastFertilizerDateStr = farmer.getLastFertilizerDate();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        Date today = new Date();
//
//        try {
//            Date lastWaterDate = sdf.parse(lastWaterDateStr);
//            Date lastFertilizerDate = sdf.parse(lastFertilizerDateStr);
//
//            long daysSinceWater = (today.getTime() - lastWaterDate.getTime()) / (1000 * 60 * 60 * 24);
//            long daysSinceFertilizer = (today.getTime() - lastFertilizerDate.getTime()) / (1000 * 60 * 60 * 24);
//
//            if (daysSinceWater >= waterIntervalDays) {
//                textWaterNotification.setText("🚿 حان وقت سقاية المحصول!");
//            } else {
//                textWaterNotification.setText("لا حاجة للري اليوم.");
//            }
//
//            if (daysSinceFertilizer >= fertilizerIntervalDays) {
//                textFertilizerNotification.setText("🌱 حان وقت تسميد المحصول!");
//            } else {
//                textFertilizerNotification.setText("لا حاجة للتسميد اليوم.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            textWaterNotification.setText("⚠️ خطأ في تاريخ السقاية");
//            textFertilizerNotification.setText("⚠️ خطأ في تاريخ التسميد");
//        }
//    }

}
