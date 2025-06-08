package com.example.happyharvest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabPagerAdapter extends FragmentStateAdapter {
    private final String expertUserName;
    private final String cropDescription;
    private final int cropId;
    private final String user;


    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity, String expertUserName, int cropId, String user, String cropDescription) {
        super(fragmentActivity);
        this.expertUserName = expertUserName;
        this.cropDescription = cropDescription;
        this.cropId = cropId;
        this.user = user;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // AboutFragment
                AboutFragment aboutFragment = new AboutFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TEACHER_USER_NAME1", expertUserName);
                bundle.putString("cropDescription", cropDescription);
                bundle.putString("STUDENT_USER", user);


                aboutFragment.setArguments(bundle);

                return aboutFragment;
            case 1:
                StepsFragment stepsFragment = new StepsFragment();
                Bundle bundle4 = new Bundle();
                bundle4.putInt("COURSE_ID", cropId);
                bundle4.putString("USER", user);
                bundle4.putString("LOCK", user);

                stepsFragment.setArguments(bundle4);
                return stepsFragment;
            case 2:
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("COURSE_ID1", cropId);
                bundle2.putString("USER_ST", user);
                reviewsFragment.setArguments(bundle2);
                return reviewsFragment;
            default:
                AboutFragment aboutFragment1 = new AboutFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("TEACHER_USER_NAME1", expertUserName);
                bundle1.putString("cropDescription", cropDescription);
                bundle1.putString("STUDENT_USER", user);

                aboutFragment1.setArguments(bundle1);
                return aboutFragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
