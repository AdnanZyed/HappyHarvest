package com.example.happyharvest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.Locale;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {
    private Context context;
    private String user;
    private List<Crop> cropList;
    private final My_View_Model viewModel;
    private OnCropClickListener listener;

    public void setOnCropClickListener(OnCropClickListener listener) {
        this.listener = listener;
    }

    public CropAdapter(Context context, List<Crop> cropList, String user) {
        this.context = context;
        this.cropList = cropList;
        this.user = user;
        this.viewModel = new ViewModelProvider((AppCompatActivity) context).get(My_View_Model.class);
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crops, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop = cropList.get(position);
        holder.tvCropName.setText(crop.getCrop_NAME());
        holder.tvCategorie.setText(crop.getCategorie());
        holder.tvCropName.setText(crop.getCrop_NAME());
        holder.tvCategorie.setText(crop.getCategorie());

        if (crop.getCompatibilityScore() > 0) {
            holder.tvCompatibility.setText(String.format(Locale.getDefault(),
                    "نسبة التوافق: %.0f%%", crop.getCompatibilityScore()));
            holder.tvCompatibility.setVisibility(View.VISIBLE);
        } else {
            holder.tvCompatibility.setVisibility(View.GONE);
        }
        //   holder.tvPrice.setText(String.format("$%d", crop.getPrice()));
//        في onBindViewHolder في CropAdapter:
//        Glide.with(context)
//                .load(crop.getImageUrl())
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error_image)
//                .into(holder.imageView);
//        byte[] imageBytes = crop.getImage();
//        if (imageBytes != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            holder.ivCropImage.setImageBitmap(bitmap);
//        } else {
//            holder.ivCropImage.setImageResource(R.drawable.sample_course_image);
//        }

//        viewModel.getBookmarkedCropsByFarmer1(user, crop.getCrop_ID()).observe((AppCompatActivity) context, farmerCrops -> {
//
//
//            if (farmerCrops.isEmpty() || farmerCrops == null) {
//                holder.BookmarkIcon.setImageResource(R.drawable.bookmark);
//            } else {
//                holder.BookmarkIcon.setImageResource(R.drawable.bookmarked);
//            }
//        });


//        holder.BookmarkIcon.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                //  showCustomDialog(crop, crop.getImage(), crop.getCrop_NAME(), crop.getCategorie(), crop.getPrice());
//
//
//                return true;
//            }
//        });
//        holder.BookmarkIcon.setOnClickListener(v -> {
//            viewModel.isFarmerCropExistsC(user, crop.getCrop_ID(), true).observe((AppCompatActivity) context, isHadc -> {
//                viewModel.isFarmerCropExistsB(user, crop.getCrop_ID(), true).observe((AppCompatActivity) context, isHadb -> {
//                    viewModel.isFarmerCropExists(user, crop.getCrop_ID(), true).observe((AppCompatActivity) context, isHad -> {
//                        if (!isHadb && !isHad && !isHadc) {
//                            String soilType, String irrigationType, String waterAvailability, double moistureLevel, String season, String location, float averageTemperature,
//
//                            Farmer_Crops farmerCrop = new Farmer_Crops(user, crop.getCrop_ID(), true, false,"",0,false,"","","" ,0,"","",0,false, 0);
//                            viewModel.insertFarmerCrop(farmerCrop);
//                            holder.BookmarkIcon.setImageResource(R.drawable.bookmarked);
//
//
//                        } else if (isHad || isHadc) {
//                            Farmer_Crops farmerCrop = new Farmer_Crops(user, crop.getCrop_ID(), true, isHad,"",0,false, isHadc, 0);
//
//                            viewModel.updateCropFarmer(farmerCrop);
//
//                            holder.BookmarkIcon.setImageResource(R.drawable.bookmarked);
//
//
//                        } else if (isHadb) {
//                            Farmer_Crops farmerCrop = new Farmer_Crops(user, crop.getCrop_ID(), false, isHad,"",0,false, isHadc, 0);
//
//                            viewModel.updateCropFarmer(farmerCrop);
//
//                            holder.BookmarkIcon.setImageResource(R.drawable.bookmark);
//
//
//                        }
//
//
//                    });
//                });
//            });
//        });


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCropClick(crop);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public Crop getCropAt(int position) {
        return cropList.get(position);
    }

    public void setCropList(List<Crop> cropList) {
        this.cropList = cropList;
        notifyDataSetChanged();
    }

    public interface OnCropClickListener {
        void onCropClick(Crop crop);
    }

    static class CropViewHolder extends RecyclerView.ViewHolder {
        TextView tvCropName, tvCategorie, tvCompatibility;
        ImageView ivCropImage, BookmarkIcon;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCropName = itemView.findViewById(R.id.tv_crop_name);
            tvCategorie = itemView.findViewById(R.id.tv_categorie);
            tvCompatibility = itemView.findViewById(R.id.tv_compatibility); // أضف هذا الـ TextView في التخطيط
            ivCropImage = itemView.findViewById(R.id.iv_crop_image);
            //   BookmarkIcon = itemView.findViewById(R.id.bookmarkIcon);
        }
    }

    private void showCustomDialog(Crop crop, byte[] image, String name, String catigories, int prise) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);


        TextView mainText = dialog.findViewById(R.id.dialog_main_text1);
        Button viewCropButton = dialog.findViewById(R.id.btn_view_Crop1);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel1);
        ImageView iv_crop_image = dialog.findViewById(R.id.iv_Crop_imageD);
        TextView tv_categorie = dialog.findViewById(R.id.tv_categorieD);
        TextView tv_crop_name = dialog.findViewById(R.id.tv_Crop_nameD);
        TextView tv_price = dialog.findViewById(R.id.tv_priceD);

        byte[] imageBytes = image;
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            iv_crop_image.setImageBitmap(bitmap);
            tv_categorie.setText(catigories);
            tv_crop_name.setText(name);
            tv_price.setText(prise + "");
            mainText.setText("Remove from Bookmark?");


            viewCropButton.setOnClickListener(v -> {

                viewModel.deleteFarmerCropByUserAndCrop(user, crop.getCrop_ID());

                dialog.dismiss();
            });

            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.BOTTOM;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);

            }


            cancelButton.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
        }

    }


}