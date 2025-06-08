package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CropDetailsPagerAdapter extends FragmentStateAdapter {

    private final int cropId;
    private final String User;

    public CropDetailsPagerAdapter(FragmentActivity fa, int cropId,String User) {
        super(fa);
        this.cropId = cropId;
        this.User = User;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return DailyCareFragment.newInstance(cropId,User);
            case 1:
                return PlantingInfoFragment.newInstance(cropId);
            case 2:
                return ProblemsSolutionsFragment.newInstance(cropId);
            case 3:
                return LearnMore.newInstance(cropId);
            default:
                return DailyCareFragment.newInstance(cropId,User);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}