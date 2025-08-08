package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImagesFragmentEdit extends Fragment {
    private static final int REQUEST_CODE_IMAGE_A = 1;
    private static final int REQUEST_CODE_IMAGE_C = 2;
    private EditText etImageUrl, et_image_urlC;
    private ImageView ivCropImage, iv_crop_imageC;
    private Button btnSelectImage, btn_select_imageC, btnLoadImageFromUrl, btn_load_image_from_urlC;
    private byte[] cropImageBytes;
    private byte[] cropImageBytesC;
    private int id;
    private My_View_Model myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        id = getArguments().getInt("ID");

        etImageUrl = view.findViewById(R.id.et_image_urlA);
        et_image_urlC = view.findViewById(R.id.et_image_urlC);
        ivCropImage = view.findViewById(R.id.iv_Crop_imageA);
        iv_crop_imageC = view.findViewById(R.id.iv_Crop_imageC);
        btnSelectImage = view.findViewById(R.id.btn_select_imageA);
        btn_select_imageC = view.findViewById(R.id.btn_select_imageC);
        btnLoadImageFromUrl = view.findViewById(R.id.btn_load_image_from_urlA);
        btn_load_image_from_urlC = view.findViewById(R.id.btn_load_image_from_urlC);

        btnSelectImage.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_A));
        btn_select_imageC.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_C));
        btnLoadImageFromUrl.setOnClickListener(v -> loadImageFromUrl(etImageUrl, ivCropImage, true));
        btn_load_image_from_urlC.setOnClickListener(v -> loadImageFromUrl(et_image_urlC, iv_crop_imageC, false));

        btnLoadImageFromUrl.setBackgroundResource(R.drawable.update_btn);
        btnSelectImage.setBackgroundResource(R.drawable.update_btn);
        btn_load_image_from_urlC.setBackgroundResource(R.drawable.update_btn);
        btn_select_imageC.setBackgroundResource(R.drawable.update_btn);


    //  loadCurrentImages();
        myViewModel.getAllCropsById(id).observe(requireActivity(), currentCrop -> {

           String name= currentCrop.get(0).getCrop_NAME();
            if (name.equals("Onion")) {
                iv_crop_imageC.setImageResource(R.drawable.onion);
                ivCropImage.setImageResource(R.drawable.onion);
            }else  if (name.equals("Tomato")){
                iv_crop_imageC.setImageResource(R.drawable.tomato);
                ivCropImage.setImageResource(R.drawable.tomato);
            }else  if (name.equals("Eggplant")){
                iv_crop_imageC.setImageResource(R.drawable.eggplant);
                ivCropImage.setImageResource(R.drawable.eggplant);
            }else  if (name.equals("Garlic")){
                iv_crop_imageC.setImageResource(R.drawable.garlic);
                ivCropImage.setImageResource(R.drawable.garlic);
            }else  if (name.equals("Carrot")){
                iv_crop_imageC.setImageResource(R.drawable.carrot);
                ivCropImage.setImageResource(R.drawable.carrot);
            }


        });

        return view;
    }

//    private void loadCurrentImages() {
//        if (getActivity() instanceof Edit_Crop_Activity) {
//            Crop currentCrop = ((Edit_Crop_Activity) getActivity()).getCurrentCrop();
//            if (currentCrop != null) {
//                if (currentCrop.getImageUrl() != null && !currentCrop.getImageUrl().isEmpty()) {
//                    etImageUrl.setText(currentCrop.getImageUrl());
//                    loadImageFromUrl(etImageUrl, ivCropImage, true);
//                }
//
//                if (currentCrop.getImageUrlC() != null && !currentCrop.getImageUrlC().isEmpty()) {
//                    et_image_urlC.setText(currentCrop.getImageUrlC());
//                    loadImageFromUrl(et_image_urlC, iv_crop_imageC, false);
//                }
//            }
//        }
//    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void loadImageFromUrl(EditText urlField, ImageView imageView, boolean isMainImage) {
        String imageUrl = urlField.getText().toString().trim();
        if (imageUrl.isEmpty()) {
            Toast.makeText(getContext(), "Please enter the image link", Toast.LENGTH_SHORT).show();
            return;
        }

        Picasso.get()
                .load(imageUrl)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setDrawingCacheEnabled(true);
                        Bitmap bitmap = imageView.getDrawingCache();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        if (isMainImage) {
                            cropImageBytes = stream.toByteArray();
                        } else {
                            cropImageBytesC = stream.toByteArray();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "Image failed to load", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                if (requestCode == REQUEST_CODE_IMAGE_A) {
                    ivCropImage.setImageURI(imageUri);
                    cropImageBytes = stream.toByteArray();
                } else if (requestCode == REQUEST_CODE_IMAGE_C) {
                    iv_crop_imageC.setImageURI(imageUri);
                    cropImageBytesC = stream.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getCropImageBytes() {
        return cropImageBytes;
    }

    public byte[] getCropImageBytesC() {
        return cropImageBytesC;
    }
}