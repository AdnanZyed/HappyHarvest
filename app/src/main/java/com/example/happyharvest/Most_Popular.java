package com.example.happyharvest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class Most_Popular extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private My_View_Model myViewModel1;
    private ImageView searchIcon1;
    private ImageView backIcon1;
    private ImageView imageFound1;
    private TextView t_sory1;
    private TextView t_found1;
    private TextView textMentor1;
    private EditText eSearsh1;
    private String searchQuery1;
    private CropAdapter cropAdapter1;
    private List<Crop> expertList1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular);

        recyclerView1 = findViewById(R.id.rv_experts1);
        t_sory1 = findViewById(R.id.sorry1);
        t_found1 = findViewById(R.id.found1);
        imageFound1 = findViewById(R.id.imageView81);
        searchIcon1 = findViewById(R.id.search_icon1);
        backIcon1 = findViewById(R.id.back_icon1);
        eSearsh1 = findViewById(R.id.e_searsh1);
        textMentor1 = findViewById(R.id.text_mentor1);
        String user = getIntent().getStringExtra("STUDENT_USER");
        eSearsh1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery1 = eSearsh1.getText().toString().trim();
                if (searchQuery1 != null && !searchQuery1.isEmpty()) {
                    searchCrops(searchQuery1);
                } else {
                    loadCrops();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        backIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cropAdapter1 = new CropAdapter(this,new ArrayList<>(), user);
        myViewModel1 = new ViewModelProvider(this).get(My_View_Model.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(cropAdapter1);
        loadCrops();
        searchIcon1.setOnClickListener(v -> {
            searchIcon1.setVisibility(View.GONE);
            textMentor1.setVisibility(View.GONE);
            backIcon1.setVisibility(View.VISIBLE);
            eSearsh1.setVisibility(View.VISIBLE);
        });
        backIcon1.setOnClickListener(v -> {
            eSearsh1.setVisibility(View.GONE);
            searchIcon1.setVisibility(View.VISIBLE);
            textMentor1.setVisibility(View.VISIBLE);
            loadCrops();
        });
    }
    private void searchCrops(String query) {
        myViewModel1.searchCrops(query).observe(this, crops -> {
            if (crops != null && !crops.isEmpty()) {
                cropAdapter1.setCropList(crops);
                recyclerView1.setVisibility(View.VISIBLE);
                t_sory1.setVisibility(View.GONE);
                t_found1.setVisibility(View.GONE);
                imageFound1.setVisibility(View.GONE);
            } else {
                recyclerView1.setVisibility(View.GONE);
                t_sory1.setVisibility(View.VISIBLE);
                t_found1.setVisibility(View.VISIBLE);
                imageFound1.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadCrops() {
        myViewModel1.getAllCrop().observe(this, crops -> {
            if (crops != null && !crops.isEmpty()) {
                cropAdapter1.setCropList(crops);
                recyclerView1.setVisibility(View.VISIBLE);
                t_sory1.setVisibility(View.GONE);
                t_found1.setVisibility(View.GONE);
                imageFound1.setVisibility(View.GONE);
            } else {
                recyclerView1.setVisibility(View.GONE);
                t_sory1.setVisibility(View.VISIBLE);
                t_found1.setVisibility(View.VISIBLE);
                imageFound1.setVisibility(View.VISIBLE);
            }
        });
    }
}