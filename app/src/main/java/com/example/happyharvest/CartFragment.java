package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    String farmers_u;

    My_View_Model myViewModel;

    public CartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        Bundle args = getArguments();
        if (args != null) {
            farmers_u = args.getString("USER_NAME");
        }

        recyclerView = view.findViewById(R.id.recycler_view_Cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        myViewModel.getisAddCartCropsByFarmer(farmers_u).observe((LifecycleOwner) getViewLifecycleOwner(), AddCart -> {


            List<Integer> cropIds = new ArrayList<>();
            for (Farmer_Crops sc : AddCart) {
                if (!sc.isAddCart() || !sc.isRegister()) {
                    cropIds.add(sc.getCrop_ID());


                }
            }

            myViewModel.getAllCropsByIds(cropIds).observe(getViewLifecycleOwner(), crops -> {

                CropsAdapter adapter = new CropsAdapter(crops, requireContext(), farmers_u);
                recyclerView.setAdapter(adapter);

            });
        });


        return view;
    }
}