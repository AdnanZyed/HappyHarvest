package com.example.happyharvest;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class My_Repository {

   // private FarmerStepDao farmerStepDao;

    private Farmer_Crop_Dao farmerCropDao;
    private Farmer_Expert_Dao farmerExpertDao;
    private Expert_Dao expertDao;

    private Farmer_Dao farmerDao;
    private Crop_Dao cropDao;
    private CropReviewsDao cropReviewsDao;
    private ExpertReviewsDao expertReviewsDao;
    private CropStepsDao cropStepsDao;
    private final NotificationDao notificationDao;
    private final LiveData<List<Notification>> allNotifications;
    private final MessageDao messageDao;
    private final ExecutorService executorService;

    private LiveData<List<Expert>> AllExpert;
    private LiveData<List<Farmer>> AllFarmers;
    private LiveData<List<Crop>> AllCrop;
    private final DatabaseReference firebaseRef;

    private LiveData<List<Crop>> allCrops;


    My_Repository(Application application) {
        My_Database db = My_Database.getDatabase(application);
        farmerDao = db.farmerDao();
        cropDao = db.cropDao();
        expertDao = db.expertDao();
        cropReviewsDao = db.cropReviewsDao();
        expertReviewsDao = db.expertReviewsDao();
        farmerCropDao = db.farmerCropDao();
        farmerExpertDao = db.farmerExpertDao();
        cropStepsDao = db.cropStepsDao();
        messageDao = db.messageDao();
        executorService = Executors.newSingleThreadExecutor();
     //   this.farmerStepDao = db.farmerStepDao();
        notificationDao = db.notificationDao();
        allNotifications = notificationDao.getAllNotifications();
        firebaseRef = FirebaseDatabase.getInstance().getReference("crops");
        allCrops = cropDao.getAllCrops(); // لازم تكون دالة getAllCrops موجودة في DAO


    }
    public void insertAll(List<Crop> crops) {
        new Thread(() -> cropDao.insertAll(new ArrayList<>(crops))).start();
    }
    public void insertAll1(List<Farmer_Crops> farmerCrops) {
        new Thread(() -> farmerCropDao.insertAll1(new ArrayList<>(farmerCrops))).start();
    }


    public LiveData<List<Crop>> getAllCrops() {
        return allCrops;
    }
//    public LiveData<List<Crop>> getAllCrops() {
//        return (LiveData<List<Crop>>) cropDao.getAllCrops();
//    }
    public void fetchCropsFromFirebase() {
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Crop> crops = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Crop crop = snap.getValue(Crop.class);
                    if (crop != null) crops.add(crop);
                }

                Executors.newSingleThreadExecutor().execute(() -> cropDao.insertAll(crops));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "فشل تحميل البيانات", error.toException());
            }
        });
    }
    public LiveData<List<Crop>> getMultipleCrops(List<Integer> cropIds) {
        return cropDao.getMultipleCrops(cropIds);
    }
    void updateCrop(Crop crop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropDao.updateCrop(crop);


        });


    }

    void insertCrop(Crop crop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropDao.insertCrop(crop);


        });

    }


    void deleteCrop(Crop crop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropDao.deleteCrop(crop);


        });

    }
    public LiveData<List<Crop>> getSesonsByIds(List<Integer> cropIds) {
        return cropDao.getCropsByIds(cropIds);
    }
    public LiveData<List<Crop>> searchCrops(String cropName) {
        return cropDao.getCropName("%" + cropName + "%");
    }
//    public LiveData<List<Crop>> updateBookmarkStatusAndGetCrops(int cropId, boolean isBookmarked) {
//        cropDao.updateBookmarkStatus(cropId, isBookmarked);
//        return cropDao.getAllCrop();
//    }
//
//    public LiveData<List<Crop>> updateisAddCartStatusAndGetCrops(int cropId, boolean isAddCart) {
//        cropDao.updateBookmarkStatus(cropId, isAddCart);
//        return cropDao.getAllCrop();
//    }


    LiveData<List<Crop>> getAllCrop() {
        return cropDao.getAllCrop();
    }

    LiveData<List<Crop>> getAllCropsById(int id) {


        return cropDao.getAllCropsById(id);
    }

    LiveData<List<Crop>> getAllCropsByExpert_USER_Name(String Expert_USER_Name) {


        return cropDao.getAllCropsByExpert_USER_Name(Expert_USER_Name);
    }


    public LiveData<List<Crop>> getCropsByCategory(String category) {
        return cropDao.getCropByCategory(category);
    }
