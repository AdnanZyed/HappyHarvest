package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SoilIrrigationFragmentEdit extends Fragment {
    private Spinner PreferredSoil, AllowedSoil, ForbiddenSoil, PreferredIrrigation,
            AllowedIrrigation, ForbiddenIrrigation, PreferredAbundance,
            AllowedAbundance, ForbiddenAbundance, Previous_crop_preferred,
            Previous_crop_allowed, Previous_crop_forbidden;
    private EditText MinArea, Soil_preparation_Favorite, Preparing_irrigation_tools_P,
            Preparing_irrigation_tools_A, Preparing_irrigation_tools_F, Soil_preparation_allowed;
    private My_View_Model myViewModel;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soil_irrigation, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        id = getArguments().getInt("ID");

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

     loadCurrentData();

        return view;
    }

    private void loadCurrentData() {
        myViewModel.getAllCropsById(id).observe(requireActivity(), currentCrop -> {

            setSpinnerSelection(PreferredSoil, currentCrop.get(0).getPreferredSoil());
            setSpinnerSelection(AllowedSoil, currentCrop.get(0).getAllowedSoil());
            setSpinnerSelection(ForbiddenSoil, currentCrop.get(0).getForbiddenSoil());
            setSpinnerSelection(PreferredIrrigation, currentCrop.get(0).getPreferredIrrigation());
            setSpinnerSelection(AllowedIrrigation, currentCrop.get(0).getAllowedIrrigation());
            setSpinnerSelection(ForbiddenIrrigation, currentCrop.get(0).getForbiddenIrrigation());
            setSpinnerSelection(PreferredAbundance, currentCrop.get(0).getPreferredAbundance());
            setSpinnerSelection(AllowedAbundance, currentCrop.get(0).getAllowedAbundance());
            setSpinnerSelection(ForbiddenAbundance, currentCrop.get(0).getForbiddenAbundance());
            setSpinnerSelection(Previous_crop_preferred, currentCrop.get(0).getPrevious_crop_preferred());
            setSpinnerSelection(Previous_crop_allowed, currentCrop.get(0).getPrevious_crop_allowed());
            setSpinnerSelection(Previous_crop_forbidden, currentCrop.get(0).getPrevious_crop_forbidden());

            MinArea.setText(String.valueOf(currentCrop.get(0).getMinArea()));
            Soil_preparation_Favorite.setText(currentCrop.get(0).getSoil_preparation_Favorite());
            Preparing_irrigation_tools_P.setText(currentCrop.get(0).getPreparing_irrigation_tools_P());
            Preparing_irrigation_tools_A.setText(currentCrop.get(0).getPreparing_irrigation_tools_A());
            Preparing_irrigation_tools_F.setText(currentCrop.get(0).getPreparing_irrigation_tools_F());
            Soil_preparation_allowed.setText(currentCrop.get(0).getSoil_preparation_allowed());
        });
            }



    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
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