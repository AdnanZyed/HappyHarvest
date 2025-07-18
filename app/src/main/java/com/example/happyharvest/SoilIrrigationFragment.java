package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;

public class SoilIrrigationFragment extends Fragment {
    private Spinner PreferredSoil, AllowedSoil, ForbiddenSoil, PreferredIrrigation,
            AllowedIrrigation, ForbiddenIrrigation, PreferredAbundance,
            AllowedAbundance, ForbiddenAbundance, Previous_crop_preferred,
            Previous_crop_allowed, Previous_crop_forbidden;
    private EditText MinArea, Soil_preparation_Favorite, Preparing_irrigation_tools_P,
            Preparing_irrigation_tools_A, Preparing_irrigation_tools_F, Soil_preparation_allowed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soil_irrigation, container, false);

        PreferredSoil = view.findViewById(R.id.sp_preferredSoil);
        AllowedSoil = view.findViewById(R.id.sp_allowedSoil);
        ForbiddenSoil = view.findViewById(R.id.sp_forbiddenSoil);
        PreferredIrrigation = view.findViewById(R.id.sp_preferredIrrigation);
        AllowedIrrigation = view.findViewById(R.id.sp_allowedIrrigation);
        ForbiddenIrrigation = view.findViewById(R.id.sp_forbiddenIrrigation);
        PreferredAbundance = view.findViewById(R.id.sp_preferredAbundance);
        AllowedAbundance = view.findViewById(R.id.sp_allowedAbundance);
        ForbiddenAbundance = view.findViewById(R.id.sp_forbiddenAbundance);
        Previous_crop_preferred = view.findViewById(R.id.sp_previous_crop_preferred);
        Previous_crop_allowed = view.findViewById(R.id.sp_previous_crop_allowed);
        Previous_crop_forbidden = view.findViewById(R.id.sp_Previous_crop_forbidden);
        MinArea = view.findViewById(R.id.minArea);
        Soil_preparation_Favorite = view.findViewById(R.id.sp_soil_preparation_Favorite);
        Preparing_irrigation_tools_P = view.findViewById(R.id.Preparing_irrigation_tools_P);
        Preparing_irrigation_tools_A = view.findViewById(R.id.Preparing_irrigation_tools_A);
        Preparing_irrigation_tools_F = view.findViewById(R.id.Preparing_irrigation_tools_F);
        Soil_preparation_allowed = view.findViewById(R.id.soil_preparation_allowed);

        return view;
    }

    public Crop getSoilIrrigationInfo(Crop crop) {
        crop.setPreferredSoil(PreferredSoil.getSelectedItem().toString());
        crop.setAllowedSoil(AllowedSoil.getSelectedItem().toString());
        crop.setForbiddenSoil(ForbiddenSoil.getSelectedItem().toString());
        crop.setPreferredIrrigation(PreferredIrrigation.getSelectedItem().toString());
        crop.setAllowedIrrigation(AllowedIrrigation.getSelectedItem().toString());
        crop.setForbiddenIrrigation(ForbiddenIrrigation.getSelectedItem().toString());
        crop.setMinArea(Double.parseDouble(MinArea.getText().toString().trim()));
        crop.setPreferredAbundance(PreferredAbundance.getSelectedItem().toString());
        crop.setAllowedAbundance(AllowedAbundance.getSelectedItem().toString());
        crop.setForbiddenAbundance(ForbiddenAbundance.getSelectedItem().toString());
        crop.setPrevious_crop_preferred(Previous_crop_preferred.getSelectedItem().toString());
        crop.setPrevious_crop_allowed(Previous_crop_allowed.getSelectedItem().toString());
        crop.setPrevious_crop_forbidden(Previous_crop_forbidden.getSelectedItem().toString());
        crop.setSoil_preparation_Favorite(Soil_preparation_Favorite.getText().toString());
        crop.setPreparing_irrigation_tools_P(Preparing_irrigation_tools_P.getText().toString().trim());
        crop.setPreparing_irrigation_tools_A(Preparing_irrigation_tools_A.getText().toString().trim());
        crop.setPreparing_irrigation_tools_F(Preparing_irrigation_tools_F.getText().toString().trim());
        crop.setSoil_preparation_allowed(Soil_preparation_allowed.getText().toString().trim());
        return crop;
    }
}