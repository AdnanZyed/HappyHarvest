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

public class ExpertAdapterMentors extends RecyclerView.Adapter<ExpertAdapterMentors.ExpertViewHolder> {

    private Context context;
    private String user;
    private List<Expert> expertsMonetorsList;

    public ExpertAdapterMentors(Context context, List<Expert> expertList, String user) {
        this.context = context;
        this.user = user;
        this.expertsMonetorsList = expertList;
    }

    public void setExpert_MonetorsList(List<Expert> experts) {
        this.expertsMonetorsList = experts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mentores_expert_item, parent, false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertViewHolder holder, int position) {
        Expert expert = expertsMonetorsList.get(position);
        holder.tvExpertNameAll.setText(expert.getExpert_name());
        holder.tvExpertEducationAll.setText(expert.getEducation());
        holder.chatAll.setImageResource(R.drawable.chat);

        if (expert.getImage_expert() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(expert.getImage_expert(), 0, expert.getImage_expert().length);
            holder.imgExpertAll.setImageBitmap(bitmap);
        } else {
            holder.imgExpertAll.setImageResource(R.drawable.unnamed);
        }
    }

    @Override
    public int getItemCount() {
        return expertsMonetorsList.size();
    }

    class ExpertViewHolder extends RecyclerView.ViewHolder {

        ImageView imgExpertAll, chatAll;
        TextView tvExpertNameAll, tvExpertEducationAll;

        public ExpertViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Expert selectedExpert = expertsMonetorsList.get(position);
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
            imgExpertAll = itemView.findViewById(R.id.iv_expert_image_all);
            tvExpertNameAll = itemView.findViewById(R.id.tv_expert_name_all);
            tvExpertEducationAll = itemView.findViewById(R.id.tv_major_all);
            chatAll = itemView.findViewById(R.id.chat_all);
        }
    }
}
