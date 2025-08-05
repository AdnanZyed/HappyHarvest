package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FAQS extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private FursaAdapter adapter;
//    private ArrayList<FursaItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        itemList = new ArrayList<>();
//        adapter = new FursaAdapter(itemList);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(item -> showBottomSheet(item));

        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://fursaty.kicklance.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiService api = retrofit.create(ApiService.class);
//        Call<FursaResponse> call = api.getFursaData();
//
//        call.enqueue(new Callback<FursaResponse>() {
//            @Override
//            public void onResponse(Call<FursaResponse> call, Response<FursaResponse> response) {
//                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
//                    itemList.clear();
//                    itemList.addAll(response.body().getData());
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(FAQS.this, "Failed to load data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FursaResponse> call, Throwable t) {
//                Toast.makeText(FAQS.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void showBottomSheet(FursaItem item) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_fursa, null);
//        bottomSheetDialog.setContentView(view);
//
//        TextView titleTextView = view.findViewById(R.id.title_faqx);
//        TextView descTextView = view.findViewById(R.id.text_faqx);
//
//        titleTextView.setText(item.getTitle());
//        descTextView.setText(item.getDescription());
//
//        bottomSheetDialog.show();
//    }


    }
