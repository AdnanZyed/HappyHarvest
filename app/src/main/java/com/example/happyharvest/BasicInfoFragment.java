package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;

public class BasicInfoFragment extends Fragment {
    private EditText etCropName, etDescription, etExpertUsername;
    private Spinner spCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);

        etCropName = view.findViewById(R.id.et_Crop_nameA);
        etDescription = view.findViewById(R.id.et_Crop_descriptionA);
        etExpertUsername = view.findViewById(R.id.et_expert_usernameA);
        spCategory = view.findViewById(R.id.sp_categoryA);

        return view;
    }

    public Crop getBasicInfo(Crop crop) {
        crop.setCrop_NAME(etCropName.getText().toString().trim());
        crop.setDescription(etDescription.getText().toString().trim());
        crop.setExpert_USER_Name(etExpertUsername.getText().toString().trim());
        crop.setCategorie(spCategory.getSelectedItem().toString());
        return crop;
    }
}