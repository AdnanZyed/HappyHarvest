package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CropsFragment extends Fragment {


    public CropsFragment() {
    }

    private RecyclerView recyclerView;
    private My_View_Model viewModel;

    private My_Database myDatabase;
   private String farmers_u;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cropss, container, false);

        ImageView imageView = view.findViewById(R.id.back_icon_enrollC1);
        Bundle args = getArguments();
        if (args != null) {
            farmers_u = args.getString("USER_NAME");


        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        TabLayout tabLayout = view.findViewById(R.id.tab_layout1);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager1);

        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity(), 1, farmers_u);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Ongoing");
                    break;
                case 1:
                    tab.setText("Completed");
                    break;
            }
        }).attach();
        return view;
    }
}