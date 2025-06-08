package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateCrop extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_A = 1;
    private static final int REQUEST_CODE_IMAGE_C = 2;

    private EditText etCropName, etDescription, etPrice, etExpertUsername, etImageUrl, et_image_urlC;
    private Spinner spCategory;
    private ImageView ivCropImage, iv_crop_imageC;
    private Button btnSelectImage, btn_select_imageC, btnSaveCrop, btnLoadImageFromUrl, btn_load_image_from_urlC;
    private byte[] cropImageBytes;
    private String NAME;
    private byte[] Image;
    private TextView cropId1;
    private String Price;
    private String Categorie;

    private String Description;
    private String PreferredHumidity;
    private String AllowedHumidity;
    private String ForbiddenHumidity;
    private String ForbiddenIrrigation;
    private String AllowedIrrigation;
    private String PreferredIrrigation;
    private String PreferredSoil;
    private String ForbiddenSoil;
    private String AllowedSoil;
    private String ForbiddenTemp;
    private String AllowedTemp;
    private String PreferredTemp;
    private byte[] Picture;
    private String USER_Name;
    private byte[] cropImageBytesC;
    private My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_crop);

        etCropName = findViewById(R.id.et_Crop_nameA1);
        etDescription = findViewById(R.id.et_Crop_descriptionA1);
        etPrice = findViewById(R.id.et_priceA1);
        cropId1 = findViewById(R.id.Crop_id);
        etExpertUsername = findViewById(R.id.et_expert_usernameA1);
        spCategory = findViewById(R.id.sp_categoryA1);
        btnSaveCrop = findViewById(R.id.btn_save_CropA1);

        btnLoadImageFromUrl = findViewById(R.id.btn_load_image_from_urlA1);
        etImageUrl = findViewById(R.id.et_image_urlA1);
        ivCropImage = findViewById(R.id.iv_Crop_imageA1);
        btnSelectImage = findViewById(R.id.btn_select_imageA1);

        btn_load_image_from_urlC = findViewById(R.id.btn_load_image_from_urlC1);
        et_image_urlC = findViewById(R.id.et_image_urlC1);
        iv_crop_imageC = findViewById(R.id.iv_Crop_imageC1);
        btn_select_imageC = findViewById(R.id.btn_select_imageC1);

        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        Intent intent = getIntent();
        cropId1.setText(intent.getIntExtra("ID", -1) + "");
        etPrice.setText(intent.getIntExtra("Price", -1) + "");
       // spCategory.setTextAlignment(Integer.parseInt(intent.getStringExtra("Categorie")));


        etCropName.setText(intent.getStringExtra("NAME"));
        etExpertUsername.setText(intent.getStringExtra("USER_Name"));
        etDescription.setText(intent.getStringExtra("Description"));


        iv_crop_imageC.setImageResource(R.drawable.unnamed);


        byte[] byteArray1 =  intent.getByteArrayExtra("Image");


        if (byteArray1 != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray1, 0, byteArray1.length);

            ivCropImage.setImageBitmap(bitmap);
        }




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
                        Toast.makeText(UpdateCrop.this, "Image failed to load", Toast.LENGTH_SHORT).show();
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

        String crop_Id = cropId1.getText().toString().trim();
        myViewModel.getAllCropsById(Integer.parseInt(crop_Id)).observe(UpdateCrop.this, crops -> {


            NAME = crops.get(0).getCrop_NAME();
           // Image = crops.get(0).getImage();
       //   Price = crops.get(0).getPrice() + "";
            Categorie = crops.get(0).getCategorie();

            PreferredHumidity = crops.get(0).getPreferredHumidity();
            AllowedHumidity = crops.get(0).getAllowedHumidity();
            ForbiddenHumidity = crops.get(0).getForbiddenHumidity();

            ForbiddenIrrigation = crops.get(0).getForbiddenIrrigation();
            AllowedIrrigation = crops.get(0).getAllowedIrrigation();
            PreferredIrrigation = crops.get(0).getPreferredIrrigation();

            PreferredSoil = crops.get(0).getPreferredSoil();
            AllowedSoil = crops.get(0).getAllowedSoil();
            ForbiddenSoil = crops.get(0).getForbiddenSoil();

            ForbiddenTemp = crops.get(0).getForbiddenTemp();
            AllowedTemp = crops.get(0).getAllowedTemp();
            PreferredTemp = crops.get(0).getPreferredTemp();


            Categorie = crops.get(0).getCategorie();
            Description = crops.get(0).getDescription();
           // Picture = crops.get(0).getProfilePicture();
            USER_Name = crops.get(0).getExpert_USER_Name();


        });


        String cropName = etCropName.getText().toString().trim();

        if (cropImageBytes == null) {

            cropImageBytes = Image;
        }
        if (cropImageBytesC == null) {

            cropImageBytesC = Picture;
        }
        if (cropName.isEmpty()) {
            cropName = NAME;

        }
        String description = etDescription.getText().toString().trim();

        if (description.isEmpty()) {
            description = Description;

        }
        String price = etPrice.getText().toString().trim();
        if (price.isEmpty()) {
            price = Price;

        }

        String category = spCategory.getSelectedItem().toString();
        if (category.isEmpty()) {
            category = Categorie;

        }
        String expertUsername = etExpertUsername.getText().toString().trim();
        if (expertUsername.isEmpty()) {
            expertUsername = USER_Name;

        }
        if (crop_Id.isEmpty()) {
            Toast.makeText(this, "Please enter your crop ID ", Toast.LENGTH_SHORT).show();
            return;
        }

        Crop crop = new Crop();
        crop.setCrop_ID(Integer.parseInt(crop_Id));
        crop.setCrop_NAME(cropName);
        crop.setCategorie(category);
        crop.setDescription(description);
        crop.setExpert_USER_Name(expertUsername);


        myViewModel.updateCrop(crop);
        Toast.makeText(this, "The crop has been saved successfully", Toast.LENGTH_SHORT).show();
        myViewModel.addNotification("Today's Special Offers", "You get a special promo today!", R.drawable.offered);
        myViewModel.addNotification("New Category Crops!", "Now the 3D design crop is available", R.drawable.offered1);

        finish();
    }
}
