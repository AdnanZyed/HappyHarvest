
package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class PlantingInfoFragment extends Fragment {

    private static final String ARG_CROP_ID = "crop_id";

    private TextView textPlantingMethod, textSoilPref, textIrrigationPref, textTempPref;
    private My_View_Model myViewModel;
    private int cropId;

    public static PlantingInfoFragment newInstance(int cropId) {
        PlantingInfoFragment fragment = new PlantingInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CROP_ID, cropId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cropId = getArguments().getInt(ARG_CROP_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planting_info, container, false);

        textPlantingMethod = view.findViewById(R.id.text_planting_method);
        textSoilPref = view.findViewById(R.id.text_soil_pref);
        textIrrigationPref = view.findViewById(R.id.text_irrigation_pref);
        textTempPref = view.findViewById(R.id.text_temp_pref);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);
        myViewModel.getAllCropsById(cropId).observe(getViewLifecycleOwner(), crop -> {
            if (crop != null) {
                textPlantingMethod.setText(crop.get(0).getPlantingMethod());
                textSoilPref.setText("التربة المفضلة: " + crop.get(0).getPreferredSoil());
                textIrrigationPref.setText("نظام الري المفضل: " + crop.get(0).getPreferredIrrigation());
                textTempPref.setText("درجة الحرارة المفضلة: " + crop.get(0).getPreferredTemp());
            }
        });
    }
}
