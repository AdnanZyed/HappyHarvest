package com.example.happyharvest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AISearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisearch);

        EditText etQuery = findViewById(R.id.et_query);
        Button btnSearch = findViewById(R.id.btn_search);
        TextView tvResults = findViewById(R.id.tv_results);

        btnSearch.setOnClickListener(v -> {
            String query = etQuery.getText().toString();
            if (!query.isEmpty()) {
                // هنا سيتم تنفيذ البحث
                tvResults.setText("نتائج البحث عن: " + query);
            }
        });
    }
}