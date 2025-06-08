package com.example.happyharvest;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import java.util.Random;

public class ForgotPassword extends AppCompatActivity {
    private EditText otpDigit1, otpDigit2, otpDigit3, otpDigit4;
    private String userName;

    private static final String CHANNEL_ID = "random_number_channel";

    private int otpCodeInt;
    int random;
    TextView textView;
    private int randomNumber;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        createNotificationChannel();
        requestNotificationPermission();
        random = getIntent().getIntExtra("RANDOM", -1);
        userName = getIntent().getStringExtra("USER");

        ImageView imageView = findViewById(R.id.back_icon_enroll1F);

        otpDigit1 = findViewById(R.id.otp_digit_11);
        otpDigit2 = findViewById(R.id.otp_digit_21);
        otpDigit3 = findViewById(R.id.otp_digit_31);
        otpDigit4 = findViewById(R.id.otp_digit_41);
        textView = findViewById(R.id.text_enroll5);

        otpDigit1.addTextChangedListener(new OTPTextWatcher(otpDigit1, otpDigit2));
        otpDigit2.addTextChangedListener(new OTPTextWatcher(otpDigit2, otpDigit3));
        otpDigit3.addTextChangedListener(new OTPTextWatcher(otpDigit3, otpDigit4));
        otpDigit4.addTextChangedListener(new OTPTextWatcher(otpDigit4, null));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                textView.setText(String.valueOf(i));
                i++;

                if (i == 60) {
                    i = 0;
                    Random random1 = new Random();
                    randomNumber = 1000 + random1.nextInt(9000);

                    sendNotification(randomNumber);
                    random = randomNumber;
                }

                new Handler(Looper.getMainLooper()).postDelayed(this, 1000);
            }
        }, 1000);


        Button continueButton = findViewById(R.id.bt_buy11);

        continueButton.setOnClickListener(v -> {
            String digit1 = otpDigit1.getText().toString().trim();
            String digit2 = otpDigit2.getText().toString().trim();
            String digit3 = otpDigit3.getText().toString().trim();
            String digit4 = otpDigit4.getText().toString().trim();


            if (digit1.isEmpty() || digit2.isEmpty() || digit3.isEmpty() || digit4.isEmpty()) {
                Toast.makeText(this, "Please enter all 4 digits of the OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            String otpCode = digit1 + digit2 + digit3 + digit4;
            otpCodeInt = Integer.parseInt(otpCode);

            if (otpCode.length() != 4) {
                Toast.makeText(this, "OTP Code must be 4 digits.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (otpCodeInt == random) {
                Intent intent = new Intent(ForgotPassword.this, NewPassword.class);
                intent.putExtra("USER", userName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "الادخال غير صحيح", Toast.LENGTH_SHORT).show();

            }


        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Random Number Channel";
            String description = "Channel for random number notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(int randomNumber) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("رقم سري جديد 🎲")
                .setContentText("الرقم الرقم السري الخاص بك هو: " + randomNumber)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private class OTPTextWatcher implements TextWatcher {
        private final EditText currentEditText;
        private final EditText nextEditText;

        public OTPTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


}