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
public interface Crop_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Crop> crops);
    @Query("SELECT * FROM Crop")
    List<Crop> getAllCropSync();
    @Query("SELECT * FROM Crop ")
    LiveData<List<Crop>> getAllCrops();
//    @Query("SELECT * FROM Crop")
//    List<Crop> getAllCrops();
    @Insert(onConflict = OnConflictStrategy.ABORT)
    Void insertCrop(Crop crop);

    @Update
    Void updateCrop(Crop crop);

//    @Query("SELECT * FROM Crop WHERE isBookmarked = 1")
//    LiveData<List<Crop>> getBookmarkedCrops();


//    @Query("UPDATE Crop SET isAddCart = :isAddCart WHERE Crop_ID = :cropId")
//    void updateAddStatus(int cropId, boolean isAddCart);

    @Query("SELECT * FROM Crop WHERE Crop_NAME LIKE :cropName")
    LiveData<List<Crop>> getCropName(String cropName);

//    @Query("UPDATE Crop SET isBookmarked = :isBookmarked WHERE Crop_ID = :cropId")
//    void updateBookmarkStatus(int cropId, boolean isBookmarked);

    @Query("SELECT * FROM Crop WHERE Crop_NAME LIKE :cropName")
    LiveData<List<Crop>> getCropByName(String cropName);

    @Delete
    Void deleteCrop(Crop crop);

    @Query("SELECT * FROM Crop WHERE Categorie LIKE '%' || :categorie || '%'")
    LiveData<List<Crop>> getCropByCategory(String categorie);

    @Query("SELECT * FROM Crop")
    LiveData<List<Crop>> getAllCrop();

    @Query("SELECT * FROM Crop WHERE Crop_ID=:crop_Id")
    LiveData<List<Crop>> getAllCropsById(int crop_Id);
    @Query("SELECT * FROM Crop WHERE crop_ID IN(:cropIds)")
    LiveData<List<Crop>> getMultipleCrops(List<Integer> cropIds);

    @Query("SELECT * FROM Crop WHERE Expert_USER_Name =:expert_USER_Name")
    LiveData<List<Crop>> getAllCropsByExpert_USER_Name(String expert_USER_Name);

    @Query("SELECT * FROM Crop WHERE Crop_ID IN (:CropIds)")
    LiveData<List<Crop>> getCropsByIds(List<Integer> CropIds);


}



