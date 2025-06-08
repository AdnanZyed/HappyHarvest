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

public interface Expert_Dao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertExpert(Expert expert);

    @Update
    void updateExpert(Expert expert);

    @Delete
    void deleteExpert(Expert expert);

    @Query("SELECT * FROM Expert WHERE Expert_name LIKE :expertName")
    LiveData<List<Expert>> getExpertByName(String expertName);

    @Query("SELECT * FROM Expert")
    LiveData<List<Expert>> getAllExperts();

    @Query("SELECT * FROM Expert WHERE Expert_USER_Name=:expert_user_name")
    LiveData<List<Expert>> getAllExpertsByUser(String expert_user_name);


}
