package com.example.happyharvest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewsFragment extends Fragment {
    private My_View_Model myViewModel;
    String farmer_name;
    byte[] bytes;
    ImageView imageView;
    RecyclerView recyclerView;

    Bundle args;
    int rating;
    String userString;
    TextView total_Comments;
    TextView total_Rating;
    EditText edit_Comment;
    ImageView imageSend;
    private RatingBar ratingBar;


    public ReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);
        imageView = view.findViewById(R.id.image_farmer);
        edit_Comment = view.findViewById(R.id.edit_comment);
        total_Comments = view.findViewById(R.id.total_comments);
        total_Rating = view.findViewById(R.id.total_rating);
        imageSend = view.findViewById(R.id.send);
        recyclerView = view.findViewById(R.id.rv_review);
        ratingBar = view.findViewById(R.id.ratingBar);


        Bundle bundle1 = getArguments();
        int cropInt = bundle1.getInt("COURSE_ID1");
        String user = bundle1.getString("USER_ST");


        myViewModel.getAllFarmerByUser(user).observe(getViewLifecycleOwner(), farmers -> {

            if (!farmers.isEmpty() && farmers != null) {
                Farmer farmer = farmers.get(0);
                farmer_name = farmer.getS_name().toString();


                bytes = farmer.getS_Image();
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.drawable.profile);
                }
            }

        });
        myViewModel.getFarmersByCropAndFarmer(user, cropInt).observe((LifecycleOwner) requireContext(), farmerCrops -> {
            if (!farmerCrops.isEmpty() && farmerCrops != null) {

                rating = farmerCrops.get(0).getRating();
            }

        });
        myViewModel.getAllReviewsByCropId(cropInt).observe(requireActivity(), reviews -> {

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            ReviewsAdapter adapter = new ReviewsAdapter(getContext(), reviews);
            recyclerView.setAdapter(adapter);
            int count = reviews.size();
            int f = 0;

            for (int i = 0; i <= reviews.size() - 1; i++) {
                f = +reviews.get(i).getRating();
            }

            total_Comments.setText("" + count);
            total_Rating.setText("" + f);
            ratingBar.setProgress(f);
        });


        edit_Comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    imageSend.setImageResource(R.drawable.send);
                    imageSend.setEnabled(false);
                } else {
                    imageSend.setImageResource(R.drawable.send_message);
                    imageSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imageSend.setOnClickListener(v -> {
            if (!edit_Comment.getText().toString().trim().isEmpty()) {
                LocalDateTime now = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    now = LocalDateTime.now();
                }
                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                }
                String formattedDate = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formattedDate = now.format(formatter);
                }


                Crop_Reviews review = new Crop_Reviews(0, bytes, farmer_name, user,
                        edit_Comment.getText().toString(),
                        formattedDate, 0, cropInt, rating, false);
                myViewModel.insertReview(review);

                edit_Comment.setText(null);
            }
        });


        return view;
    }
}
