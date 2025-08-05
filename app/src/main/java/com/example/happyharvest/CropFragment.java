package com.example.happyharvest;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CropFragment extends Fragment {

    private My_View_Model myViewModel;
    private CropAdapter cropAdapter;
    private String user;
    private RecyclerView recyclerView;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);

        user = getArguments() != null ? getArguments().getString("USER_NAME_R") : "default_user";
        cropAdapter = new CropAdapter(requireContext(), new ArrayList<>(), user);
        recyclerView = view.findViewById(R.id.rv_Crops);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadCrops();


        return view;
    }

    private void setCropClickListener() {
        if (cropAdapter != null) {
            cropAdapter.setOnCropClickListener(crop -> {
                if (isAdded() && getContext() != null) {
                    myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {
                        if (farmerCrops == null || farmerCrops.isEmpty()) {
                            FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                            sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                        } else {
                            Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                            intent.putExtra("ID", crop.getCrop_ID());
                            intent.putExtra("USER", user);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }

    private void loadCropsFromFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://happy-harvest-2271a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("crops");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!isAdded()) return;

                if (!dataSnapshot.exists()) {
                    Toast.makeText(getContext(), "مشكلة في الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Crop> cropList = new ArrayList<>();
                for (DataSnapshot cropSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Crop crop = cropSnapshot.getValue(Crop.class);
                        if (crop != null) {
                            cropList.add(crop);
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error parsing crop: " + e.getMessage());
                    }
                }

                if (!cropList.isEmpty()) {
                    new Thread(() -> {

                        new Handler(Looper.getMainLooper()).post(() -> {
                            myViewModel.insertAllCrops(cropList);
                            Toast.makeText(requireContext(), "تم جلب البيانات بنجاح", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value: " + error.getMessage());
                if (isAdded() && getContext() != null) {
                    Toast.makeText(getContext(), "خطأ في جلب البيانات", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadCrops() {
        if (!isAdded() || getContext() == null) return;

        myViewModel.getAllCrop().observe(getViewLifecycleOwner(), localCrops -> {
            if (localCrops != null && !localCrops.isEmpty()) {
                // البيانات موجودة بالفعل، لا تقم بتحميلها من Firebase
                cropAdapter.setCropList(localCrops);
                recyclerView.setAdapter(cropAdapter);
                setCropClickListener();  // تابع لتعيين click listener
            } else {
                // البيانات غير موجودة، حملها من Firebase
                loadCropsFromFirebase();
            }
        });
    }

//    public void loadCrops1() {
//        // 1. التحقق من اتصال Fragment بالنشاط
//        if (!isAdded() || getContext() == null) {
//            Log.w("CropFragment", "Fragment not attached to activity");
//            return;
//        }        dbRef = database.getReference("crops"); // لازم تعملها قبل push()

//
//        // 2. الحصول على مرجع قاعدة البيانات
//        dbRef = FirebaseDatabase.getInstance().getReference("crops");
//
//        // 3. إضافة مستمع للبيانات
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!isAdded() || getContext() == null) return;
//
//                Log.d("FirebaseData", "DataSnapshot: " + dataSnapshot.toString());
//
//                if (!dataSnapshot.exists()) {
//                    Log.w("FirebaseData", "No data available at crops node");
//                    Toast.makeText(getContext(), "لا توجد محاصيل متاحة", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                List<Crop> cropList = new ArrayList<>();
//                for (DataSnapshot cropSnapshot : dataSnapshot.getChildren()) {
//                    try {
//                        Crop crop = cropSnapshot.getValue(Crop.class);
//                        if (crop != null) {
//                        //    crop.setFirebaseKey(cropSnapshot.getKey()); // حفظ المفتاح إذا لزم الأمر
//                            cropList.add(crop);
//                            Log.d("FirebaseData", "Loaded crop: " + crop.getCrop_NAME());
//                        }
//                    } catch (Exception e) {
//                        Log.e("FirebaseError", "Error parsing crop at key: " + cropSnapshot.getKey(), e);
//                    }
//                }
//
//                updateCropList(cropList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("FirebaseError", "Failed to read value: " + error.getMessage() +
//                        "\nDetails: " + error.getDetails());
//                if (isAdded() && getContext() != null) {
//                    Toast.makeText(getContext(), "خطأ في جلب البيانات", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void updateCropList(List<Crop> crops) {
        if (getActivity() == null || cropAdapter == null) {
            Log.w("CropFragment", "Activity or Adapter is null");
            return;
        }

        getActivity().runOnUiThread(() -> {
            if (cropAdapter != null) {
                cropAdapter.setCropList(crops);
                cropAdapter.notifyDataSetChanged();
                Log.d("CropFragment", "Adapter updated with " + crops.size() + " crops");

                setupItemClickListener();
            }
        });
    }

    private void setupItemClickListener() {
        cropAdapter.setOnCropClickListener(crop -> {
            if (!isAdded() || getContext() == null) return;

            try {
//                Intent intent = new Intent(getContext(), CropDetailsActivity.class);
                myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {
                    if (farmerCrops.isEmpty() || farmerCrops == null) {
                        FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                        sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                    } else {
                        Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                        intent.putExtra("ID", crop.getCrop_ID());
                        intent.putExtra("USER", user);
                        startActivity(intent);
                    }
                });

                // إضافة البيانات بشكل آمن
//                if (crop.getCrop_ID() != null)
//                    intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                if (user != null)
//                    intent.putExtra("USER", user);
//                if (crop.getExpert_USER_Name() != null)
//                    intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
                // ... باقي البيانات بنفس الطريقة

//                startActivity(intent);
            } catch (Exception e) {
                Log.e("IntentError", "Failed to start activity", e);
            }
        });
    }

    // باقي الدوال (loadCropsByUserExpert, loadCrops_Categorie_XXX) تبقى كما هي بدون تغيير
    // ...

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // إزالة المستمع عند تدمير Fragment
        if (dbRef != null) {
            // dbRef.removeEventListener(valueEventListener);
        }
    }


//    public void loadCrops() {
//        // 1. التحقق من اتصال Fragment بالنشاط
//        if (!isAdded() || getContext() == null) {
//            return;
//        }
//
//        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://happy-harvest-2271a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("crops");
//
//        // 2. استخدام ListenerForSingleValueEvent للقراءة لمرة واحدة
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!isAdded()) return; // التأكد من أن Fragment لا يزال مضافًا
//
//                if (!dataSnapshot.exists()) {
//                    Toast.makeText(getContext(), "مشكلة في الاتصال بالخادم", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                List<Crop> cropList = new ArrayList<>();
//                for (DataSnapshot cropSnapshot : dataSnapshot.getChildren()) {
//                    try {
//                        Crop crop = cropSnapshot.getValue(Crop.class);
//                        if (crop != null) {
//
//                            cropList.add(crop);
//                            myViewModel.insertAllCrops(cropList);
//                            //Toast.makeText(requireContext(), "cropList"+cropList, Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } catch (Exception e) {
//                        Log.e("Firebase", "Error parsing crop: " + e.getMessage());
//                    }
//                }
//                if (!cropList.isEmpty()) {
//                    myViewModel.insertAllCrops(cropList);
//                }
//                // 3. تحديث UI على main thread
////                if (getActivity() != null) {
////                    getActivity().runOnUiThread(() -> {
//                myViewModel.getAllCrop().observe(getViewLifecycleOwner(), crops -> {
//                    cropAdapter.setCropList(crops);
//                    recyclerView.setAdapter(cropAdapter); // يجب تعيين Adapter هنا قبل loadCrops()
//
//                });
//                if (cropAdapter != null) {
//
//                    cropAdapter.setOnCropClickListener(crop -> {
//                        if (isAdded() && getContext() != null) {
//
//                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {
//
//
//                                if (farmerCrops.isEmpty() || farmerCrops == null) {
//
//                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
//                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
//
//                                } else {
//
//                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
//                                    intent.putExtra("ID", crop.getCrop_ID());
//                                    intent.putExtra("USER", user);
//                                    startActivity(intent);
//
//                                }
//                            });
////                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(),user);
////                                    sheet.show(getSupportFragmentManager(), "FarmingMethodSheet");
////                                    Intent intent = new Intent(getContext(), CropDetailsActivity.class);
////                                    intent.putExtra("COURSE_ID", crop.getCrop_ID());
////                                    intent.putExtra("USER", user);
////                                    intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
////                                    intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
////                                    intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
////                                    intent.putExtra("COURSE_CATEGORIES", crop.getCategorie());
////                                    intent.putExtra("COURSE_IMAGE", crop.getImage());
//                            //   intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//                            //     startActivity(intent);
//                        }
//                    });
//                } else {
//                    Log.e("Firebase", "Adapter is null");
//                }

    /// /                    });
    /// /                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Failed to read value: " + error.getMessage());
//                if (isAdded() && getContext() != null) {
//                    Toast.makeText(getContext(), "خطأ في جلب البيانات", Toast.LENGTH_SHORT).show();
//                }
//           }
//        });
//    }

    // 3. دالة منفصلة لتحديث الواجهة
    private void updateUI(List<Crop> crops) {
        if (getActivity() == null || cropAdapter == null) return;

        getActivity().runOnUiThread(() -> {
            cropAdapter.setCropList(crops);
            cropAdapter.notifyDataSetChanged();

            cropAdapter.setOnCropClickListener(crop -> {
                myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                    if (farmerCrops.isEmpty() || farmerCrops == null) {
                        FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                        sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                    } else {

                        Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                        intent.putExtra("ID", crop.getCrop_ID());
                        intent.putExtra("USER", user);

                        startActivity(intent);
                    }
                });
            });
        });
    }

    public void loadCropsByUserExpert(String string) {


        myViewModel.getAllcropsByExpert_USER_Name(string).observe(getViewLifecycleOwner(), crops -> {
            cropAdapter.setCropList(crops);
            cropAdapter.setOnCropClickListener(crop -> {

                myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                    if (farmerCrops.isEmpty() || farmerCrops == null) {
                        FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                        sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                    } else {

                        Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                        intent.putExtra("ID", crop.getCrop_ID());
                        intent.putExtra("USER", user);

                        startActivity(intent);
                    }
                });
//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//                intent.putExtra("USER", user);
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//
//                //intent.putExtra("COURSE_PRICE", crop.getPrice());
//           //     intent.putExtra("COURSE_IMAGE", crop.getImage());
//              //  intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
            });

        });
    }

    public void loadCrops_Categorie_btn_Root(WeatherResponse weatherResponse) {


        myViewModel.getCropsByCategory("Root").observe(getViewLifecycleOwner(), crops -> {

            cropAdapter.setCropList(crops);

            cropAdapter.setOnCropClickListener(crop -> {
                myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                    if (farmerCrops.isEmpty() || farmerCrops == null) {
                        FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                        sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                    } else {

                        Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                        intent.putExtra("ID", crop.getCrop_ID());
                        intent.putExtra("USER", user);
                        intent.putExtra("weather", (CharSequence) weatherResponse);
                        startActivity(intent);
                    }
                });
            });

        });
    }
                public void loadCrops_Categorie_btn_grain (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("grain").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {
                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);
                                    startActivity(intent);
                                }
                            });

//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//             //   intent.putExtra("COURSE_IMAGE", crop.getImage());
//                intent.putExtra("USER", user);
//
//            //    intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }

                public void loadCrops_Categorie_btn_irrigated (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("Irrigated").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {
                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {
                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });

//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//                intent.putExtra("USER", user);
//              //  intent.putExtra("COURSE_IMAGE", crop.getImage());
//              //  intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }

                public void loadCrops_Categorie_fruits (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("fruits").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {
                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });
//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//                intent.putExtra("USER", user);
//             //   intent.putExtra("COURSE_IMAGE", crop.getImage());
//              //  intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);

                            FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                            sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");

                        });

                    });
                }

                public void loadCrops_Categorie_Vegetable_Crops (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("Vegetable").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {
                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });
