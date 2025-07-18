//بدي بس اول مرة بفتح التطبيق بيفح الواجهة هذي

package com.example.happyharvest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity_part extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    public static final String FERTILIZER_CHANNEL_ID = "HappyHarvestChannel";
    private ViewPager2 viewPager;
    private Part_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_h);

        viewPager = findViewById(R.id.viewPager);
        Button btnNext = findViewById(R.id.B_Next);
        LinearLayout dotsLayout = findViewById(R.id.dots);

        List<Part_Item> items = new ArrayList<>();
        items.add(new Part_Item(R.drawable.pw1, " اختيار المحاصيل الأنسب لبيئتك وموسمك، مع تقليل التكاليف والجهد باستخدام بيانات حية وخبرات مزارعين آخرين."));
        items.add(new Part_Item(R.drawable.pw2, "تجربة زراعية مبتكرة مع تطبيق يتتبع تقدم المحاصيل ويوفر لك أفضل الأوقات للزراعة والحصاد لتحسين إنتاجك بسهولة.\""));
        items.add(new Part_Item(R.drawable.pw3, "تطبيق ذكي يساعدك في اختيار المحاصيل المثالية بناءً على البيئة والموسم، مع واجهة سهلة وتحديثات مستمرة من خبراء الزراعة"));
        adapter = new Part_Adapter(items);
        viewPager.setAdapter(adapter);

        setupDotsIndicator(items.size(), dotsLayout);


      //  setupNotificationChannel();
        checkAndRequestNotificationPermission();
       // setupNotificationChannel();



        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < items.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                Intent intent = new Intent(this, MainActivity_sign.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkAndRequestNotificationPermission() {//دالة بتفحص إذا التطبيق يملك إذن إرسال إشعارات (فقط في أندرويد 13 وأعلى):
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                new AlertDialog.Builder(this)
                        .setTitle("إذن الإشعارات")
                        .setMessage("نحتاج إلى إذنك لإرسال تنبيهات التسميد والري المهمة لمحاصيلك")
                        .setPositiveButton("موافق", (dialog, which) -> {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                    NOTIFICATION_PERMISSION_REQUEST_CODE);
                        })
                        .setNegativeButton("لاحقًا", null)
                        .show();
            }
        }
    }

    // معالجة نتيجة طلب الإذن
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {//هذي في كل الاحوال سيتم استدعائها لكن انا هنا بس حددت ايش يبعثلو لما يوافق او يرفض
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // تم منح الإذن
                Toast.makeText(this, "شكرًا! سيتم إعلامك بمواعيد التسميد", Toast.LENGTH_SHORT).show();
            } else {
                // تم رفض الإذن
                Toast.makeText(this, "لن تتمكن من استقبال تنبيهات التسميد", Toast.LENGTH_LONG).show();
            }
        }}


//    private void setupNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//            }
//            NotificationManager manager = getSystemService(NotificationManager.class);
//
//            if (manager.getNotificationChannel(FERTILIZER_CHANNEL_ID) == null) {
//                NotificationChannel channel = new NotificationChannel(
//                        FERTILIZER_CHANNEL_ID,
//                        "تنبيهات التسميد",
//                        NotificationManager.IMPORTANCE_HIGH
//                );
//
//                channel.setDescription("تنبيهات مواعيد تسميد المحاصيل");
//                channel.enableLights(true);
//                channel.setLightColor(ContextCompat.getColor(this, R.color.green_L));
//                channel.enableVibration(true);
//                channel.setVibrationPattern(new long[]{100, 200, 300});
//
//                manager.createNotificationChannel(channel);
//            }
//        }
//    }

    private void setupDotsIndicator(int size, LinearLayout dotsLayout) {
        for (int i = 0; i < size; i++) {
            View dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            params.setMargins(8, 0, 8, 0);

            dot.setSelected(i == 0);
            dotsLayout.addView(dot, params);

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    for (int j = 0; j < dotsLayout.getChildCount(); j++) {
                        dotsLayout.getChildAt(j).setSelected(j == position);
                    }
                }
            });
        }
    }
}


