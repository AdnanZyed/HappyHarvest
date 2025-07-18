package com.example.happyharvest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FarmingMethodBottomSheet extends BottomSheetDialogFragment {

    private int id;
    private String user;

    public FarmingMethodBottomSheet(int id, String user) {
        this.id = id;
        this.user = user;
    }

    public FarmingMethodBottomSheet() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_farming_methods, container, false);

        // تعريف العناصر
        CardView cvGreenhouse = view.findViewById(R.id.cv_greenhouse);
        CardView cvOpenField = view.findViewById(R.id.cv_open_field);
        CardView cvHydroponic = view.findViewById(R.id.cv_hydroponic);
        CardView cvOrganic = view.findViewById(R.id.cv_organic);

        // إضافة مستمعين للنقر
        cvGreenhouse.setOnClickListener(v -> navigateToFarmInput("greenhouse"));
        cvOpenField.setOnClickListener(v -> navigateToFarmInput("open_field"));
        cvHydroponic.setOnClickListener(v -> navigateToFarmInput("hydroponic"));
        cvOrganic.setOnClickListener(v -> navigateToFarmInput("organic"));

        return view;
    }

    private void navigateToFarmInput(String farmingMethod) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), FarmInput.class);
            intent.putExtra("USER",user);
            intent.putExtra("ID",id);
            // إضافة البيانات الإضافية
            intent.putExtra("farming_method", farmingMethod);

            // إذا كنت تحتاج إلى تمرير بيانات أخرى من النشاط الأصلي
            if (getArguments() != null) {
                intent.putExtras(getArguments());
            }

            startActivity(intent);
            dismiss();
        }
    }
}

