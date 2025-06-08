package com.example.happyharvest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ChatViewHolder> {

    private final Context context;
    private final String user;
    private List<Farmer> farmers = new ArrayList<>();

    public CallAdapter(Context context, String user) {
        this.context = context;
        this.user = user;
    }

    public void setFarmers(List<Farmer> farmers) {
        this.farmers = farmers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_call_farmer, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Farmer farmer = farmers.get(position);

        holder.farmerName.setText(farmer.getS_name());
        if (farmer.getS_Image() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(farmer.getS_Image(), 0, farmer.getS_Image().length);
            holder.farmerImage.setImageBitmap(bitmap);
        } else {

            holder.farmerImage.setImageResource(R.drawable.profile);


        }
        holder.callImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, CallActivity.class);
            intent.putExtra("farmerUsername", farmer.getFarmer_user_name());
            intent.putExtra("farmerName", farmer.getS_name());
            intent.putExtra("USER", user);
            intent.putExtra("farmerImage", farmer.getS_Image());

            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return farmers.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView farmerImage, callImage;
        TextView farmerName, lastMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            farmerImage = itemView.findViewById(R.id.ivFarmerImage1);
            farmerName = itemView.findViewById(R.id.tvFarmerNameItemC1);
            lastMessage = itemView.findViewById(R.id.tvLastMessage1);
            callImage = itemView.findViewById(R.id.callImage);
        }
    }
}
