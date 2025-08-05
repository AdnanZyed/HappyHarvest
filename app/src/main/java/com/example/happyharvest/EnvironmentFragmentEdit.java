package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EnvironmentFragmentEdit extends Fragment {
    private Spinner PreferredHumidity, AllowedHumidity, ForbiddenHumidity,
            PreferredTemp, AllowedTemp, ForbiddenTemp;
    private EditText Season, OptimalHumidity, OptimalTemperature, TemperatureTolerance,
            HumidityTolerance, LightRequirements;
    private My_View_Model myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_environment, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        PreferredHumidity = view.findViewById(R.id.sp_preferredHumidity);
        AllowedHumidity = view.findViewById(R.id.sp_allowedHumidity);
        ForbiddenHumidity = view.findViewById(R.id.sp_forbiddenHumidity);
        PreferredTemp = view.findViewById(R.id.sp_preferredTemp);
        AllowedTemp = view.findViewById(R.id.sp_allowedTemp);
        ForbiddenTemp = view.findViewById(R.id.sp_forbiddenTemp);
        Season = view.findViewById(R.id.season);
        OptimalHumidity = view.findViewById(R.id.optimalHumidity);
        OptimalTemperature = view.findViewById(R.id.optimalTemperature);
        TemperatureTolerance = view.findViewById(R.id.temperatureTolerance);
        HumidityTolerance = view.findViewById(R.id.humidityTolerance);
        LightRequirements = view.findViewById(R.id.lightRequirements);

      loadCurrentData();

        return view;
    }

    private void loadCurrentData() {
        myViewModel.getAllCropsById(1).observe(requireActivity(), currentCrop -> {

            setSpinnerSelection(PreferredHumidity, currentCrop.get(0).getPreferredHumidity());
            setSpinnerSelection(AllowedHumidity, currentCrop.get(0).getAllowedHumidity());
            setSpinnerSelection(ForbiddenHumidity, currentCrop.get(0).getForbiddenHumidity());
            setSpinnerSelection(PreferredTemp, currentCrop.get(0).getPreferredTemp());
            setSpinnerSelection(AllowedTemp, currentCrop.get(0).getAllowedTemp());
            setSpinnerSelection(ForbiddenTemp, currentCrop.get(0).getForbiddenTemp());

            Season.setText(currentCrop.get(0).getSeason());
            OptimalHumidity.setText(String.valueOf(currentCrop.get(0).getOptimalHumidity()));
            OptimalTemperature.setText(String.valueOf(currentCrop.get(0).getOptimalTemperature()));
            TemperatureTolerance.setText(String.valueOf(currentCrop.get(0).getTemperatureTolerance()));
            HumidityTolerance.setText(String.valueOf(currentCrop.get(0).getHumidityTolerance()));
            LightRequirements.setText(String.valueOf(currentCrop.get(0).getLightRequirements()));
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

    public Crop getEnvironmentInfo(Crop crop) {
        crop.setPreferredHumidity(PreferredHumidity.getSelectedItem().toString());
        crop.setAllowedHumidity(AllowedHumidity.getSelectedItem().toString());
        crop.setForbiddenHumidity(ForbiddenHumidity.getSelectedItem().toString());
        crop.setPreferredTemp(PreferredTemp.getSelectedItem().toString());
        crop.setAllowedTemp(AllowedTemp.getSelectedItem().toString());
        crop.setForbiddenTemp(ForbiddenTemp.getSelectedItem().toString());
        crop.setSeason(Season.getText().toString().trim());
        crop.setOptimalHumidity(Float.parseFloat(OptimalHumidity.getText().toString().trim()));
        crop.setOptimalTemperature(Float.parseFloat(OptimalTemperature.getText().toString().trim()));
        crop.setTemperatureTolerance(Float.parseFloat(TemperatureTolerance.getText().toString().trim()));
        crop.setHumidityTolerance(Float.parseFloat(HumidityTolerance.getText().toString().trim()));
        crop.setLightRequirements(Integer.parseInt(LightRequirements.getText().toString().trim()));
        return crop;
    }
}