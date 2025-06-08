package com.example.happyharvest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome_screen extends AppCompatActivity {
    private int loadingStep = 0;
    private TextView loadingText;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);

        initializeViews();
        playWelcomeSound();
        startAnimations();
        scheduleNextActivity();
    }

    private void initializeViews() {
        loadingText = findViewById(R.id.loading_text);
        ImageView logo = findViewById(R.id.logo);
    }

    private void playWelcomeSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.welcome_sound);
        mediaPlayer.start();
    }

    private void startAnimations() {
        ImageView logo = findViewById(R.id.logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeIn);

        new Handler().postDelayed(this::updateLoadingText, 500);
    }

    private void updateLoadingText() {
        if (loadingStep < 3) {
            loadingStep++;
            loadingText.setText(getString(R.string.loading_text, ".".repeat(loadingStep)));
            new Handler().postDelayed(this::updateLoadingText, 500);
        }
    }

    private void scheduleNextActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Welcome_screen.this, Main_Activity_part.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
            finish();
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}