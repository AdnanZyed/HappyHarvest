package com.example.happyharvest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    List<Step> steps;

    public StepAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.icon.setImageResource(step.iconResId);
        holder.title.setText(step.title);

        if (step.isCurrent) {
            holder.icon.setBackgroundResource(R.drawable.circle_bg); // زرقاء
        } else {
            holder.icon.setBackgroundResource(R.drawable.unnamed); // رمادية مثلا
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        StepViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.step_icon);
            title = itemView.findViewById(R.id.step_title);
        }
    }
}
