package com.example.happyharvest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class CertificatesFragment extends Fragment {

    private int cropId;
    private My_View_Model viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certificates, container, false);
        ImageView imageView = view.findViewById(R.id.Certificates_image);

        viewModel = new ViewModelProvider(this).get(My_View_Model.class);


        if (getArguments() != null) {
            cropId = getArguments().getInt("COURSE_ID", -1);
        }
        viewModel.getAllCropsById(cropId).observe(getViewLifecycleOwner(), Crops -> {
         //   byte[] bitmapBytes = Crops.get(0).getProfilePicture();
//            if (bitmapBytes != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
//                imageView.setImageBitmap(bitmap);
//            } else {
//
//                imageView.setImageResource(R.drawable.certificate);
//
//            }


        });

        return view;
    }
}
