package com.example.happyharvest;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FertilizerWorker extends Worker {
    public FertilizerWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // هنا كود التحقق من التأخير وإرسال الإشعارات
        return Result.success();
    }
}