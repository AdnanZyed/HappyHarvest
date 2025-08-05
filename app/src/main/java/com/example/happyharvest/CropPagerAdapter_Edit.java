package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CropPagerAdapter_Edit extends FragmentStateAdapter {

    public CropPagerAdapter_Edit(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new BasicInfoFragmentEdit();
            case 1: return new SoilIrrigationFragmentEdit();
            case 2: return new EnvironmentFragmentEdit();
            case 3: return new CareFragmentEdit();
            case 4: return new ImagesFragmentEdit();
            default: return new BasicInfoFragmentEdit();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}