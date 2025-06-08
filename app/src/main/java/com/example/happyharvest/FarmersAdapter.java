package com.example.happyharvest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class FarmersAdapter extends RecyclerView.Adapter<FarmersAdapter.FarmerViewHolder> {

    private final List<Farmer> farmerList;
    private final Context context;

    public FarmersAdapter(List<Farmer> farmerList, Context context) {
        this.farmerList = farmerList;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_farmer, parent, false);
        return new FarmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerViewHolder holder, int position) {
        Farmer farmer = farmerList.get(position);
        holder.bind(farmer);
    }

    @Override
    public int getItemCount() {
        return farmerList != null ? farmerList.size() : 0;
    }


    class FarmerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView farmerImage;
        private final TextView farmerName;
        private final TextView lastMessage;

        public FarmerViewHolder(@NonNull View itemView) {
            super(itemView);
            farmerImage = itemView.findViewById(R.id.ivFarmerImageS);
            farmerName = itemView.findViewById(R.id.tvFarmerNameS);
            lastMessage = itemView.findViewById(R.id.tvLastMessageS);


        }

        public void bind(Farmer farmer) {
            Bitmap bitmap = ImageConverter.byteArrayToBitmap(farmer.getS_Image());
            if (bitmap != null) {
                farmerImage.setImageBitmap(bitmap);
            } else {
                farmerImage.setImageResource(R.drawable.profile);
            }

            farmerName.setText(farmer.getS_name());
            lastMessage.setText(farmer.getBio());
        }

    }
}