//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//                intent.putExtra("USER", user);
//             //   intent.putExtra("COURSE_IMAGE", crop.getImage());
//             //   intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }

                public void loadCrops_Categorie_Bulb_Crops (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("Bulb").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {

                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });
//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//                intent.putExtra("USER", user);
//              //  intent.putExtra("COURSE_IMAGE", crop.getImage());
//             //   intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }
                public void loadCrops_Categorie_btn_seasonal (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("seasonal").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {

                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });
//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//              //  intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//                intent.putExtra("USER", user);
//              //  intent.putExtra("COURSE_IMAGE", crop.getImage());
//             //   intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }

                public void loadCrops_Categorie_btn_Highdemand (WeatherResponse weatherResponse){


                    myViewModel.getCropsByCategory("Highdemand").observe(getViewLifecycleOwner(), crops -> {

                        cropAdapter.setCropList(crops);

                        cropAdapter.setOnCropClickListener(crop -> {
                            myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {


                                if (farmerCrops.isEmpty() || farmerCrops == null) {
                                    FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                                    sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
                                } else {

                                    Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                    intent.putExtra("ID", crop.getCrop_ID());
                                    intent.putExtra("USER", user);
                                    intent.putExtra("weather", (CharSequence) weatherResponse);

                                    startActivity(intent);
                                }
                            });

//                Intent intent = new Intent(requireContext(), CropDetailsActivity.class);
//
//                intent.putExtra("COURSE_ID", crop.getCrop_ID());
//                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
//                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
//               // intent.putExtra("COURSE_PRICE", crop.getPrice());
//                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
//
//              //  intent.putExtra("COURSE_IMAGE", crop.getImage());
//           //     intent.putExtra("TEACHER_NAME", crop.getExpert_name());
//
//
//                startActivity(intent);
                        });

                    });
                }

