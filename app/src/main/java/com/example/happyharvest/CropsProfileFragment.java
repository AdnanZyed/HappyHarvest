package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class CropsProfileFragment extends Fragment {
    private My_View_Model myViewModel;
    private CropAdapter cropAdapter;

    public CropsProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_crops_profile, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_Crops1);
        String expertUserName = getArguments().getString("EXPERT_USER_NAME1");


        cropAdapter = new CropAdapter(requireContext(), new ArrayList<Crop>(), "");
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(cropAdapter);

        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);


        myViewModel.getAllcropsByExpert_USER_Name("jane_smith").observe(getViewLifecycleOwner(), crops -> {
            cropAdapter.setCropList(crops);
            cropAdapter.setOnCropClickListener(crop -> {

                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);

                intent.putExtra("COURSE_ID", crop.getCrop_ID());
                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
              //  intent.putExtra("COURSE_IMAGE", crop.getImage());
              //  intent.putExtra("TEACHER_NAME", crop.getExpert_name());
                intent.putExtra("COURSE_CATEGORIES", crop.getCategorie());
                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());


                startActivity(intent);
            });

        });


        return view;
    }
}