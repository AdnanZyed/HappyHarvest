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

public interface Farmer_Dao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertFarmer(Farmer farmer);

    @Update
    void updateFarmer(Farmer farmer);

    @Query("SELECT * FROM Farmer WHERE Farmer_user_name != :currentUsername")
    LiveData<List<Farmer>> getAllFarmersExcept(String currentUsername);

    @Delete
    Void deleteFarmer(Farmer farmer);

    @Query("SELECT * FROM Farmer")
    LiveData<List<Farmer>> getAllFarmers();

    @Query("SELECT * FROM Farmer WHERE Farmer_user_name=:farmer_user_name")
    LiveData<List<Farmer>> getAllFarmersByUser(String farmer_user_name);


    @Query("SELECT * FROM Farmer WHERE Farmer_user_name = :username AND Farmer_password = :password")
    LiveData<List<Farmer>> getFarmerByUsernameAndPassword(String username, String password);

}
