package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.happyharvest.GreenhouseEvaluation;

import java.util.List;

@Dao
public interface GreenhouseEvaluationDao {
    @Insert
    void insert(GreenhouseEvaluation evaluation);

    @Update
    void update(GreenhouseEvaluation evaluation);

    @Delete
    void delete(GreenhouseEvaluation evaluation);

    @Query("SELECT * FROM greenhouse_evaluations WHERE farmerUserName = :userName")
    LiveData<List<GreenhouseEvaluation>> getEvaluationsByUser(String userName);
}