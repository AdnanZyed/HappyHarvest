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
public interface Farmer_Crop_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll1(List<Farmer_Crops> farmerCrops);
    @Query("SELECT COUNT(*) FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId  AND  isRegister = :isRegister")
    int isFarmerCropExists(String farmerUsername, int cropId, boolean isRegister);

    @Query("SELECT COUNT(*) FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId  AND  isAddCart = :isAddCart")
    int isFarmerCropExists1(String farmerUsername, int cropId, boolean isAddCart);

    @Query("SELECT COUNT(*) FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId  AND  isBookmark = :isBookmark")
    int isFarmerCropExistsB(String farmerUsername, int cropId, boolean isBookmark);

    @Update
    void updateCropFarmer(Farmer_Crops farmerCrop);

    @Insert
    void insertFarmerCrop(Farmer_Crops farmerCrop);

    @Delete
    void deleteFarmerCrop(Farmer_Crops farmerCrop);

    @Query("UPDATE Farmer_Crops SET isBookmark = 0 WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId AND isBookmark= 1")
    void deleteFarmerCropByUserAndCrop(String farmerUsername, int cropId);

    @Query("DELETE FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId AND isBookmark= 1")
    void delete1FarmerCropByUserAndCrop(String farmerUsername, int cropId);
//    @Query("DELETE FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId ")
//    Farmer_Crops getFarmerCrop(String farmerUsername, int cropId);
    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerName AND Crop_ID = :cropId")
    LiveData<List<Farmer_Crops>> getFarmerCrop(String farmerName, int cropId);

    @Update
    void updateFarmerCrop(Farmer_Crops farmerCrops);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateFarmerCrop(Farmer_Crops farmerCrops);

//    @Query("UPDATE Crop  SET isBookmarked = :isBookmarked WHERE Crop_ID = :cropId")
//    void updateBookmarkStatus(int cropId, boolean isBookmarked);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername")
    LiveData<List<Farmer_Crops>> getCropsByFarmer(String farmerUsername);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND isBookmark = 1")
    LiveData<List<Farmer_Crops>> getBookmarkedCropsByFarmer(String farmerUsername);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND isAddCart =1")
    LiveData<List<Farmer_Crops>> getisAddCartCropsByFarmer(String farmerUsername);


    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId AND isBookmark = 1 ")
    LiveData<List<Farmer_Crops>> getBookmarkedCropsByFarmer1(String farmerUsername, int cropId);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId AND isAddCart= 1")
    LiveData<List<Farmer_Crops>> getisAddCartCropsByFarmer1(String farmerUsername, int cropId);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername AND Crop_ID = :cropId AND rating!=0 OR rating!=NULL")
    LiveData<List<Farmer_Crops>> getisRatingCropsByFarmer1(String farmerUsername, int cropId);

    @Query("SELECT * FROM Farmer_Crops WHERE Farmer_user_name = :farmerUsername ")
    LiveData<List<Farmer_Crops>> getisRegisterCropsByFarmer1(String farmerUsername);

    @Query("SELECT * FROM Farmer_Crops WHERE Crop_ID = :cropId")
    LiveData<List<Farmer_Crops>> getFarmersByCrop(int cropId);

    @Query("SELECT * FROM Farmer_Crops WHERE Crop_ID = :cropId AND Farmer_user_name= :farmerCrop")
    LiveData<List<Farmer_Crops>> getFarmersByCropAndFarmer(String farmerCrop, int cropId);
}