//
//    public LiveData<List<Crop>> getBookmarkedCrops() {
//        return cropDao.getBookmarkedCrops();
//    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    public void insert(Notification notification) {
        executorService.execute(() -> notificationDao.insert(notification));
    }
//
//    public void insertFarmerStep(FarmerStep farmerStep) {
//        My_Database.databaseWriteExecutor.execute(() -> {
//            farmerStepDao.insertFarmerStep(farmerStep);
//        });
//    }
//
//    public void deleteFarmerStep(String farmerUserName, int stepId) {
//        My_Database.databaseWriteExecutor.execute(() -> {
//            farmerStepDao.deleteFarmerStep(farmerUserName, stepId);
//        });
//    }
//
//    public void updateCompletionStatus(String farmerUserName, int stepId, boolean completed) {
//        My_Database.databaseWriteExecutor.execute(() -> {
//            farmerStepDao.updateCompletionStatus(farmerUserName, stepId, completed);
//        });
//    }
//
//    public LiveData<List<FarmerStep>> getCompletedStepsForFarmer(String farmerUserName) {
//        return farmerStepDao.getCompletedStepsForFarmer(farmerUserName);
//    }


    public void insertMessage(Message message) {
        My_Database.databaseWriteExecutor.execute(() -> messageDao.insertMessage(message));
    }

    public LiveData<List<Message>> getMessagesBetweenUsers(String currentUser, String otherUser) {
        return messageDao.getMessagesBetweenUsers(currentUser, otherUser);
    }

    public LiveData<Message> getLastMessageForUser(String username) {
        return messageDao.getLastMessageForUser(username);
    }

    public LiveData<List<Farmer>> getAllFarmersExceptCurrent(String currentUsername) {
        return farmerDao.getAllFarmersExcept(currentUsername);
    }

    public LiveData<Integer> getTotalStepsCountByCropId(int cropId) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            int count = cropStepsDao.getTotalStepsCountByCropId(cropId);
            result.postValue(count);
        });
        return result;
    }

    public LiveData<Integer> getCompletedStepsCountByCropId(int cropId) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            int count = cropStepsDao.getCompletedStepsCountByCropId(cropId);
            result.postValue(count);
        });
        return result;
    }

    public LiveData<Integer> getTotalStepsTimeByCropId(int cropId) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            int totalTime = cropStepsDao.getTotalStepsTimeByCropId(cropId);
            result.postValue(totalTime);
        });
        return result;
    }

    public void insertCropStep(CropStep cropStep) {
        My_Database.databaseWriteExecutor.execute(() -> {

            cropStepsDao.insert(cropStep);

        });

    }
    public LiveData<List<Farmer_Crops>> getFarmerCrop(String farmerName, int cropId) {
        return farmerCropDao.getFarmerCrop(farmerName, cropId);
    }


    public void updateCropStep(CropStep cropStep) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropStepsDao.update(cropStep);

        });


    }

    public LiveData<Integer> getTotalStepsCount() {
        MutableLiveData<Integer> totalStepsCount = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            totalStepsCount.postValue(cropStepsDao.getTotalStepsCount());
        });
        return totalStepsCount;
    }

    public LiveData<Integer> getCompletedStepsCount() {
        MutableLiveData<Integer> completedStepsCount = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            completedStepsCount.postValue(cropStepsDao.getCompletedStepsCount());
        });
        return completedStepsCount;
    }

    public LiveData<Integer> getTotalStepsTime() {
        MutableLiveData<Integer> totalStepsTime = new MutableLiveData<>();
        My_Database.databaseWriteExecutor.execute(() -> {
            totalStepsTime.postValue(cropStepsDao.getTotalStepsTime());
        });
        return totalStepsTime;
    }


    public void deleteCropStep(CropStep cropStep) {
        My_Database.databaseWriteExecutor.execute(() -> {

            cropStepsDao.delete(cropStep);
        });
    }

    public LiveData<List<CropStep>> getStepsByCropId(int cropId) {
        return cropStepsDao.getStepsByCropId(cropId);
    }

    public void updateStepCompletionStatus(int stepId, boolean isCompleted) {
        My_Database.databaseWriteExecutor.execute(() -> {

            cropStepsDao.updateStepCompletionStatus(stepId, isCompleted);
        });

    }

    void insertReview(Crop_Reviews review) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropReviewsDao.insertReview(review);
        });
    }

    void updateReviewByFarmer(String farmerUserName, String newComment, float newRating) {
        My_Database.databaseWriteExecutor.execute(() -> {
            cropReviewsDao.updateReviewByFarmer(farmerUserName, newComment, newRating);
        });
    }

    void deleteReviewByFarmer(String farmerUserName) {
        My_Database.databaseWriteExecutor.execute(() -> {

            cropReviewsDao.deleteReviewByFarmer(farmerUserName);
        });
    }

    public LiveData<List<Crop_Reviews>> getAllReviewsByCropId(int cropId) {
        return cropReviewsDao.getAllReviewsByCropId(cropId);
    }


    void insertReviewT(Expert_Reviews review) {
        My_Database.databaseWriteExecutor.execute(() -> {
            expertReviewsDao.insertReviewT(review);
        });
    }


    void updateReviewByFarmerT(String farmerUserName, String newComment, float newRating) {
        My_Database.databaseWriteExecutor.execute(() -> {
            expertReviewsDao.updateReviewByFarmerT(farmerUserName, newComment, newRating);
        });
    }

    void deleteReviewByFarmerT(String farmerUserName) {
        My_Database.databaseWriteExecutor.execute(() -> {

            expertReviewsDao.deleteReviewByFarmerT(farmerUserName);
        });
    }

    public LiveData<List<Expert_Reviews>> getAllReviewsByCropIdT(String expert) {
        return expertReviewsDao.getAllReviewsByCropIdT(expert);
    }


    public LiveData<List<Farmer_Crops>> getBookmarkedCropsByFarmer(String farmerUsername) {
        return farmerCropDao.getBookmarkedCropsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getBookmarkedCropsByFarmer1(String farmerUsername, int cropId) {
        return farmerCropDao.getBookmarkedCropsByFarmer1(farmerUsername, cropId);

    }

    public LiveData<List<Farmer_Crops>> getAddCartCropsByFarmer1(String farmerUsername, int cropId) {
        return farmerCropDao.getisAddCartCropsByFarmer1(farmerUsername, cropId);
    }

    public LiveData<List<Farmer_Crops>> getisRatingCropsByFarmer1(String farmerUsername, int cropId) {
        return farmerCropDao.getisRatingCropsByFarmer1(farmerUsername, cropId);
    }

    public LiveData<List<Farmer_Crops>> getisRegisterCropsByFarmer1(String farmerUsername) {
        return farmerCropDao.getisRegisterCropsByFarmer1(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getisAddCartCropsByFarmer(String farmerUsername) {
        return farmerCropDao.getisAddCartCropsByFarmer(farmerUsername);
    }

    public boolean isFarmerCropExists(String farmerUsername, int cropId, boolean isRegister) {
        return farmerCropDao.isFarmerCropExists(farmerUsername, cropId, isRegister) > 0;
    }

    public boolean isFarmerCropExists1(String farmerUsername, int cropId, boolean isAddCart) {
        return farmerCropDao.isFarmerCropExists1(farmerUsername, cropId, isAddCart) > 0;
    }

    public boolean isFarmerCropExistsB(String farmerUsername, int cropId, boolean isBookmark) {
        return farmerCropDao.isFarmerCropExistsB(farmerUsername, cropId, isBookmark) > 0;
    }

    public void insertFarmerCrop(Farmer_Crops farmerCrop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerCropDao.insertFarmerCrop(farmerCrop);
        });
    } public void deleteFarmerCrop(Farmer_Crops farmerCrop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerCropDao.deleteFarmerCrop(farmerCrop);
        });
    }

    public void updateCropFarmer(Farmer_Crops farmerCrop) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerCropDao.updateCropFarmer(farmerCrop);
        });
    }

    public LiveData<Void> deleteFarmerCropByUserAndCrop(String farmerUsername, int cropId) {
        MutableLiveData<Void> result = new MutableLiveData<>();

        executorService.execute(() -> {
            farmerCropDao.deleteFarmerCropByUserAndCrop(farmerUsername, cropId);
            result.postValue(null);
        });
        return result;

    }    public LiveData<Void> delete1FarmerCropByUserAndCrop(String farmerUsername, int cropId) {
        MutableLiveData<Void> result = new MutableLiveData<>();

        executorService.execute(() -> {
            farmerCropDao.delete1FarmerCropByUserAndCrop(farmerUsername, cropId);
            result.postValue(null);
        });
        return result;

    }

    public LiveData<List<Farmer_Crops>> getCropsByFarmer(String farmerUsername) {
        return farmerCropDao.getCropsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Crops>> getFarmersByCrop(int cropId) {
        return farmerCropDao.getFarmersByCrop(cropId);
    }

    public LiveData<List<Farmer_Crops>> getFarmersByCropAndFarmer(String user, int cropId) {
        return farmerCropDao.getFarmersByCropAndFarmer(user, cropId);
    }



    void insertFarmer(Farmer farmer) {
        My_Database.databaseWriteExecutor.execute(() -> {

            farmerDao.insertFarmer(farmer);


        });
    }

    void updateFarmer(Farmer farmer) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerDao.updateFarmer(farmer);


        });
    }

    void deleteFarmer(Farmer farmer) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerDao.deleteFarmer(farmer);


        });
    }

    LiveData<List<Farmer>> getAllFarmer() {

        return farmerDao.getAllFarmers();
    }

    LiveData<List<Farmer>> getFarmerByUsernameAndPassword(String username, String password) {
        return farmerDao.getFarmerByUsernameAndPassword(username, password);
    }

    LiveData<List<Farmer>> getAllFarmerByUser(String farmer_user_name) {

        return farmerDao.getAllFarmersByUser(farmer_user_name);
    }


    void insertExpert(Expert expert) {
        My_Database.databaseWriteExecutor.execute(() -> {

            expertDao.insertExpert(expert);


        });
    }

    void updateExpert(Expert expert) {
        My_Database.databaseWriteExecutor.execute(() -> {
            expertDao.updateExpert(expert);


        });
    }

    void deleteExpert(Expert expert) {
        My_Database.databaseWriteExecutor.execute(() -> {
            expertDao.deleteExpert(expert);


        });
    }

    LiveData<List<Expert>> getAllExpert() {

        return expertDao.getAllExperts();
    }

    public LiveData<List<Expert>> searchExperts(String expertName) {
        return expertDao.getExpertByName("%" + expertName + "%");
    }


    LiveData<List<Expert>> getAllExpertByUser(String Expert_USER_Name) {

        return expertDao.getAllExpertsByUser(Expert_USER_Name);
    }

    public void insertFarmerExpert(Farmer_Expert farmerExpert) {
        My_Database.databaseWriteExecutor.execute(() -> {
            farmerExpertDao.insertFarmerExpert(farmerExpert);
        });
    }

    public LiveData<List<Farmer_Expert>> getExpertsByFarmer(String farmerUsername) {
        return farmerExpertDao.getExpertsByFarmer(farmerUsername);
    }

    public LiveData<List<Farmer_Expert>> getFarmersByExpert(String expertUsername) {
        return farmerExpertDao.getFarmersByExpert(expertUsername);
    }

}
