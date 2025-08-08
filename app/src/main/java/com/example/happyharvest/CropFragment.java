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
                            if (user.equals("")) {
                                NotRegisteredBottomSheet bottomSheet = new NotRegisteredBottomSheet();
                                bottomSheet.show(getActivity().getSupportFragmentManager(), "NotRegisteredBottomSheet");
                            } else {
                                Intent intent = new Intent(requireContext(), CropDetailsActivity1.class);
                                intent.putExtra("ID", crop.getCrop_ID());
                                intent.putExtra("USER", user);
                                startActivity(intent);
                            }

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
        if (!isAdded() || getContext() == null || myViewModel == null) {
            Log.e("CropFragment", "Fragment not attached or ViewModel is null");
            return;
        }

        myViewModel.getAllCrop().observe(getViewLifecycleOwner(), localCrops -> {
            if (localCrops != null && !localCrops.isEmpty()) {
                cropAdapter.setCropList(localCrops);
                recyclerView.setAdapter(cropAdapter);
                setCropClickListener();
            } else {
                loadCropsFromFirebase();
            }
        });
    }

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


            } catch (Exception e) {
                Log.e("IntentError", "Failed to start activity", e);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // إزالة المستمع عند تدمير Fragment
        if (dbRef != null) {
            // dbRef.removeEventListener(valueEventListener);
        }
    }


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

    public void loadCrops_Categorie_btn_grain(WeatherResponse weatherResponse) {


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


            });

        });
    }

    public void loadCrops_Categorie_btn_irrigated(WeatherResponse weatherResponse) {


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


            });

        });
    }

    public void loadCrops_Categorie_fruits(WeatherResponse weatherResponse) {


        myViewModel.getCropsByCategory("Fruit").observe(getViewLifecycleOwner(), crops -> {

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


                FarmingMethodBottomSheet sheet = new FarmingMethodBottomSheet(crop.getCrop_ID(), user);
                sheet.show(getActivity().getSupportFragmentManager(), "FarmingMethodSheet");

            });

        });
    }

    public void loadCrops_Categorie_Vegetable_Crops(WeatherResponse weatherResponse) {


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

            });

        });
    }

    public void loadCrops_Categorie_Bulb_Crops(WeatherResponse weatherResponse) {


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

            });

        });
    }

    public void loadCrops_Categorie_btn_seasonal(WeatherResponse weatherResponse) {


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

            });

        });
    }

    public void loadCrops_Categorie_btn_Highdemand(WeatherResponse weatherResponse) {


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

            });


        });
    }
}