package com.example.happyharvest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Part_Adapter extends RecyclerView.Adapter<Part_Adapter.PartItemViewHolder> {

    private List<Part_Item> partItems;


    public Part_Adapter(List<Part_Item> partItems) {
        this.partItems = partItems;
    }

    @NonNull
    @Override
    public PartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_item, parent, false);
        return new PartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartItemViewHolder holder, int position) {
        holder.bind(partItems.get(position));
    }

    @Override
    public int getItemCount() {
        return partItems.size();
    }

    static class PartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public PartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }

        public void bind(Part_Item item) {
            imageView.setImageResource(item.getImageResId());
            textView.setText(item.getText());
        }
    }
}

