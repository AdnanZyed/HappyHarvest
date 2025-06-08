package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class FarmersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Farmer_Expert> farmersList = new ArrayList<>();
    private List<Farmer> farmersList1 = new ArrayList<>();

    private FarmersAdapter adapter;

    My_View_Model myViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_farmers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewFarmers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        String expertUserName = getArguments().getString("TEACHER_USER_NAME1");


        myViewModel.getFarmersByExpert(expertUserName).observe((LifecycleOwner) requireContext(), farmerExperts -> {
            if (farmerExperts != null && !farmerExperts.isEmpty()) {
                farmersList1.clear();

                for (Farmer_Expert farmerExpert : farmerExperts) {
                    myViewModel.getAllFarmerByUser(farmerExpert.getFarmerUserName()).observe((LifecycleOwner) requireContext(), farmer2 -> {
                        if (farmer2 != null && !farmer2.isEmpty()) {
                            farmersList1.addAll(farmer2);

                            if (farmersList1.size() == farmerExperts.size()) {
                                adapter = new FarmersAdapter(farmersList1, requireContext());
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                }
            } else {
                Toast.makeText(requireContext(), "No farmers found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
