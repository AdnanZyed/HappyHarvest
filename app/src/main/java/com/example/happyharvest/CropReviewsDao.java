package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CropReviewsDao {

    @Insert
    void insertReview(Crop_Reviews review);

    @Query("DELETE FROM Crop_Reviews WHERE s_u_name = :farmerUserName")
    void deleteReviewByFarmer(String farmerUserName);

    @Query("UPDATE Crop_Reviews SET comment = :newComment, rating = :newRating WHERE s_u_name = :farmerUserName")
    void updateReviewByFarmer(String farmerUserName, String newComment, float newRating);

    @Query("SELECT * FROM Crop_Reviews WHERE Crop_ID_R = :cropId")
    LiveData<List<Crop_Reviews>> getAllReviewsByCropId(int cropId);
}