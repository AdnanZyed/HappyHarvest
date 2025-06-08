package com.example.happyharvest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageAnalysisResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_analysis_result);

        ImageView ivImage = findViewById(R.id.iv_image);
        TextView tvResults = findViewById(R.id.tv_results);

        Bitmap image = getIntent().getParcelableExtra("image_bitmap");
        int cropId = getIntent().getIntExtra("crop_id", 0);

        if (image != null) {
            ivImage.setImageBitmap(image);
            tvResults.setText("نتائج تحليل صورة المحصول:\nتم اكتشاف مشاكل صحية بنسبة 85%");
        }
    }
}