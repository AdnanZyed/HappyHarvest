package com.example.happyharvest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CropStepsAdapter extends RecyclerView.Adapter<CropStepsAdapter.StepViewHolder> {

    private final My_View_Model myViewModel;
    private final List<CropStep> steps;
    private final String user;
    private final String lock;
    private final Context context;


    public CropStepsAdapter(My_View_Model myViewModel, List<CropStep> steps, String user, Context context, String lock) {
        this.myViewModel = myViewModel;
        this.steps = steps;
        this.lock = lock;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        CropStep step = steps.get(position);

        holder.mainText.setText(step.getS_name());
        holder.secondaryText.setText(step.getS_time() + "");
        holder.numberBadge.setText(String.valueOf(position + 1));
        if (lock == null || lock.isEmpty()) {

            if (step.isS_completed() || position == 0 || steps.get(position - 1).isS_completed()) {


                if (position == getItemCount()) {

                }
                holder.iconPlay.setAlpha(1.0f);
                holder.iconPlay.setEnabled(true);

                holder.iconPlay.setOnClickListener(v -> {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(step.getS_url()));
                    holder.itemView.getContext().startActivity(intent);
                    FarmerStep farmerStep1 = new FarmerStep(user, step.getS_id(), true);
                    myViewModel.insertFarmerStep(farmerStep1);

                    step.setS_completed(true);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {


                        myViewModel.updateCropStep(step);


                        notifyItemChanged(position);
                    }, 2000);
                });
            } else {
                holder.iconPlay.setAlpha(0.5f);
                holder.iconPlay.setEnabled(false);
                holder.iconPlay.setOnClickListener(null);

                step.setS_completed(false);

            }

        } else {

            holder.iconPlay.setAlpha(0.5f);
            holder.iconPlay.setEnabled(false);
            holder.iconPlay.setOnClickListener(null);

            step.setS_completed(false);


        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView mainText, secondaryText, numberBadge;
        ImageView iconPlay;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);

            mainText = itemView.findViewById(R.id.mainText);
            secondaryText = itemView.findViewById(R.id.secondaryText);
            numberBadge = itemView.findViewById(R.id.numberBadge);
            iconPlay = itemView.findViewById(R.id.iconPlay);
        }
    }
}
