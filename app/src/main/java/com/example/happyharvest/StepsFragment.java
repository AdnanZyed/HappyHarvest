package com.example.happyharvest;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment {
    public StepsFragment() {
    }
    My_View_Model myViewModel;
    List<ImageView> stars = new ArrayList<>();

    int coursId;
    int currentRating = 0;

    String user;
    String lock;
    int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        if (getArguments() != null) {

            coursId = getArguments().getInt("COURSE_ID");
            user = getArguments().getString("USER");
            lock = getArguments().getString("LOCK");

        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_steps);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        myViewModel.getStepsByCropId(coursId).observe(getViewLifecycleOwner(), steps -> {
            for (i = 0; i < steps.size(); i++) {

                myViewModel.getCompletedStepsForFarmer(user).observe((LifecycleOwner) requireContext(), stepsc -> {
                    for (FarmerStep cl : stepsc) {
                        if (cl.getStepId() == steps.get(i - 1).getS_id()) {
                            //steps.get(i-1).setL_completed(true);
                            CropStep cropSteps = new CropStep();
                            cropSteps.setCrop_ID(steps.get(i - 1).getS_id());
                            cropSteps.setS_completed(true);
                            myViewModel.updateCropStep(cropSteps);


                        } else {
                            CropStep cropSteps = new CropStep();
                            cropSteps.setCrop_ID(steps.get(i - 1).getS_id());
                            cropSteps.setS_completed(false);
                            myViewModel.updateCropStep(cropSteps);

                        }
                    }
                });

            }


            if (steps.get(steps.size() - 1).isS_completed()) {
                myViewModel.getisRatingCropsByFarmer1(user, coursId).observe(getViewLifecycleOwner(), steps1 -> {


                    if (steps1 == null || steps1.isEmpty()) {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {

                            showCustomDialog();
                        }, 1000);
                    }
                });
            }
        });
        myViewModel.getStepsByCropId(coursId).observe(getViewLifecycleOwner(), steps -> {


            CropStepsAdapter adapter = new CropStepsAdapter(myViewModel, steps, user, requireContext(),lock);
            recyclerView.setAdapter(adapter);

        });
        return view;
    }

    public void showCustomDialog() {

        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.complete_crop_dialog);
        dialog.setCancelable(true);


        Button viewCropButton = dialog.findViewById(R.id.btn_view_Crop);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel);


        stars.add(dialog.findViewById(R.id.star1));
        stars.add(dialog.findViewById(R.id.star2));
        stars.add(dialog.findViewById(R.id.star3));
        stars.add(dialog.findViewById(R.id.star4));
        stars.add(dialog.findViewById(R.id.star5));

        for (int i = 0; i < stars.size(); i++) {
            final int index = i;
            stars.get(i).setOnClickListener(v -> setRating(index + 1));
        }

        viewCropButton.setOnClickListener(v -> {
//            Farmer_Crops farmerCrop = new Farmer_Crops(user, coursId, false, true,"",0,false, false, currentRating);
//            myViewModel.updateCropFarmer(farmerCrop);

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();
    }

    private void setRating(int rating) {
        currentRating = rating;
        for (int i = 0; i < stars.size(); i++) {
            if (i < rating) {
                stars.get(i).setImageResource(R.drawable.star);
            } else {
                stars.get(i).setImageResource(R.drawable.starn);
            }
        }
    }
}