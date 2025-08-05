package com.example.happyharvest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final int cropId;
    private final String user;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int cropId, String user) {
        super(fragmentActivity);
        this.cropId = cropId;
        this.user = user;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                OngoingFragment ongoingFragment = new OngoingFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("ID", cropId);
                bundle1.putString("USER", user);
                ongoingFragment.setArguments(bundle1);
                return ongoingFragment;
            case 1:
                CompletedFragment completedFragment = new CompletedFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("ID", cropId);
                bundle2.putString("USER", user);
                completedFragment.setArguments(bundle2);
                return completedFragment;
            default:
                OngoingFragment ongoingFragment1 = new OngoingFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("USER", user);
                bundle3.putInt("ID", cropId);
                ongoingFragment1.setArguments(bundle3);
                return ongoingFragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
