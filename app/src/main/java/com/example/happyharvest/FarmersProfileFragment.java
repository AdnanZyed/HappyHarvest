package com.example.happyharvest;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.common.base.MoreObjects;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmersProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private String user,farmerName,farmerBio,farmers_u,BIO,NAME,PASSWORD;
    private Bitmap bitmap;
    private int CARD,PHONE;
    private byte[] imageBytes,image;
    private Button Save;
    private My_View_Model myViewModel;

    private TextView tvFarmerName,tvFarmerBio;

    private ImageView imgFarmerProfile,BtnEditName,BtnEditBio;

    private CropsAdapter adapter;

    private EditText etFarmerName,etFarmerBio;
    private RecyclerView recyclerView;


    public FarmersProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_farmers_profile, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        etFarmerName = view.findViewById(R.id.etFarmerName);

        recyclerView = view.findViewById(R.id.rv_st);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        etFarmerBio = view.findViewById(R.id.etFarmerBio);
        imgFarmerProfile = view.findViewById(R.id.imgFarmerProfile);
        tvFarmerName = view.findViewById(R.id.tvFarmerNameP);
        tvFarmerBio = view.findViewById(R.id.tvFarmerBio);
        Save = view.findViewById(R.id.save);
        BtnEditName = view.findViewById(R.id.btnEditName);
        BtnEditBio = view.findViewById(R.id.btnEditBio);
        user = getArguments().getString("USER_NAME");

        ImageView signOutButton = view.findViewById(R.id.sign_out);
        signOutButton.setOnClickListener(v -> {
            SharedPreferences preferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(requireActivity(), ActivityMainSignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        myViewModel.getisRegisterCropsByFarmer1(user).observe(getViewLifecycleOwner(), farmerCrops -> {
            List<Integer> cropIds = new ArrayList<>();
            for (Farmer_Crops sc : farmerCrops) {
                cropIds.add(sc.getCrop_ID());

            }

            myViewModel.getAllCropsByIds(cropIds).observe(getViewLifecycleOwner(), crops -> {
                adapter = new CropsAdapter(crops, requireContext(), user);
                recyclerView.setAdapter(adapter);


            });
        });


        myViewModel.getAllFarmerByUser(user).observe((LifecycleOwner) requireContext(), farmers -> {
            if (!farmers.isEmpty()) {
                NAME = farmers.get(0).getS_name();
                BIO = farmers.get(0).getBio();
                image = farmers.get(0).getS_Image();
                PASSWORD = farmers.get(0).getFarmer_Password();
                PHONE = farmers.get(0).getPhone_nomber();
                CARD = farmers.get(0).getCard_Number();

                tvFarmerName.setText(NAME);
                tvFarmerBio.setText((BIO == null || BIO.isEmpty()) ? "Welcome" : BIO);

                if (image != null) {
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imgFarmerProfile.setImageBitmap(bitmap);
                } else {
                    imgFarmerProfile.setImageResource(R.drawable.profile);
                }
            }
        });
        imgFarmerProfile.setOnClickListener(v -> openGallery());

        BtnEditName.setOnClickListener(v -> {
            tvFarmerName.setVisibility(View.GONE);
            etFarmerName.setVisibility(View.VISIBLE);
            etFarmerName.setText(tvFarmerName.getText().toString());
            Save.setVisibility(View.VISIBLE);
        });

        BtnEditBio.setOnClickListener(v -> {
            tvFarmerBio.setVisibility(View.GONE);
            etFarmerBio.setVisibility(View.VISIBLE);
            etFarmerBio.setText(tvFarmerBio.getText().toString());
            Save.setVisibility(View.VISIBLE);
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Farmer farmer = new Farmer(user, PASSWORD, PHONE, CARD, etFarmerName.getText().toString(), imageBytes, etFarmerBio.getText().toString());
                myViewModel.updateFarmer(farmer);
                tvFarmerBio.setVisibility(View.VISIBLE);
                etFarmerBio.setVisibility(View.GONE);
                Save.setVisibility(View.GONE);
                tvFarmerName.setVisibility(View.VISIBLE);
                etFarmerName.setVisibility(View.GONE);
                Save.setVisibility(View.GONE);
            }
        });


        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public static FarmersProfileFragment newInstance(String username) {
        FarmersProfileFragment fragment = new FarmersProfileFragment();
        Bundle args = new Bundle();
        args.putString("USER_NAME_R", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                imgFarmerProfile.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

