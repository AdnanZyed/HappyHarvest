package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FarmerStepDao {

    @Insert
    void insertFarmerStep(FarmerStep farmerStep);

    @Query("SELECT * FROM FarmerStep WHERE farmerUserName = :farmerUserName AND completed = 1 ")
    LiveData<List<FarmerStep>> getCompletedStepsForFarmer(String farmerUserName);

    @Query("UPDATE FarmerStep SET completed = :completed WHERE farmerUserName = :farmerUserName AND stepId = :stepId")
    void updateCompletionStatus(String farmerUserName, int stepId, boolean completed);

    @Query("DELETE FROM FarmerStep WHERE farmerUserName = :farmerUserName AND stepId = :stepId")
    void deleteFarmerStep(String farmerUserName, int stepId);
}
