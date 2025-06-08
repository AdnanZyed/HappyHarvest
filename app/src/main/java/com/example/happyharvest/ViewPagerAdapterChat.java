package com.example.happyharvest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterChat extends FragmentStateAdapter {

    private final String user;

    public ViewPagerAdapterChat(@NonNull FragmentActivity fragmentActivity, String user) {
        super(fragmentActivity);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                ChatFragment chatFragment = new ChatFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("USER", user);

                chatFragment.setArguments(bundle1);
                return chatFragment;
            case 1:
                CallFragment callFragment = new CallFragment();
                Bundle bundle = new Bundle();
                bundle.putString("USER", user);
                callFragment.setArguments(bundle);
                return callFragment;
            default:
                ChatFragment chatFragment1 = new ChatFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("USER", user);
                chatFragment1.setArguments(bundle2);
                return chatFragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
