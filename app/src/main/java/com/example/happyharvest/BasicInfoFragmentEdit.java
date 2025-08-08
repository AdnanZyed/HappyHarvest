package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class BasicInfoFragmentEdit extends Fragment {
    private EditText etCropName, etDescription, etExpertUsername;
    private Spinner spCategory;
    private My_View_Model myViewModel;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
         id = getArguments().getInt("ID");

        etCropName = view.findViewById(R.id.et_Crop_nameA);
        etDescription = view.findViewById(R.id.et_Crop_descriptionA);
        etExpertUsername = view.findViewById(R.id.et_expert_usernameA);
        spCategory = view.findViewById(R.id.sp_categoryA);

        loadCurrentData();

        return view;
    }

    private void loadCurrentData() {
        myViewModel.getAllCropsById(id).observe(requireActivity(), crop -> {
            etCropName.setText(crop.get(0).getCrop_NAME());
            etDescription.setText(crop.get(0).getDescription());
            etExpertUsername.setText(crop.get(0).getExpert_USER_Name());

            for (int i = 0; i < spCategory.getCount(); i++) {
                if (spCategory.getItemAtPosition(i).toString().equals(crop.get(0).getCategorie())) {
                    spCategory.setSelection(i);
                    break;
                }
            }
        });


    }

    public Crop getBasicInfo(Crop crop) {
        crop.setCrop_NAME(etCropName.getText().toString().trim());
        crop.setDescription(etDescription.getText().toString().trim());
        crop.setExpert_USER_Name(etExpertUsername.getText().toString().trim());
        crop.setCategorie(spCategory.getSelectedItem().toString());
        return crop;
    }
}