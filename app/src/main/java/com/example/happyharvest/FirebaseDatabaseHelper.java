package com.example.happyharvest;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabaseHelper instance;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;

    private FirebaseDatabaseHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://happy-harvest-2271a-default-rtdb.europe-west1.firebasedatabase.app/");
        dbRef = database.getReference("crops");
       // dbRef = database.getReference("farmers");
        mAuth = FirebaseAuth.getInstance();
       //dbRef = FirebaseDatabase.getInstance().getReference("users");
    }

    public static synchronized FirebaseDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseHelper();
        }
        return instance;
    }
    public interface OnCompleteListener<T> {
        void onComplete(boolean success, Exception exception);
    }


    public void addCrop(Crop crop, OnCompleteListener<Void> listener) {
        String id = dbRef.push().getKey();
        if (id != null) {
            dbRef.child(id).setValue(crop)
                    .addOnCompleteListener(task -> {
                        if (listener != null) {
                            if (task.isSuccessful()) {
                                listener.onComplete(true, null);
                            } else {
                                listener.onComplete(false, task.getException());
                            }
                        }
                    });
        } else {
            if (listener != null) {
                listener.onComplete(false, new Exception("Failed to generate key"));
            }
        }
    }
    public void addFarmer(Farmer farmer, OnCompleteListener<Void> listener) {
        DatabaseReference farmerR = dbRef.getRoot().child("farmers");
        String id = farmerR.push().getKey();

        if (id != null) {
            farmerR.child(id).setValue(farmer)
                    .addOnCompleteListener(task -> {
                        if (listener != null) {
                            if (task.isSuccessful()) {
                                listener.onComplete(true, null);
                            } else {
                                listener.onComplete(false, task.getException());
                            }
                        }
                    });
        } else {
            if (listener != null) {
                listener.onComplete(false, new Exception("Failed to generate key"));
            }
        }
    }

    public void updateCrop(String cropId, Crop updatedCrop, OnCompleteListener<Void> listener) {
        dbRef.child(cropId).setValue(updatedCrop)
                .addOnCompleteListener(task -> {
                    if (listener != null) {
                        if (task.isSuccessful()) {
                            listener.onComplete(true, null);
                        } else {
                            listener.onComplete(false, task.getException());
                        }
                    }
                });
    }

    public void addFarmerCrop(Farmer_Crops farmerCrop, OnCompleteListener<Void> listener) {
        DatabaseReference farmerCropRef = dbRef.getRoot().child("farmer_crops");
        String id = farmerCropRef.push().getKey();

        if (id != null) {
            farmerCropRef.child(id).setValue(farmerCrop)
                    .addOnCompleteListener(task -> {
                        if (listener != null) {

                            if (task.isSuccessful()) {
                                listener.onComplete(true, null);
                            } else {
                                listener.onComplete(false, task.getException());
                            }
                        }
                    });
        } else {
            if (listener != null) {
                listener.onComplete(false, new Exception("Failed to generate key"));
            }
        }
    }

//    public void addFarmer(Farmer farmer, OnCompleteListener<Void> listener) {
//        mAuth.createUserWithEmailAndPassword(farmer.getFarmer_user_name(), farmer.getFarmer_Password())
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // حفظ البيانات في Realtime Database
//                        dbRef.child(farmer.getFarmer_user_name().replace(".", ","))
//                                .setValue(farmer)
//                                .addOnCompleteListener(dbTask -> {
//                                    if (dbTask.isSuccessful()) {
//                                        listener.onComplete(true, null);
//                                    } else {
//                                        // حذف الحساب إذا فشل حفظ البيانات
//                                        mAuth.getCurrentUser().delete();
//                                        listener.onComplete(false, dbTask.getException());
//                                    }
//                                });
//                    } else {
//                        listener.onComplete(false, task.getException());
//                    }
//                });
//    }
    public void checkUserExists(String email, OnCompleteListener<Boolean> listener) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean exists = !task.getResult().getSignInMethods().isEmpty();
                        listener.onComplete(exists, null);
                    } else {
                        listener.onComplete(false, task.getException());
                    }
                });
    }


}