package com.example.happyharvest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout helpand_feedback = findViewById(R.id.helpand_feedback);
        LinearLayout ll_language = findViewById(R.id.ll_language);
        LinearLayout Notification_BTN = findViewById(R.id.notification_btn);
        LinearLayout faqs = findViewById(R.id.faqs);
        LinearLayout privacy_policy_btn = findViewById(R.id.privacy_policy_btn);

        ll_language.setOnClickListener(v -> showLanguageSelector(this));

        Notification_BTN.setOnClickListener(v -> {
            View bottomSheetView = LayoutInflater.from(this)
                    .inflate(R.layout.notification_settings_sheet, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        helpand_feedback.setOnClickListener(v -> {
            Intent intent = new Intent(this, HelpFeedback.class);
            startActivity(intent);
        });

        privacy_policy_btn.setOnClickListener(v -> fetchPrivacyData());

        faqs.setOnClickListener(v -> {
            Intent intent = new Intent(this, FAQS.class);
            startActivity(intent);
        });
    }

    private void fetchPrivacyData() {

    }

    private void showBottomSheet(String title, String description) {
        View bottomSheetView = LayoutInflater.from(this)
                .inflate(R.layout.privacy_policy, null);

        TextView titleTextView = bottomSheetView.findViewById(R.id.privacy_policy);
        TextView descTextView = bottomSheetView.findViewById(R.id.text_privacy_policy);

        titleTextView.setText(title);
        descTextView.setText(description);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void showLanguageSelector(Context context) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.language_prefrence_sheet, null);
        dialog.setContentView(view);

        LanguageManager langManager = new LanguageManager(context);

        Button btnEnglish = view.findViewById(R.id.btnEnglish);
        Button btnArabic = view.findViewById(R.id.btnArabic);

        Drawable checkIcon = ContextCompat.getDrawable(context, R.drawable.ic_check);
        int iconPadding = 16;

        btnEnglish.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnArabic.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        String currentLang = langManager.getLanguage();
        if ("ar".equals(currentLang)) {
            btnArabic.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null);
            btnArabic.setCompoundDrawablePadding(iconPadding);
        } else {
            btnEnglish.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null);
            btnEnglish.setCompoundDrawablePadding(iconPadding);
        }

        btnEnglish.setOnClickListener(v -> {
            langManager.setLanguage("en");
            refreshApp(context);
            dialog.dismiss();
        });

        btnArabic.setOnClickListener(v -> {
            langManager.setLanguage("ar");
            refreshApp(context);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void refreshApp(Context context) {
        Intent intent = new Intent(context, MainActivity_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
