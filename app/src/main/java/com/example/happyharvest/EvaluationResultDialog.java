package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvaluationResultDialog extends Dialog {

    private final String location, date, season, temp, moisture, acceptance, success, resultText, user, previuse;
    private final boolean showPlantButton;
    private final int id;
    private ProgressBar progressBar;
    private My_View_Model viewModel;
    private OnPlantNowListener onPlantNowListener;
    private final WeakReference<AppCompatActivity> activityRef;

    public EvaluationResultDialog(@NonNull AppCompatActivity activity,
                                  String location, String date, String season,
                                  String temp, String moisture, String acceptance,
                                  String success, String resultText,
                                  boolean showPlantButton, int id,
                                  String user, String previuse) {
        super(activity);
        this.activityRef = new WeakReference<>(activity);
        this.location = location;
        this.date = date;
        this.season = season;
        this.temp = temp;
        this.moisture = moisture;
        this.acceptance = acceptance;
        this.success = success;
        this.resultText = resultText;
        this.showPlantButton = showPlantButton;
        this.id = id;
        this.user = user;
        this.previuse = previuse;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_result);

        AppCompatActivity activity = activityRef.get();
        if (activity == null || activity.isFinishing()) {
            dismiss();
            return;
        }
        viewModel = new ViewModelProvider(activity).get(My_View_Model.class);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.DialogAnimation);


        initViews();
        setupData();
        setupButtons();
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        Button btnPlantNow = findViewById(R.id.btn_Plant_now);
        Button btnPlantOther = findViewById(R.id.btn_Plant_other);
        Button btnClose = findViewById(R.id.btn_close);
    }

    private void setupData() {
        AppCompatActivity activity = activityRef.get();
        if (activity == null) return;

        ((TextView) findViewById(R.id.text_location)).setText("الموقع: " + location);
        ((TextView) findViewById(R.id.text_date)).setText("تاريخ الزراعة: " + date);
        ((TextView) findViewById(R.id.text_season)).setText("الموسم: " + season);
        ((TextView) findViewById(R.id.text_temp)).setText("درجة الحرارة: " + temp);
        ((TextView) findViewById(R.id.text_moisture)).setText("مستوى الرطوبة: " + moisture);
        ((TextView) findViewById(R.id.text_acceptance)).setText("القبول: " + acceptance);
        ((TextView) findViewById(R.id.text_previuse)).setText("المحصول السابق: " + previuse);
        ((TextView) findViewById(R.id.text_success)).setText("نسبة النجاح: " + success);
        ((TextView) findViewById(R.id.textViewResult)).setText(resultText);

        findViewById(R.id.btn_Plant_now).setVisibility(showPlantButton ? View.VISIBLE : View.GONE);
        findViewById(R.id.btn_Plant_other).setVisibility(showPlantButton ? View.GONE : View.VISIBLE);

        if (!showPlantButton) {
            Toast.makeText(getContext(), "لا يمكنك زراعة هذا المحصول لأنه لا يتوافق مع البيانات المدخلة", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupButtons() {
        findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());

        findViewById(R.id.btn_Plant_now).setOnClickListener(v -> {
            if (onPlantNowListener != null) {
                onPlantNowListener.onPlantNow();
            }
        });

        findViewById(R.id.btn_Plant_other).setOnClickListener(v -> {
            dismiss();
            showRecommendedCrops();
        });
    }

    private void showRecommendedCrops() {
        AppCompatActivity activity = activityRef.get();
        if (activity == null || activity.isFinishing()) return;

        My_View_Model sharedViewModel = new ViewModelProvider(activity).get(My_View_Model.class);

        try {
            int moistureValue = 53;
            int tempValue = 24;
            sharedViewModel.getAllCrop().observe(activity, allCrops -> {
                if (allCrops == null || allCrops.isEmpty()) {
                    Toast.makeText(activity, "لا توجد محاصيل متاحة", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Crop> recommended = filterRecommendedCrops(allCrops, tempValue, moistureValue, season, previuse);
                if (recommended.isEmpty()) {
                    Toast.makeText(activity, "لا توجد محاصيل موصى بها تناسب بياناتك", Toast.LENGTH_SHORT).show();
                } else {
                    new RecommendedCropsBottomSheet(recommended, user)
                            .show(activity.getSupportFragmentManager(), "RecommendedCrops");
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(activity, "قيم الرطوبة أو الحرارة غير صالحة", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Crop> filterRecommendedCrops(List<Crop> allCrops, int temp, int moisture, String season, String previousCrop) {
        List<Crop> recommended = new ArrayList<>();

        for (Crop crop : allCrops) {
            int score = calculateCompatibilityScore(crop, temp, moisture, season, previousCrop);
            if (score >= 50) {
                crop.setCompatibilityScore(score);
                recommended.add(crop);
            }
        }

        Collections.sort(recommended, (c1, c2) ->
                Double.compare(c2.getCompatibilityScore(), c1.getCompatibilityScore()));

        return recommended;
    }

    private int calculateCompatibilityScore(Crop crop, int temp, int moisture, String season, String previousCrop) {
        int score = 0;

        // درجة الحرارة (30 نقطة)
        float optimalTemp = crop.getOptimalTemperature();
        float tempTolerance = crop.getTemperatureTolerance();
        if (Math.abs(temp - optimalTemp) <= tempTolerance) {
            score += 30;
        }

        // الرطوبة (30 نقطة)
        float optimalHumidity = crop.getOptimalHumidity();
        float humidityTolerance = crop.getHumidityTolerance();
        if (Math.abs(moisture - optimalHumidity) <= humidityTolerance) {
            score += 30;
        }

        // الموسم (20 نقطة)
        if (crop.getSeason() != null && crop.getSeason().equalsIgnoreCase(season)) {
            score += 20;
        }

        // المحصول السابق (20 نقطة)
        if (crop.getPrevious_crop_preferred() != null &&
                crop.getPrevious_crop_preferred().equalsIgnoreCase(previousCrop)) {
            score += 20;
        }

        return score;
    }

    public void showProgressBar(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void setOnPlantNowListener(OnPlantNowListener listener) {
        this.onPlantNowListener = listener;
    }

    public interface OnPlantNowListener {
        void onPlantNow();
    }

    public static class RecommendedCropsBottomSheet extends BottomSheetDialogFragment {
        private final List<Crop> recommendedCrops;
        private final String user;

        public RecommendedCropsBottomSheet(List<Crop> recommendedCrops, String user) {
            this.recommendedCrops = recommendedCrops;
            this.user = user;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.bottom_sheet_recommended_crops, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCrops);
            Button btnClose = view.findViewById(R.id.btnClose);

            CropAdapter adapter = new CropAdapter(requireContext(), recommendedCrops, user);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);

            btnClose.setOnClickListener(v -> dismiss());

            return view;
        }
    }
}