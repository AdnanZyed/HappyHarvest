package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;

public class EnvironmentFragment extends Fragment {
    private Spinner PreferredHumidity, AllowedHumidity, ForbiddenHumidity,
            PreferredTemp, AllowedTemp, ForbiddenTemp;
    private EditText Season, OptimalHumidity, OptimalTemperature, TemperatureTolerance,
            HumidityTolerance, LightRequirements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_environment, container, false);

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

        return view;
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