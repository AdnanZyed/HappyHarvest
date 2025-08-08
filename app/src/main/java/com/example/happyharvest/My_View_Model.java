package com.example.happyharvest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class My_View_Model extends AndroidViewModel {
    private LiveData<List<Notification>> allNotifications;
    private final FirebaseDatabaseHelper firebaseHelper;

    private My_Repository repository;

    private LiveData<List<Crop>> allCrops;

    private final LiveData<List<Crop_Reviews>> allReviews;
    private final LiveData<List<Expert_Reviews>> allReviewsT;
    // private final LiveData<List<Crop>> allCrops;


    public My_View_Model(@NonNull Application application) {
        super(application);
        repository = new My_Repository(application);
        allReviews = null;
        allReviewsT = null;
        allNotifications = repository.getAllNotifications();
        //   allCrops = repository.getAllCrops();
        allCrops = repository.getAllCrops();
        firebaseHelper = FirebaseDatabaseHelper.getInstance();


    }
//    public LiveData<List<Crop>> getAllCrops() {
//        return allCrops;
//    }

    public void syncWithFirebase() {
        repository.fetchCropsFromFirebase();
    }

    void insertCrop(Crop crop) {
        repository.insertCrop(crop);


    }

    public void insertAllCrops(List<Crop> crops) {
        repository.insertAll(crops);
    }
    public void insertAllFarmerCrop(List<Farmer_Crops> farmerCrop) {
        repository.insertAll1(farmerCrop);
    }

    //    public LiveData<List<Crop>> getAllCrop() {
//        return allCrops;
//    }
    void updateCrop(Crop crop) {
        repository.updateCrop(crop);


    }

    void deleteCrop(Crop crop) {
        repository.deleteCrop(crop);


    }


    LiveData<List<Crop>> getAllCrop() {
        return repository.getAllCrop();
    }

//    public LiveData<List<Crop>> getBookmarkedCrops() {
//        return repository.getBookmarkedCrops();
//    }

//    public LiveData<List<Crop>> updateBookmarkStatusAndGetCrops(int cropId, boolean isBookmarked) {
//        return repository.updateBookmarkStatusAndGetCrops(cropId, isBookmarked);
//    }
//
//    public LiveData<List<Crop>> updateisAddCartStatusAndGetCrops(int cropId, boolean isAddCart) {
//        return repository.updateisAddCartStatusAndGetCrops(cropId, isAddCart);
//    }


    public LiveData<List<Crop>> getCropsByCategory(String category) {
        return repository.getCropsByCategory(category);
    }

    LiveData<List<Crop>> getAllCropsById(int id) {
        return repository.getAllCropsById(id);
    }

    public LiveData<List<Crop>> getAllCropsByIds(List<Integer> cropIds) {
        return repository.getSesonsByIds(cropIds);
    }

    LiveData<List<Crop>> getAllcropsByExpert_USER_Name(String Expert_USER_Name) {
        return repository.getAllCropsByExpert_USER_Name(Expert_USER_Name);
    }

    public void saveFarmerCrop(Farmer_Crops record) {
        repository.insertFarmerCrop(record);
    }

    // في My_View_Model.java
    public LiveData<List<Crop>> getMultipleCrops(List<Integer> cropIds) {
        return repository.getMultipleCrops(cropIds);
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    public void addNotification(String title, String message, int iconResId) {
        repository.insert(new Notification(title, message, iconResId));
    }

//    public void insertFarmerStep(FarmerStep farmerStep) {
//        repository.insertFarmerStep(farmerStep);
//    }
//
//    public LiveData<List<FarmerStep>> getCompletedStepsForFarmer(String farmerUserName) {
//        return repository.getCompletedStepsForFarmer(farmerUserName);
//    }

//    public void updateCompletionStatus(String farmerUserName, int stepId, boolean completed) {
//        repository.updateCompletionStatus(farmerUserName, stepId, completed);
//    }
//
//    public void deleteFarmerStep(String farmerUserName, int stepId) {
//        repository.deleteFarmerStep(farmerUserName, stepId);
//    }

    public void insertMessage(Message message) {
        repository.insertMessage(message);
    }

    public LiveData<List<Message>> getMessagesBetweenUsers(String currentUser, String otherUser) {
        return repository.getMessagesBetweenUsers(currentUser, otherUser);
    }

    public LiveData<List<Farmer>> getAllFarmersExceptCurrent(String currentUsername) {
        return repository.getAllFarmersExceptCurrent(currentUsername);
    }

    public LiveData<Message> getLastMessageForUser(String username) {
        return repository.getLastMessageForUser(username);
    }
//

    public void insertCropStep(CropStep cropStep) {
        repository.insertCropStep(cropStep);
    }

    public LiveData<Integer> getTotalStepsCount() {
        return repository.getTotalStepsCount();
    }

    public LiveData<Integer> getCompletedStepsCount() {
        return repository.getCompletedStepsCount();
    }

    public LiveData<Integer> getTotalStepsTime() {
        return repository.getTotalStepsTime();
    }

    public void updateCropStep(CropStep cropStep) {
        repository.updateCropStep(cropStep);
    }
    public LiveData<List<Farmer_Crops>> fetchFarmerCrop(String user, int cropId) {
        return repository.getFarmerCrop(user, cropId);
    }
    public void deleteCropStep(CropStep cropStep) {
        repository.deleteCropStep(cropStep);
    }

    public LiveData<Integer> getTotalStepsCountByCropId(int cropId) {
        return repository.getTotalStepsCountByCropId(cropId);
    }

    public LiveData<Integer> getCompletedStepsCountByCropId(int cropId) {
        return repository.getCompletedStepsCountByCropId(cropId);
    }

    public LiveData<Integer> getTotalStepsTimeByCropId(int cropId) {
        return repository.getTotalStepsTimeByCropId(cropId);
    }

    public LiveData<List<CropStep>> getStepsByCropId(int cropId) {
        return repository.getStepsByCropId(cropId);
    }

    public void updateStepCompletionStatus(int stepId, boolean isCompleted) {
        repository.updateStepCompletionStatus(stepId, isCompleted);
    }

    public void insertReview(Crop_Reviews review) {
        repository.insertReview(review);
    }

    public void deleteReviewByFarmer(String farmerUserName) {
        repository.deleteReviewByFarmer(farmerUserName);
    }

    public void updateReviewByFarmer(String farmerUserName, String newComment, float newRating) {
        repository.updateReviewByFarmer(farmerUserName, newComment, newRating);
    }

    public LiveData<List<Crop_Reviews>> getAllReviewsByCropId(int cropId) {
        return repository.getAllReviewsByCropId(cropId);
    }


    public void insertReviewT(Expert_Reviews review) {
        repository.insertReviewT(review);
    }

    public void deleteReviewByFarmerT(String farmerUserName) {
        repository.deleteReviewByFarmerT(farmerUserName);
    }

    public void updateReviewByFarmerT(String farmerUserName, String newComment, float newRating) {
        repository.updateReviewByFarmerT(farmerUserName, newComment, newRating);
    }

    public LiveData<List<Expert_Reviews>> getAllReviewsByCropIdT(String expert) {
        return repository.getAllReviewsByCropIdT(expert);
    }


    public LiveData<List<Farmer_Crops>> getBookmarkedCropByFarmer(String farmerUsername) {
        return repository.getBookmarkedCropsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getisAddCartCropsByFarmer(String farmerUsername) {
        return repository.getisAddCartCropsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getBookmarkedCropsByFarmer1(String farmerUsername, int cropId) {
        return repository.getBookmarkedCropsByFarmer1(farmerUsername, cropId);

    }

    public LiveData<List<Farmer_Crops>> getAddCartCropsByFarmer1(String farmerUsername, int cropId) {
        return repository.getAddCartCropsByFarmer1(farmerUsername, cropId);

    }

    public LiveData<List<Farmer_Crops>> getisRatingCropsByFarmer1(String farmerUsername, int cropId) {
        return repository.getisRatingCropsByFarmer1(farmerUsername, cropId);
    }

    public LiveData<List<Farmer_Crops>> getisRegisterCropsByFarmer1(String farmerUsername) {
        return repository.getisRegisterCropsByFarmer1(farmerUsername);
    }

    public LiveData<Void> deleteFarmerCropByUserAndCrop(String farmerUsername, int cropId) {
        return repository.deleteFarmerCropByUserAndCrop(farmerUsername, cropId);

    }

    public LiveData<Void> delete1FarmerCropByUserAndCrop(String farmerUsername, int cropId) {
        return repository.delete1FarmerCropByUserAndCrop(farmerUsername, cropId);
    }

    public LiveData<Boolean> isFarmerCropExists(String farmerUsername, int cropId, boolean isRegister) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            boolean exists = repository.isFarmerCropExists(farmerUsername, cropId, isRegister);
            result.postValue(exists);
        });
        return result;
    }

    public LiveData<Boolean> isFarmerCropExistsC(String farmerUsername, int cropId, boolean isAddCart) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            boolean exists = repository.isFarmerCropExists1(farmerUsername, cropId, isAddCart);
            result.postValue(exists);
        });
        return result;
    }

    public LiveData<Boolean> isFarmerCropExistsB(String farmerUsername, int cropId, boolean isBookmark) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            boolean exists = repository.isFarmerCropExistsB(farmerUsername, cropId, isBookmark);
            result.postValue(exists);
        });
        return result;
    }

    public void insertFarmerCrop(Farmer_Crops farmerCrop) {
        repository.insertFarmerCrop(farmerCrop);
    }

    public void deleteFarmerCrop(Farmer_Crops farmerCrop) {
        repository.deleteFarmerCrop(farmerCrop);
    }

    public void updateCropFarmer(Farmer_Crops farmerCrop) {
        repository.updateCropFarmer(farmerCrop);
    }

    public LiveData<List<Farmer_Crops>> getCropsByFarmer(String farmerUsername) {
        return repository.getCropsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getFarmersByCrop(int cropId) {
        return repository.getFarmersByCrop(cropId);
    }

    public LiveData<List<Farmer_Crops>> getFarmersByCropAndFarmer(String user, int cropId) {
        return repository.getFarmersByCropAndFarmer(user, cropId);
    }

    void insertFarmer(Farmer farmer) {

        repository.insertFarmer(farmer);


    }

    void updateFarmer(Farmer farmer) {
        repository.updateFarmer(farmer);


    }

    void deleteFarmer(Farmer farmer) {
        repository.deleteFarmer(farmer);


    }

    LiveData<List<Farmer>> getAllFarmer() {

        return repository.getAllFarmer();
    }

    LiveData<List<Farmer>> getFarmerByUsernameAndPassword(String username, String password) {
        return repository.getFarmerByUsernameAndPassword(username, password);
    }

    LiveData<List<Farmer>> getAllFarmerByUser(String farmer_user_name) {

        return repository.getAllFarmerByUser(farmer_user_name);
    }


    void insertExpert(Expert expert) {

        repository.insertExpert(expert);


    }

    void updateExpert(Expert expert) {
        repository.updateExpert(expert);


    }

    void deleteExpert(Expert expert) {
        repository.deleteExpert(expert);


    }

    LiveData<List<Expert>> getAllExpert() {

        return repository.getAllExpert();
    }


    public LiveData<List<Expert>> searchExperts(String expertName) {
        return repository.searchExperts(expertName);
    }

    public LiveData<List<Crop>> searchCrops(String cropName) {
        return repository.searchCrops(cropName);
    }

    LiveData<List<Expert>> getAllExpertByUser(String Expert_USER_Name) {

        return repository.getAllExpertByUser(Expert_USER_Name);
    }

    public void insertFarmerExpert(Farmer_Expert farmerExpert) {
        repository.insertFarmerExpert(farmerExpert);
    }

    public LiveData<List<Farmer_Expert>> getExpertsByFarmer(String farmerUsername) {
        return repository.getExpertsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Expert>> getFarmersByExpert(String expertUsername) {
        return repository.getFarmersByExpert(expertUsername);
    }

}