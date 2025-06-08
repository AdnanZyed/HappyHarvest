package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CropStepsDao {

    @Insert
    void insert(CropStep cropStep);

    @Update
    void update(CropStep cropStep);

    @Query("SELECT COUNT(*) FROM CropSteps")
    int getTotalStepsCount();

    @Query("SELECT COUNT(*) FROM CropSteps WHERE s_completed = 1")
    int getCompletedStepsCount();

    @Query("SELECT SUM(s_time) FROM CropSteps")
    int getTotalStepsTime();

    @Query("SELECT COUNT(*) FROM CropSteps WHERE Crop_ID = :cropId")
    int getTotalStepsCountByCropId(int cropId);

    @Query("SELECT COUNT(*) FROM CropSteps WHERE Crop_ID = :cropId AND s_completed = 1")
    int getCompletedStepsCountByCropId(int cropId);

    @Query("SELECT SUM(s_time) FROM CropSteps WHERE Crop_ID = :cropId")
    int getTotalStepsTimeByCropId(int cropId);

    @Delete
    void delete(CropStep cropStep);

    @Query("SELECT * FROM CropSteps WHERE Crop_ID = :cropId")
    LiveData<List<CropStep>> getStepsByCropId(int cropId);

    @Query("UPDATE CropSteps SET s_completed = :isCompleted WHERE s_id = :stepId")
    void updateStepCompletionStatus(int stepId, boolean isCompleted);
}
