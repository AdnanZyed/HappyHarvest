package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class InboxFragment extends Fragment {


    String farmers_u;

    public InboxFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout_chat);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager_chat);
        Bundle args = getArguments();
        if (args != null) {
            farmers_u = args.getString("USER_NAME");


        }

        ViewPagerAdapterChat adapter = new ViewPagerAdapterChat(requireActivity(), farmers_u);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chat");
                    break;
                case 1:
                    tab.setText("Call");
                    break;
            }
        }).attach();
        return view;
    }
}