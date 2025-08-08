package com.example.happyharvest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CropPagerAdapter_Edit extends FragmentStateAdapter {
    int id;

    public CropPagerAdapter_Edit(@NonNull FragmentActivity fragmentActivity, int id) {
        super(fragmentActivity);
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                BasicInfoFragmentEdit basicInfoFragmentEdit = new BasicInfoFragmentEdit();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                basicInfoFragmentEdit.setArguments(bundle);
                return  basicInfoFragmentEdit;
            case 1:
                SoilIrrigationFragmentEdit soilIrrigationFragmentEdit = new SoilIrrigationFragmentEdit();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("ID", id);
                soilIrrigationFragmentEdit.setArguments(bundle1);
                return  soilIrrigationFragmentEdit;
            case 2:
                EnvironmentFragmentEdit environmentFragmentEdit = new EnvironmentFragmentEdit();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("ID", id);
                environmentFragmentEdit.setArguments(bundle2);
                return  environmentFragmentEdit;
            case 3:
                CareFragmentEdit careFragmentEdit = new CareFragmentEdit();
                Bundle bundle3 = new Bundle();
                bundle3.putInt("ID", id);
                careFragmentEdit.setArguments(bundle3);
                return  careFragmentEdit;
            case 4:
                ImagesFragmentEdit imagesFragmentEdit = new ImagesFragmentEdit();
                Bundle bundle4 = new Bundle();
                bundle4.putInt("ID", id);
                imagesFragmentEdit.setArguments(bundle4);
                return  imagesFragmentEdit;
            default:
                BasicInfoFragmentEdit basicInfoFragmentEdit2 = new BasicInfoFragmentEdit();
                Bundle bundle5 = new Bundle();
                bundle5.putInt("ID", id);
                basicInfoFragmentEdit2.setArguments(bundle5);
                return  basicInfoFragmentEdit2;        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}