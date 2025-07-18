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
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewsExpertFragment extends Fragment {
    private My_View_Model myViewModel;
    private String farmer_name;
    private byte[] bytes;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Spinner ratingSpinner;

    private EditText edit_Comment;
    private ImageView imageSend;

    public ReviewsExpertFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reviwes_expert, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);
        imageView = view.findViewById(R.id.image_farmer1);
        edit_Comment = view.findViewById(R.id.edit_comment1);

        ratingSpinner = view.findViewById(R.id.rating1);
        imageSend = view.findViewById(R.id.send1);

        recyclerView = view.findViewById(R.id.rv_review1);
        String expertUserName = getArguments().getString("TEACHER_USER_NAME1");
        String user = getArguments().getString("STUDENT_USER_NAME1");

        myViewModel.getAllFarmerByUser(user).observe(getViewLifecycleOwner(), farmers -> {

            Farmer farmer = farmers.get(0);
            farmer_name = farmer.getS_name().toString();


            bytes = farmer.getS_Image();
            if (bytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.profile);
            }


        });
        myViewModel.getAllReviewsByCropIdT(expertUserName).observe(requireActivity(), reviews -> {

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            ReviewsExpertAdapter adapter = new ReviewsExpertAdapter(getContext(), reviews);
            recyclerView.setAdapter(adapter);
            int count = reviews.size();
            int f = 0;

            for (int i = 0; i <= reviews.size() - 1; i++) {
                f += reviews.get(i).getRating();
            }


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
                    imageSend.setImageResource(R.drawable.unnamed);
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

                int rating_spinner = Integer.parseInt(ratingSpinner.getSelectedItem().toString());

                Expert_Reviews review = new Expert_Reviews(0, bytes, farmer_name, user,
                        edit_Comment.getText().toString(),
                        formattedDate, 0, expertUserName, rating_spinner, false);
                myViewModel.insertReviewT(review);

                edit_Comment.setText(null);
            }
        });


        return view;
    }
}
