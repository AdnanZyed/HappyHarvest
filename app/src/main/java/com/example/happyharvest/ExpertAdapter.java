package com.example.happyharvest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder> {
    private Context context;

    private String user;
    private List<Expert> expertsList;

    public void setExpertList(List<Expert> experts) {
        this.expertsList = experts;
        notifyDataSetChanged();
    }

    public ExpertAdapter(Context context, List<Expert> expertsList, String user) {
        this.context = context;
        this.expertsList = expertsList;
        this.user = user;
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expert, parent, false);

        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertViewHolder holder, int position) {
        Expert expert = expertsList.get(position);


        holder.tvExpert_name.setText(expert.getExpert_name().toString());
        byte[] imageBytes = expert.getImage_expert();

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.unnamed);
        }
    }

    @Override
    public int getItemCount() {
        return expertsList != null ? expertsList.size() : 0;
    }

    class ExpertViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpert_name;

        ImageView imageView;

        public ExpertViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpert_name = itemView.findViewById(R.id.tv_expert_name);
            imageView = itemView.findViewById(R.id.iv_expert_image);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Expert selectedExpert = expertsList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("TEACHER_NAME_TEXT_VIEW", selectedExpert.getExpert_name());
                    bundle.putString("TEACHER_USER_NAME_TEXT_VIEW", selectedExpert.getExpert_USER_Name());
                    bundle.putString("EDUCATION_TEXT_VIEW", selectedExpert.getEducation());
                    bundle.putString("STUDENT_USER", user);
                    bundle.putByteArray("BITMAP", selectedExpert.getImage_expert());
                    Intent intent = new Intent(context, ExpertProfileActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }
    }
}
