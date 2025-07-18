package com.example.happyharvest;
import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "happy_harvest_channel";
    private static final int NOTIFICATION_ID_BASE = 1000;



//    ✅ شرح مبسط لكل جزء:
//            ✅ onReceive(...)
//    هذه الدالة تُستدعى عندما يتم استقبال البث (broadcast)، وتقوم بـ:
//
//    التأكد إن فيه بيانات (cropName).
//
//    استخراج باقي البيانات مثل نوع السماد، الكمية، ونوع التذكير (ري أو تسميد).
//
//    إنشاء PendingIntent ليفتح واجهة تفاصيل المحصول عند الضغط على الإشعار.
//
//    بناء الإشعار برسالة مخصصة.
//
//    إرسال الإشعار إن كان الإذن موجود.
    @Override
    public void onReceive(Context context, Intent intent) {
        // 1. التحقق من وجود البيانات الأساسية
        if (intent == null || !intent.hasExtra("cropName")) {
            return;
        }

        String cropName = intent.getStringExtra("cropName");
        String fertilizerName = intent.getStringExtra("fertilizerName");
        String type = intent.getStringExtra("type");
        double amount = intent.getDoubleExtra("amount", 0);
        int notificationId = intent.getIntExtra("notificationId", 0) + NOTIFICATION_ID_BASE;

        PendingIntent pendingIntent = createPendingIntent(context);

        Notification notification = buildNotification(context, cropName, fertilizerName, type, amount, pendingIntent);

        sendNotification(context, notificationId, notification);
    }
//ينشئ Intent يفتح CropDetailsActivity1 لما المستخدم يضغط على الإشعار.
    @NonNull
    private PendingIntent createPendingIntent(Context context) {
        Intent mainIntent = new Intent(context, CropDetailsActivity1.class);
        return PendingIntent.getActivity(
                context,
                0,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
//يبني الإشعار نفسه، باستخدام NotificationCompat ويدعمه بنص مفصل باستخدام BigTextStyle.

    @NonNull
    private Notification buildNotification(Context context, String cropName,
                                           String fertilizerName, String type,
                                           double amount, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.fertilizer)
                .setContentTitle(context.getString(R.string.notification_title, cropName))
                .setContentText(context.getString(R.string.notification_content, fertilizerName))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.notification_details,
                                cropName, type, fertilizerName,
                                String.format(Locale.getDefault(), "%.1f", amount))))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();
    }
//يتحقق من وجود الإذن (POST_NOTIFICATIONS) قبل ما يرسل الإشعار عبر NotificationManagerCompat.
    private void sendNotification(Context context, int notificationId, Notification notification) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(notificationId, notification);
        } else {
            // يمكنك تسجيل هذه الحالة للتحليل لاحقاً
        }
    }
}