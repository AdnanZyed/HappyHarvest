package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UpdateCrop extends AppCompatActivity {
    RecyclerView recyclerView;
    CropAdapter cropAdapter;
    My_View_Model myViewModel;
    FloatingActionButton fab_add_crop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update1);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);
        recyclerView = findViewById(R.id.rv_Crops_update);
        fab_add_crop = findViewById(R.id.fab_add1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab_add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateCrop.this, Add_New_Crop.class);
                startActivity(intent);
            }
        });
        cropAdapter = new CropAdapter(UpdateCrop.this, new ArrayList<>(), "");
        recyclerView.setAdapter(cropAdapter);
        myViewModel.getAllCrop().observe(this, new Observer<List<Crop>>() {
            @Override
            public void onChanged(List<Crop> crops) {
                cropAdapter.setCropList(crops);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            }


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                Paint paint = new Paint();
                paint.setAntiAlias(true); // مهم لتفعيل الزوايا المدورة

                if (dX > 0) {  // السحب لليمين فقط
                    float cornerRadius = 16f; // نصف قطر الزوايا المدورة
                    float editWidth = dX / 2;  // النصف الأول للتعديل
                    float deleteStartX = itemView.getLeft() + dX / 4; // بداية الحذف
                    float deleteEndX = itemView.getLeft() + dX / 2;       // نهاية الحذف

                    // 1. منطقة التعديل (أخضر مع زوايا مدورة)
                    paint.setColor(0xFF25750C); // أخضر للتعديل
                    c.drawRoundRect(
                            (float) itemView.getLeft(),
                            (float) itemView.getTop(),
                            deleteStartX,
                            (float) itemView.getBottom(),
                            cornerRadius,
                            cornerRadius,
                            paint
                    );

                    // 2. منطقة الحذف (أحمر مع زوايا مدورة)
                    paint.setColor(0xFFD32F2F); // أحمر للحذف
                    c.drawRoundRect(
                            deleteStartX,
                            (float) itemView.getTop(),
                            deleteEndX,
                            (float) itemView.getBottom(),
                            cornerRadius,
                            cornerRadius,
                            paint
                    );

                    // 3. الأيقونات (بنفس الموقع الأصلي)
                    Bitmap editIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pencil);
                    float iconMargin = (itemView.getHeight() - editIcon.getHeight()) / 2;
                    float iconLeftEdit = itemView.getLeft() + 100;
                    float iconTopEdit = itemView.getTop() + iconMargin;
                    c.drawBitmap(editIcon, iconLeftEdit, iconTopEdit, null);

                    Bitmap deleteIcon = BitmapFactory.decodeResource(getResources(), R.drawable.trash);
                    float iconLeftDelete = deleteStartX + 100;
                    float iconTopDelete = itemView.getTop() + (itemView.getHeight() - deleteIcon.getHeight()) / 2;
                    c.drawBitmap(deleteIcon, iconLeftDelete, iconTopDelete, null);

                    // 4. معالجة اللمس (بدون تغيير)
                    recyclerView.setOnTouchListener((v, event) -> {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            float touchX = event.getX();
                            float touchY = event.getY();

                            if (touchY > itemView.getTop() && touchY < itemView.getBottom()) {
                                int position = viewHolder.getAdapterPosition();
                                Crop crop = cropAdapter.getCropAt(position);

                                if (touchX > itemView.getLeft() && touchX < deleteStartX) {
                                    Intent intent = new Intent(UpdateCrop.this, Edit_Crop_Activity.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    startActivity(intent);
                                } else if (touchX > deleteStartX && touchX < deleteEndX) {
                                    showCustomDialog(viewHolder);
                                }
                            }
                        }
                        return false;
                    });
                }
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }


    private void showCustomDialog(RecyclerView.ViewHolder viewHolder) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setCancelable(true);


        Button viewCropButton = dialog.findViewById(R.id.btn_view_Crop2);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel2);


        viewCropButton.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            Crop crop = cropAdapter.getCropAt(position);
            myViewModel.deleteCrop(crop);

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);

        }
        dialog.show();
    }

}