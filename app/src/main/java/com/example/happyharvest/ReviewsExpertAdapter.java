package com.example.happyharvest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class ReviewsExpertAdapter extends RecyclerView.Adapter<ReviewsExpertAdapter.ReviewViewHolder> {

    private final List<Expert_Reviews> reviewsList;
    private final Context context;

    public ReviewsExpertAdapter(Context context, List<Expert_Reviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crops_reviwes, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Expert_Reviews review = reviewsList.get(position);

        if (review.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(review.getImage(), 0, review.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        } else {

            holder.imageView.setImageResource(R.drawable.profile);
        }

        holder.nameTextView.setText(review.getName());
        holder.commentTextView.setText(review.getComment());

        holder.imageView6.setOnClickListener(v -> {
            int currentLove = review.getLove();

            if (review.isChecked()) {
                review.setChecked(false);
                review.setLove(currentLove - 1);

                holder.imageView6.setBackgroundResource(R.drawable.heart);

                notifyItemChanged(position);
            } else {
                review.setChecked(true);
                review.setLove(currentLove + 1);

                holder.imageView6.setBackgroundResource(R.drawable.heart_r);
                notifyItemChanged(position);

            }
            holder.int_Love.setText(String.valueOf(review.getLove()));
            notifyItemChanged(position);

        });
        holder.ratingSpinner.setSelection((int) review.getRating() - 1);
        holder.date.setText(review.getFormattedDate());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView imageView6;
        TextView nameTextView;
        TextView commentTextView;
        TextView date;
        TextView int_Love;
        Spinner ratingSpinner;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_r);
            nameTextView = itemView.findViewById(R.id.name_r);
            commentTextView = itemView.findViewById(R.id.textView4);
            date = itemView.findViewById(R.id.Date);
            ratingSpinner = itemView.findViewById(R.id.rating1);
            int_Love = itemView.findViewById(R.id.int_love);
            imageView6 = itemView.findViewById(R.id.imageView6);
        }
    }
}
