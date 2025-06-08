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

public class TopMentors extends AppCompatActivity {
    private RecyclerView recyclerView;
    private My_View_Model myViewModel;
    private ImageView searchIcon;
    private ImageView backIcon;
    private ImageView imageFound;
    private TextView t_sory;
    private TextView t_found;
    private TextView textMentor;
    private EditText eSearsh;
    private String searchQuery;
    private ExpertAdapterMentors expertAdapter;
    private List<Expert> expertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_mentors);

        recyclerView = findViewById(R.id.rv_experts);
        t_sory = findViewById(R.id.sorry);
        t_found = findViewById(R.id.found);
        imageFound = findViewById(R.id.imageView8);
        searchIcon = findViewById(R.id.search_icon);
        backIcon = findViewById(R.id.back_icon);
        eSearsh = findViewById(R.id.e_searsh);
        textMentor = findViewById(R.id.text_mentor);

        String user = getIntent().getStringExtra("STUDENT_USER");

        eSearsh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = eSearsh.getText().toString().trim();
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    searchExperts(searchQuery);
                } else {
                    loadExpert1();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        expertAdapter = new ExpertAdapterMentors(this, new ArrayList<>(), user);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(expertAdapter);

        loadExpert1();

        searchIcon.setOnClickListener(v -> {
            searchIcon.setVisibility(View.GONE);
            textMentor.setVisibility(View.GONE);
            backIcon.setVisibility(View.VISIBLE);
            eSearsh.setVisibility(View.VISIBLE);
        });

        backIcon.setOnClickListener(v -> {
            eSearsh.setVisibility(View.GONE);
            searchIcon.setVisibility(View.VISIBLE);
            textMentor.setVisibility(View.VISIBLE);
            loadExpert1();
        });
    }

    private void searchExperts(String query) {
        myViewModel.searchExperts(query).observe(this, experts -> {
            if (experts != null && !experts.isEmpty()) {
                expertAdapter.setExpert_MonetorsList(experts);
                recyclerView.setVisibility(View.VISIBLE);
                t_sory.setVisibility(View.GONE);
                t_found.setVisibility(View.GONE);
                imageFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                t_sory.setVisibility(View.VISIBLE);
                t_found.setVisibility(View.VISIBLE);
                imageFound.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadExpert1() {
        myViewModel.getAllExpert().observe(this, experts -> {
            if (experts != null && !experts.isEmpty()) {
                expertAdapter.setExpert_MonetorsList(experts);
                recyclerView.setVisibility(View.VISIBLE);
                t_sory.setVisibility(View.GONE);
                t_found.setVisibility(View.GONE);
                imageFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                t_sory.setVisibility(View.VISIBLE);
                t_found.setVisibility(View.VISIBLE);
                imageFound.setVisibility(View.VISIBLE);
            }
        });
    }
}