package com.example.happyharvest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class Tab_profile_Adapter
        extends FragmentStateAdapter {
    private final String expertUserName;
    private final String user;

    public Tab_profile_Adapter(@NonNull FragmentActivity fragmentActivity, String expertUserName, String user) {
        super(fragmentActivity);
        this.expertUserName = expertUserName;
        this.user = user;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:

                CropsProfileFragment cropsProfileFragment = new CropsProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TEACHER_USER_NAME1", expertUserName);
                cropsProfileFragment.setArguments(bundle);
                return cropsProfileFragment;

            case 1:
                FarmersFragment farmersFragment = new FarmersFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("TEACHER_USER_NAME1", expertUserName);
                farmersFragment.setArguments(bundle2);
                return farmersFragment;
            case 2:
                ReviewsExpertFragment reviewsExpertFragment = new ReviewsExpertFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("TEACHER_USER_NAME1", expertUserName);
                bundle3.putString("STUDENT_USER_NAME1", user);
                reviewsExpertFragment.setArguments(bundle3);
                return reviewsExpertFragment;
            default:
                CropsProfileFragment cropsProfileFragment1 = new CropsProfileFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("TEACHER_USER_NAME1", expertUserName);
                cropsProfileFragment1.setArguments(bundle1);
                return cropsProfileFragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}


