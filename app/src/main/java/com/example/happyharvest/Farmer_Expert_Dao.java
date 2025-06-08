package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Farmer_Expert_Dao {

    @Insert
    void insertFarmerExpert(Farmer_Expert farmerExpert);

    @Delete
    void deleteFarmerExpert(Farmer_Expert farmerExpert);

    @Query("SELECT * FROM Farmer_Expert WHERE farmerUserName = :farmerUsername")
    LiveData<List<Farmer_Expert>> getExpertsByFarmer(String farmerUsername);

    @Query("SELECT * FROM Farmer_Expert WHERE expertUserName = :expertUsername")
    LiveData<List<Farmer_Expert>> getFarmersByExpert(String expertUsername);
}


