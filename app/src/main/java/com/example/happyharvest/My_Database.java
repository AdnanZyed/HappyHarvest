package com.example.happyharvest;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Farmer.class, Crop.class, Expert.class, Farmer_Crops.class,
        Farmer_Expert.class, Crop_Reviews.class, CropStep.class, Message.class,
        Expert_Reviews.class, FarmerStep.class, Notification.class,CropProblem.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class My_Database extends RoomDatabase {
    public abstract FarmerStepDao farmerStepDao();

    public abstract NotificationDao notificationDao();

    public abstract Crop_Dao cropDao();

    public abstract MessageDao messageDao();

    public abstract Farmer_Dao farmerDao();

    public abstract Expert_Dao expertDao();

    public abstract CropStepsDao cropStepsDao();


    public abstract Farmer_Crop_Dao farmerCropDao();

    public abstract Farmer_Expert_Dao farmerExpertDao();

    public abstract CropReviewsDao cropReviewsDao();

    public abstract ExpertReviewsDao expertReviewsDao();
    public abstract CropProblem_Dao cropProblemDao();


    private static volatile My_Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);


    static My_Database getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (My_Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    My_Database.class, "My_Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
