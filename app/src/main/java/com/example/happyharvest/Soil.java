package com.example.happyharvest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class Soil extends AppCompatActivity {

    private Spinner colorSpinner, textureSpinner, stickinessSpinner, absorptionSpinner, rocksSpinner, clumpingSpinner;
    private TextView resultText;
    private Button detectButton;
    private Button button_suppmet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil);

        colorSpinner = findViewById(R.id.spinner_color);
        textureSpinner = findViewById(R.id.spinner_texture);
        stickinessSpinner = findViewById(R.id.spinner_stickiness);
        absorptionSpinner = findViewById(R.id.spinner_absorption);
        rocksSpinner = findViewById(R.id.spinner_rocks);
        clumpingSpinner = findViewById(R.id.spinner_clumping);
        resultText = findViewById(R.id.text_result);
        detectButton = findViewById(R.id.button_detect);
        button_suppmet = findViewById(R.id.button_Suppmet);

        String[] colors = {"فاتحة", "داكنة", "رمادية", "صفراء"};
        String[] textures = {"مفككة", "لزجة", "متماسكة"};
        String[] stickiness = {"لا تلتصق", "تلتصق قليلاً", "تلتصق بشدة"};
        String[] absorption = {"سريعة", "متوسطة", "بطيئة"};
        String[] rocks = {"لا يوجد", "قليل", "كثير"};
        String[] clumping = {"لا", "نعم"};

        setupSpinner(colorSpinner, colors);
        setupSpinner(textureSpinner, textures);
        setupSpinner(stickinessSpinner, stickiness);
        setupSpinner(absorptionSpinner, absorption);
        setupSpinner(rocksSpinner, rocks);
        setupSpinner(clumpingSpinner, clumping);

        detectButton.setOnClickListener(v -> {
            Map<String, String> answers = new HashMap<>();
            answers.put("color", colorSpinner.getSelectedItem().toString());
            answers.put("texture", textureSpinner.getSelectedItem().toString());
            answers.put("stickiness", stickinessSpinner.getSelectedItem().toString());
            answers.put("absorption", absorptionSpinner.getSelectedItem().toString());
            answers.put("rocks", rocksSpinner.getSelectedItem().toString());
            answers.put("clumping", clumpingSpinner.getSelectedItem().toString());

            String soilType = detectSoilType(answers);
            resultText.setText("نوع التربة: " + soilType);
        });
        button_suppmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Soil.this,FarmInput.class);
                intent.putExtra("resultText",resultText.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private String detectSoilType(Map<String, String> answers) {
        int clayScore = 0;
        int sandScore = 0;
        int gravelScore = 0;

        String color = answers.get("color");
        if (color.equals("داكنة")) clayScore++;
        else if (color.equals("فاتحة")) sandScore++;
        else if (color.equals("رمادية") || color.equals("صفراء")) gravelScore++;

        String texture = answers.get("texture");
        if (texture.contains("لزجة")) clayScore++;
        else if (texture.contains("مفككة")) sandScore++;

        String stickiness = answers.get("stickiness");
        if (stickiness.equals("تلتصق بشدة")) clayScore++;
        else if (stickiness.equals("لا تلتصق")) sandScore++;

        String absorb = answers.get("absorption");
        if (absorb.equals("بطيئة")) clayScore++;
        else if (absorb.equals("سريعة")) sandScore++;

        String rocks = answers.get("rocks");
        if (rocks.equals("كثير")) gravelScore++;

        String clumping = answers.get("clumping");
        if (clumping.equals("نعم")) clayScore++;
        else sandScore++;

        if (clayScore >= sandScore && clayScore >= gravelScore) return "muddy";
        if (sandScore >= clayScore && sandScore >= gravelScore) return "sandy";
        if (gravelScore > clayScore && gravelScore > sandScore) return "rocky";

        return "طميية"; // الافتراضي
    }
}
