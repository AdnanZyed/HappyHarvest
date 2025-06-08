package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_New_Crop extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_A = 1;
    private static final int REQUEST_CODE_IMAGE_C = 2;
    private EditText etCropName, etDescription, etPrice, etExpertUsername, etImageUrl, et_image_urlC;
    private Spinner spCategory;
    private ImageView ivCropImage, iv_crop_imageC;
    private Button btnSelectImage, btn_select_imageC, btnSaveCrop, btnLoadImageFromUrl, btn_load_image_from_urlC;
    private byte[] cropImageBytes;
    private byte[] cropImageBytesC;
    private My_View_Model myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_crop);

        etCropName = findViewById(R.id.et_Crop_nameA);
        etDescription = findViewById(R.id.et_Crop_descriptionA);
        etPrice = findViewById(R.id.et_priceA);
        etExpertUsername = findViewById(R.id.et_expert_usernameA);
        spCategory = findViewById(R.id.sp_categoryA);
        btnSaveCrop = findViewById(R.id.btn_save_CropA);
        btnLoadImageFromUrl = findViewById(R.id.btn_load_image_from_urlA);
        etImageUrl = findViewById(R.id.et_image_urlA);
        ivCropImage = findViewById(R.id.iv_Crop_imageA);
        btnSelectImage = findViewById(R.id.btn_select_imageA);
        btn_load_image_from_urlC = findViewById(R.id.btn_load_image_from_urlC);
        et_image_urlC = findViewById(R.id.et_image_urlC);
        iv_crop_imageC = findViewById(R.id.iv_Crop_imageC);
        btn_select_imageC = findViewById(R.id.btn_select_imageC);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        btnSelectImage.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_A));
        btn_select_imageC.setOnClickListener(v -> selectImage(REQUEST_CODE_IMAGE_C));
        btnLoadImageFromUrl.setOnClickListener(v -> loadImageFromUrl(etImageUrl, ivCropImage, true));
        btn_load_image_from_urlC.setOnClickListener(v -> loadImageFromUrl(et_image_urlC, iv_crop_imageC, false));
        btnSaveCrop.setOnClickListener(v -> saveCrop());
    }

    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void loadImageFromUrl(EditText urlField, ImageView imageView, boolean isMainImage) {
        String imageUrl = urlField.getText().toString().trim();
        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "Please enter the image link", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Add_New_Crop.this, "Image failed to load", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
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

    private void saveCrop() {
        String cropName = etCropName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        int price = Integer.parseInt(etPrice.getText().toString().trim());
        String category = spCategory.getSelectedItem().toString();
        String expertUsername = etExpertUsername.getText().toString().trim();

        if (cropName.isEmpty() || description.isEmpty() || expertUsername.isEmpty() || cropImageBytes == null) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

    //    Crop z = new Crop(0, cropName, cropImageBytes, price, category, description, null, cropImageBytesC, false, false, false, expertUsername, null, 0, 0);

       // myViewModel.insertCrop(z);


        Toast.makeText(this, "The crop has been saved successfully", Toast.LENGTH_SHORT).show();
        myViewModel.addNotification("Today's Special Offers", "You get a special promo today!", R.drawable.offered);
        myViewModel.addNotification("New Category Crops!", "Now the 3D design crop is available", R.drawable.offered1);


        finish();
    }
}
