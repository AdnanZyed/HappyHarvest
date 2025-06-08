package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class EvaluationResultDialog extends Dialog {

    private String location, date, season, temp, moisture, acceptance, success, resultText, user,previuse;
    private boolean showPlantButton;
    private ProgressBar progressBar;
    private Button btnPlantNow, btnClose;
    private int id;

    public EvaluationResultDialog(Context context, String location, String date, String season, String temp, String moisture,
                                  String acceptance, String success, String resultText,
                                  boolean showPlantButton, int id, String user,String previuse) {
        super(context);
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
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // تهيئة عناصر الواجهة
        TextView textLocation = findViewById(R.id.text_location);
        TextView textDate = findViewById(R.id.text_date);
        TextView textSeason = findViewById(R.id.text_season);
        TextView textWeatherDesc = findViewById(R.id.text_weather_desc);
        TextView textTemp = findViewById(R.id.text_temp);
        TextView textMoisture = findViewById(R.id.text_moisture);
        TextView textAcceptance = findViewById(R.id.text_acceptance);
        TextView Previuse = findViewById(R.id.text_previuse);
        TextView textSuccess = findViewById(R.id.text_success);
        TextView textViewResult = findViewById(R.id.textViewResult);
        progressBar = findViewById(R.id.progressBar);
        btnPlantNow = findViewById(R.id.btn_Plant_now);
        btnClose = findViewById(R.id.btn_close);
        getWindow().setWindowAnimations(R.style.DialogAnimation);
        // تعيين القيم للنصوص
        textLocation.setText("الموقع: " + location);
        textDate.setText("تاريخ الزراعة: " + date);
        textSeason.setText("الموسم: " + season);
        textTemp.setText("درجة الحرارة: " + temp);
        textMoisture.setText("مستوى الرطوبة: " + moisture);
        textAcceptance.setText("القبول: " + acceptance);
        Previuse.setText("المحصول السابق: " + previuse);
        textSuccess.setText("نسبة النجاح: " + success);
        //progressBar.setProgress(Integer.parseInt(success));
        textViewResult.setText(resultText);


        // التحكم في ظهور زر الزراعة
        if (showPlantButton) {
            btnPlantNow.setVisibility(View.VISIBLE);

        } else {
            btnPlantNow.setVisibility(View.GONE);

//            showEnhancedErrorToast("EVALUATION",
//                    "لا يمكنك زراعة هذا المحصول لأنه لا يتوافق مع البيانات المدخلة");
            Toast.makeText(getContext(), "لا يمكنك زراعة هذا المحصول لأنه لا يتوافق مع البيانات المدخلة", Toast.LENGTH_SHORT).show();

        }

        // إعداد مستمعين للأزرار
        btnClose.setOnClickListener(v -> dismiss());

        btnPlantNow.setOnClickListener(v -> {
            // تنفيذ الإجراء عند الضغط على زر "قيّم الزراعة"
            if (onPlantNowListener != null) {
                onPlantNowListener.onPlantNow();
            }
        });
    }

//    private void setupResultColor(TextView textAcceptance, boolean accepted) {
//        int color = accepted ?
//                ContextCompat.getColor(getContext(), R.color.success_green) :
//                ContextCompat.getColor(getContext(), R.color.error_red);
//
//        textAcceptance.setTextColor(color);
//    }

    // عرض/إخفاء شريط التقدم
    public void showProgressBar(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    // واجهة للاستماع لضغطة زر الزراعة
    private OnPlantNowListener onPlantNowListener;

    public interface OnPlantNowListener {
        void onPlantNow();
    }

    public void setOnPlantNowListener(OnPlantNowListener listener) {
        this.onPlantNowListener = listener;
    }
}