package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class UpdateCrop1 extends AppCompatActivity {
    RecyclerView recyclerView;
    CropAdapter cropAdapter;
    My_View_Model myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_crop1);
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

        recyclerView = findViewById(R.id.rv_Crops_update);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cropAdapter = new CropAdapter(UpdateCrop1.this, new ArrayList<>(), "");
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


            @SuppressLint("ResourceAsColor")
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                int recyclerViewWidth = recyclerView.getWidth();


                viewHolder.itemView.setTranslationX(recyclerViewWidth / 4);

                View itemView = viewHolder.itemView;
                Paint paint = new Paint();
                paint.setColor(0xFF25750C);


                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);
                Bitmap trashIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img_19);

                if (dX > 0) {
                    float iconMargin = (itemView.getHeight() - trashIcon.getHeight()) / 2;
                    float iconLeft = itemView.getLeft() + 100;
                    float iconTop = itemView.getTop() + iconMargin;

                    c.drawBitmap(trashIcon, iconLeft, iconTop, null);

                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                                if (event.getX() > iconLeft && event.getX() < iconLeft + trashIcon.getWidth() &&
                                        event.getY() > iconTop && event.getY() < iconTop + trashIcon.getHeight()) {

                                    Intent intent=new Intent(UpdateCrop1.this,UpdateCrop.class);
                                    int position = viewHolder.getAdapterPosition();
                                    Crop crop = cropAdapter.getCropAt(position);
                                    intent.putExtra("ID",crop.getCrop_ID());
                                 //   intent.putExtra("Picture",crop.getProfilePicture());
                                    intent.putExtra("Categorie",crop.getCategorie());
                                    intent.putExtra("Description",crop.getDescription());
                              //    intent.putExtra("Price",crop.getPrice());
                                    intent.putExtra("NAME",crop.getCrop_NAME());
                                    intent.putExtra("USER_Name",crop.getExpert_USER_Name());
                                    //intent.putExtra("Image",crop.getImage());
                                    startActivity(intent);

                                }
                            }
                            return false;
                        }
                    });

                }
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }




}