package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class OngoingFragment extends Fragment {
    private RecyclerView recyclerView;
    private My_View_Model viewModel;
    private String user;
    private int coursId;
    private List<Crop> ongoingCrops = new ArrayList<>();
    private CropsAdapter adapter;

    public OngoingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);
        recyclerView = view.findViewById(R.id.rv_ongoing);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            coursId = getArguments().getInt("COURSE_ID");
            user = getArguments().getString("USER");
        }

        viewModel = new ViewModelProvider(this).get(My_View_Model.class);

        adapter = new CropsAdapter(ongoingCrops, requireContext(), user);
        recyclerView.setAdapter(adapter);

        loadOngoingCrops();

        return view;
    }

    private void loadOngoingCrops() {
        viewModel.getisRegisterCropsByFarmer1(user).observe(getViewLifecycleOwner(), farmerCrops -> {
            List<Integer> cropIds = new ArrayList<>();
            for (Farmer_Crops sc : farmerCrops) {
                cropIds.add(sc.getCrop_ID());
            }

            viewModel.getAllCropsByIds(cropIds).observe(getViewLifecycleOwner(), crops -> {
                List<Crop> tempOngoingCrops = new ArrayList<>();

                for (Crop crop : crops) {
                    viewModel.getTotalStepsCountByCropId(crop.getCrop_ID()).observe(getViewLifecycleOwner(), totalSteps -> {
                        viewModel.getCompletedStepsCountByCropId(crop.getCrop_ID()).observe(getViewLifecycleOwner(), completedSteps -> {
                            if (completedSteps != totalSteps) {
                                tempOngoingCrops.add(crop);
                            }

                            if (tempOngoingCrops.size() > 0) {
                                ongoingCrops.clear();
                                ongoingCrops.addAll(tempOngoingCrops);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    });
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        loadOngoingCrops();

    }
}
