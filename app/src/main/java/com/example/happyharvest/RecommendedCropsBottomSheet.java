package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class RecommendedCropsBottomSheet extends BottomSheetDialogFragment {

    private List<Crop> recommendedCrops;
    private String user;
    private My_View_Model viewModel;

    public RecommendedCropsBottomSheet(List<Crop> recommendedCrops, String user) {
        this.recommendedCrops = recommendedCrops;
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_recommended_crops, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCrops);
        Button btnClose = view.findViewById(R.id.btnClose);

        CropAdapter adapter = new CropAdapter(getContext(), recommendedCrops, user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnClose.setOnClickListener(v -> dismiss());

        return view;
    }
}