//    public void loadCrops_Categorie_3D_Design(WeatherResponse weatherResponse) {
//
//
//        myViewModel.getCropsByCategory("3D Design").observe(getViewLifecycleOwner(), crops -> {
//
//            cropAdapter.setCropList(crops);
//
//            cropAdapter.setOnCropClickListener(crop -> {
//                myViewModel.getFarmersByCropAndFarmer(user, crop.getCrop_ID()).observe(getViewLifecycleOwner(), farmerCrops -> {
//
//
//                    if (farmerCrops.isEmpty() || farmerCrops == null) {
//                        FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
//                        sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");
//                    } else {
//
//                        Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
//                        intent.putExtra("ID", crop.getCrop_ID());
//                        intent.putExtra("USER", user);
//                        intent.putExtra("weather", (CharSequence) weatherResponse);
//
//                        startActivity(intent);
//                    }
//                });
////                intent.putExtra("COURSE_ID", crop.getCrop_ID());
////                intent.putExtra("TEACHER_USER_NAME", crop.getExpert_USER_Name());
////                intent.putExtra("COURSE_NAME", crop.getCrop_NAME());
////               // intent.putExtra("COURSE_PRICE", crop.getPrice());
////                intent.putExtra("COURSE_DESCRIPTION", crop.getDescription());
////
////             //   intent.putExtra("COURSE_IMAGE", crop.getImage());
////             //   intent.putExtra("TEACHER_NAME", crop.getExpert_name());
////
////
////
//            });
//
//        });
//    }
            }
