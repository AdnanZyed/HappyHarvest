package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CropProblem_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCropProblem(CropProblem problem);

    @Update
    void updateCropProblem(CropProblem problem);

    @Delete
    void deleteCropProblem(CropProblem problem);

    @Query("SELECT * FROM CropProblem WHERE cropId = :cropId")
    LiveData<List<CropProblem>> getProblemsByCropId(int cropId);

    @Query("SELECT * FROM CropProblem WHERE problemId = :problemId LIMIT 1")
    LiveData<CropProblem> getProblemById(int problemId);

    @Query("SELECT * FROM CropProblem")
    LiveData<List<CropProblem>> getAllProblems();
}