package com.example.happyharvest;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FertilizingDetailActivity extends AppCompatActivity {

    private TextView txtType, txtStart, txtEnd, txtCount, txtCurrent, txtRemaining, txtNotes, txtinf, txtinf1;
    private ProgressBar progressBar;
    private PieChart pieChart;
    int s;
    int a;
    int b;
    private Button btnDone;
    Farmer_Crops crop;
    private My_View_Model viewModel;
    private String user;
    private int cropId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizing);

        Intent intent = getIntent();
        user = intent.getStringExtra("USER");
        cropId = intent.getIntExtra("ID", -1);

        txtType = findViewById(R.id.txtType);
        txtStart = findViewById(R.id.txtStart);
        txtinf = findViewById(R.id.txtinf);
        txtinf1 = findViewById(R.id.txtinf1);
        txtEnd = findViewById(R.id.txtEnd);
        txtCount = findViewById(R.id.txtCount);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtRemaining = findViewById(R.id.txtRemaining);
        txtNotes = findViewById(R.id.txtNotes);
        progressBar = findViewById(R.id.progressBar1);
        pieChart = findViewById(R.id.pieChart);
        btnDone = findViewById(R.id.btnDone);

        viewModel = new ViewModelProvider(this).get(My_View_Model.class);
        viewModel.getAllCropsById(cropId).observe(this, crop1 -> {
            a = crop1.get(0).getDaysToMaturity();
            b = crop1.get(0).getFertilizingFrequencyDays();
            s = a / b;
        });
        viewModel.getFarmersByCropAndFarmer(user, cropId).observe(this, crops -> {
            if (crops == null || crops.isEmpty()) {
                Toast.makeText(this, "لم يتم العثور على بيانات التسميد", Toast.LENGTH_SHORT).show();
                return;
            }

            crop = crops.get(0); // الحصول على أول عنصر فقط
//            fertilizingInstructions تعليمات التسميد
//            daysToMaturity الوقت حتى النضج
//            organicFertilizer
//            organicPerPlant
//            chemicalFertilizer
//            chemicalPerPlant
//


            txtType.setText("نوع التسميد: " + crop.getChemicalFertilizer());
            txtinf.setText("معلومات التسميد: " + crop.getFertilizerInfo());
            txtinf1.setText("معلومات التسميد: " + crop.getFertilizingInfo());
            txtStart.setText("بداية الجدول: " + crop.getStartDate());
            txtEnd.setText("عدد الايام المتبقية: " + s);  // تأكد أن getEndDate() موجود في كلاس Crop
            txtCount.setText("عدد مرات التسميد: " + crop.getFertilizingFrequencyDays_F());
            txtCurrent.setText("آخر مرة تم التسميد فيها: " + crop.getLastFertilizerDate());

            long remainingMillis = crop.getNextFertilizingDate().getTime() - System.currentTimeMillis();
            if (remainingMillis > 0) {
                long days = TimeUnit.MILLISECONDS.toDays(remainingMillis);
                long hours = TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24;
                txtRemaining.setText("تبقى: " + days + " يوم و" + hours + " ساعة");
            } else {
                txtRemaining.setText("انتهى وقت التسميد، الرجاء التسميد فورًا");
            }

            int totalTimes = s;       // عدد مرات التسميد الكلي
            int currentTime = s;     // عدد مرات التسميد التي تم تنفيذها
            int progress = totalTimes > 0 ? (currentTime * 100 / totalTimes) : 0;

            progressBar.setProgress(progress);

            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(progress, "تم"));
            entries.add(new PieEntry(100 - progress, "متبقي"));

            PieDataSet dataSet = new PieDataSet(entries, "حالة التسميد");
            PieData pieData = new PieData(dataSet);
            pieChart.setData(pieData);

            Description description = new Description();
            description.setText("نسبة الإنجاز");
            pieChart.setDescription(description);
            pieChart.invalidate();

            txtNotes.setText("ملاحظات: يفضل التسميد صباحًا وعدم التسميد قبل المطر.");
        });

        btnDone.setOnClickListener(v -> {
            crop.setDone(true);
            Toast.makeText(this, "تم تسجيل عملية التسميد بنجاح", Toast.LENGTH_SHORT).show();
        });
    }
}
