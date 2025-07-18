package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CropPagerAdapter extends FragmentStateAdapter {

    public CropPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BasicInfoFragment();
            case 1:
                return new SoilIrrigationFragment();
            case 2:
                return new EnvironmentFragment();
            case 3:
                return new CareFragment();
            case 4:
                return new ImagesFragment();
            default:
                return new BasicInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}