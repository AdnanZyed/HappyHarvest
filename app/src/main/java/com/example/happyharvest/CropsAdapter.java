package com.example.happyharvest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CropsAdapter extends RecyclerView.Adapter<CropsAdapter.CropViewHolder> {

    public Crop crop1;
    boolean isCompleted;

    private final List<Crop> cropList;
    private final Context context;
    private final My_View_Model viewModel;
    private final String user;

    public CropsAdapter(List<Crop> cropsList, Context context, String user) {
        this.cropList = cropsList;
        this.context = context;
        this.user = user;
        this.viewModel = new ViewModelProvider((AppCompatActivity) context).get(My_View_Model.class);

    }


    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crop_regestered, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop = cropList.get(position);

        holder.bind(crop);

        crop1 = crop;

    }

    @Override
    public int getItemCount() {
        return cropList != null ? cropList.size() : 0;
    }

    public boolean completed() {

        viewModel.getTotalStepsCountByCropId(crop1.getCrop_ID()).observe((AppCompatActivity) context, totalSteps1 -> {
            int total = totalSteps1;


            viewModel.getCompletedStepsCountByCropId(crop1.getCrop_ID()).observe((AppCompatActivity) context, completedSteps1 -> {
                int complete = completedSteps1;


                if (total == complete) {


                    isCompleted = true;

                } else {
                    isCompleted = false;


                }


            });
        });
        return isCompleted;

    }

    class CropViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cropImage;
        private final TextView cropName;
        private final TextView cropTime;
        private final ProgressBar progressBar;
        private int progress;
        private final TextView stepsNumR;
        private final TextView numStepR;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);

            cropImage = itemView.findViewById(R.id.iv_Crop_imageR);
            cropName = itemView.findViewById(R.id.tv_Crop_nameR);
            cropTime = itemView.findViewById(R.id.time_CropR);
            progressBar = itemView.findViewById(R.id.progressBarR);
            stepsNumR = itemView.findViewById(R.id.steps_numR);
            numStepR = itemView.findViewById(R.id.numstepR);
            String Time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            cropTime.setText(Time);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Crop selectedCrop = cropList.get(position);
                    Intent intent = new Intent(context, CropDetailsActivity1.class);
                    intent.putExtra("ID", selectedCrop.getCrop_ID());
                    intent.putExtra("USER", user);
                    context.startActivity(intent);

                }

            });

        }


        public void bind(Crop crop) {


            cropName.setText(crop.getCrop_NAME());


//            if (crop.getImage() != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(crop.getImage(), 0, crop.getImage().length);
//                cropImage.setImageBitmap(bitmap);
//            } else {
//                cropImage.setImageResource(R.drawable.unnamed);
//            }

//            viewModel.getTotalStepsCountByCropId(crop.getCrop_ID()).observe((AppCompatActivity) context, totalSteps -> {
//                numStepR.setText(String.valueOf(totalSteps));
//
//
//            });
//
//            viewModel.getCompletedStepsCountByCropId(crop.getCrop_ID()).observe((AppCompatActivity) context, completedSteps -> {
//                stepsNumR.setText(String.valueOf(completedSteps));
//
//            });
//
//            viewModel.getTotalStepsTimeByCropId(crop.getCrop_ID()).observe((AppCompatActivity) context, totalTime -> {
//                cropTime.setText(String.format("%d min", totalTime));
//            });


            viewModel.fetchFarmerCrop(user, crop.getCrop_ID()).observe((AppCompatActivity) context, farmerCropList -> {
                if (farmerCropList != null && !farmerCropList.isEmpty()) {
                    Farmer_Crops farmerCrops = farmerCropList.get(0);
                    if (farmerCrops.isDone()) {
                        progressBar.setProgress(4);
                        progress = 4;
                    } else if (farmerCrops.isCare()) {
                        progressBar.setProgress(3);
                        progress = 3;

                    } else if (farmerCrops.isAgriculture()) {
                        progressBar.setProgress(2);
                        progress = 2;

                    } else if (farmerCrops.isSoil()) {
                        progressBar.setProgress(1);
                        progress = 1;

                    }
                } else {
                }
                numStepR.setText(String.valueOf(4));
                stepsNumR.setText(String.valueOf(progress));
//                viewModel.getCompletedStepsCountByCropId(crop.getCrop_ID()).observe((AppCompatActivity) context, completedSteps -> {
//                    int progress = (totalSteps > 0) ? (completedSteps * 100 / totalSteps) : 0;
//                    progressBar.setProgress(progress);
//
////                    if (totalSteps == completedSteps) {
////                        crop.setCompleted(true);
////
////
////                    }
//                });
            });


        }
    }
}

