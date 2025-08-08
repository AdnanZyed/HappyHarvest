package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private List<Button> buttons = new ArrayList<>();
    private Button activeButton = null;
    private Button Vegetable_Crops, btn_fruits, btnAll, btn_Root, btn_seasonal, Bulb_Crops, btn_Highdemand, btn_irrigated, btn_grain;

    private int speed;
    private ViewPager2 viewPager;
    private Bundle args, bundle;
    private RecyclerView expertRecyclerView;
    private ExpertAdapter expertAdapter;
    private double lat, lon;
    private TextView tv_Seeall2, tv_Welcom, tv_Name, tv_wind, e_searsh, tvLocation, tvTemp, tvCondition, tvHumidity;


    private String farmers_u, bio;
    private ImageView tv_Seeall, iv_S, Iv_Bookmark, weatherIcon, Iv_notification, menu;
    private WeatherResponse weather;
    private My_View_Model myViewModel;
    private Map<Button, View> buttonUnderlineMap = new HashMap<>();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);

        args = getArguments();
        if (args != null) {
            farmers_u = args.getString("USER_NAME");
        }
        ViewPager2 viewPager2 = rootView.findViewById(R.id.viewPager2);
        List<Integer> images = Arrays.asList(R.drawable.dec3, R.drawable.dec2, R.drawable.dec1);
        ImageAdapter adapter = new ImageAdapter(images);
        viewPager2.setAdapter(adapter);
        bundle = new Bundle();
        bundle.putString("USER_NAME_R", farmers_u);
        btnAll = rootView.findViewById(R.id.btn_all);
        menu = rootView.findViewById(R.id.menu);
        btn_Root = rootView.findViewById(R.id.btn_Root);
        Vegetable_Crops = rootView.findViewById(R.id.btn_Vegetable);
        Bulb_Crops = rootView.findViewById(R.id.btn_Bulb);
        btn_seasonal = rootView.findViewById(R.id.btn_seasonal);
        btn_fruits = rootView.findViewById(R.id.btn_Fruits);
        btn_grain = rootView.findViewById(R.id.btn_grain);
        btn_irrigated = rootView.findViewById(R.id.btn_irrigated);
        btn_Highdemand = rootView.findViewById(R.id.btn_Highdemand);
        Iv_notification = rootView.findViewById(R.id.iv_notification_h);
        iv_S = rootView.findViewById(R.id.iv_s);
     //   e_searsh = rootView.findViewById(R.id.et_searsh);
        Iv_Bookmark = rootView.findViewById(R.id.iv_Bookmark);
        tv_Seeall = rootView.findViewById(R.id.tv_seeall);
        tv_Seeall2 = rootView.findViewById(R.id.tv_seeall2);
        tv_Name = rootView.findViewById(R.id.tv_name);
        tv_Welcom = rootView.findViewById(R.id.tv_welcom);
        // btn_Bulb = rootView.findViewById(R.id.btn_Bulb);
        expertRecyclerView = rootView.findViewById(R.id.rv_t);

        buttonUnderlineMap.put(btnAll, rootView.findViewById(R.id.underline_all));
        buttonUnderlineMap.put(btn_Root, rootView.findViewById(R.id.underline_root));
        buttonUnderlineMap.put(Vegetable_Crops, rootView.findViewById(R.id.underline_vegetable));
        buttonUnderlineMap.put(btn_fruits, rootView.findViewById(R.id.underline_fruits));
        buttonUnderlineMap.put(Bulb_Crops, rootView.findViewById(R.id.underline_Bulb));
        buttonUnderlineMap.put(btn_seasonal, rootView.findViewById(R.id.underline_Bulb));
        buttonUnderlineMap.put(btn_Highdemand, rootView.findViewById(R.id.underline_Highdemand));
        //buttonUnderlineMap.put(btn_Bulb, rootView.findViewById(R.id.underline_bulb));
        buttonUnderlineMap.put(btn_grain, rootView.findViewById(R.id.underline_grain));
        buttonUnderlineMap.put(btn_irrigated, rootView.findViewById(R.id.underline_Irrigated));
        CropFragment fragment = (CropFragment) getParentFragmentManager()
                .findFragmentById(R.id.fram_corse);
        if (fragment != null && isAdded()) {
            try {
                fragment.loadCrops();
                onButtonClicked(btnAll);
            } catch (Exception e) {
                Log.e("HomeFragment", "Error loading crops", e);
            }
        }


        new Thread(() -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                loadFarmerCrops();
            }, 3000);
        }).start();


        buttons.add(btnAll);
        buttons.add(btn_Root);
        buttons.add(btn_irrigated);
        buttons.add(Vegetable_Crops);
        buttons.add(Bulb_Crops);
        buttons.add(btn_fruits);
        // buttons.add(btn_Bulb);
        buttons.add(btn_Highdemand);
        buttons.add(btn_grain);

        // Button defaultButton = btnAll;
        //  defaultButton.setBackgroundResource(R.drawable.catigories_btn_selected);
        //   defaultButton.setTextColor(R.color.white);
        for (Button button : buttons) {
            button.setOnClickListener(v -> onButtonClicked(button));
        }


        tvLocation = rootView.findViewById(R.id.tv_location);
        tvTemp = rootView.findViewById(R.id.tv_temp);
        tvCondition = rootView.findViewById(R.id.tv_wind);
        tvHumidity = rootView.findViewById(R.id.tv_humidity);
        tv_wind = rootView.findViewById(R.id.tv_wind);
        weatherIcon = rootView.findViewById(R.id.weather_icon_h);

        if (farmers_u.equals("")){

        }else{

            fetchWeatherData();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchWeatherData();
                    handler.postDelayed(this, 3600000); // كل ساعة
                }
            }, 3600000);


            myViewModel.getAllFarmerByUser(farmers_u).observe(getViewLifecycleOwner(), farmers -> {
                if (farmers != null && !farmers.isEmpty()) {
                    Farmer farmer = farmers.get(0);
                    String farmer_name = farmer.getS_name().toString();
                    bio = farmer.getBio().toString();
                    if (bio.isEmpty()) {
                        tv_Welcom.setText("Welcom");
                    } else {
                        tv_Welcom.setText(bio);
                    }
                    byte[] bytes = farmer.getS_Image();
                    if (bytes != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        iv_S.setImageBitmap(bitmap);
                    } else {
                        iv_S.setImageResource(R.drawable.profile);
                    }

                    tv_Name.setText(farmer_name);
                }
            });
            loadExpert();

        }


//        e_searsh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                e_searsh.setBackgroundResource(R.drawable.shap_selected);
//
//            }
//        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        iv_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FarmersProfileFragment fragment = new FarmersProfileFragment();
                Bundle args = new Bundle();
                args.putString("USER_NAME_R", farmers_u);
                fragment.setArguments(args);


                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        Iv_Bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireContext(), BookmarkActivity.class);
                intent.putExtra("USER", farmers_u);

                startActivity(intent);
            }
        });


        if (savedInstanceState == null) {
            CropFragment cropFragment = new CropFragment();
            Bundle bundle = new Bundle();
            bundle.putString("USER_NAME_R", farmers_u);
            cropFragment.setArguments(bundle);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fram_corse, cropFragment)
                    .commit();
            onButtonClicked(btnAll);

        }

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops();
                    onButtonClicked(btnAll);


                }
            }
        });
        btn_grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_btn_grain(weather);
                    onButtonClicked(btn_grain);


                }

            }
        });
        btn_Root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_btn_Root(weather);
                    onButtonClicked(btn_Root);


                }

            }
        });

        Vegetable_Crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_Vegetable_Crops(weather);
                    onButtonClicked(Vegetable_Crops);


                }

            }
        });
        Bulb_Crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_Bulb_Crops(weather);
                    onButtonClicked(Bulb_Crops);


                }

            }
        });
        btn_fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_fruits(weather);
                    onButtonClicked(btn_fruits);


                }

            }
        });
        btn_seasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_btn_seasonal(weather);
                    onButtonClicked(btn_seasonal);


                }

            }
        });
        btn_fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_fruits(weather);
                    onButtonClicked(btn_fruits);


                }

            }
        });

        btn_Highdemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_btn_Highdemand(weather);
                    onButtonClicked(btn_Highdemand);


                }

            }
        });

        btn_irrigated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_btn_irrigated(weather);
                    onButtonClicked(btn_irrigated);


                }

            }
        });

//        btn_Bulb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                CropFragment fragment = (CropFragment) getFragmentManager()
//                        .findFragmentById(R.id.fram_corse);
//
//                if (fragment != null) {
//                    fragment.loadCrops_Categorie_3D_Design(weather);
//                    onButtonClicked(btn_Bulb);
//
//
//                }
//
//            }
//        });


        tv_Seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), TopMentors.class);
                intent.putExtra("STUDENT_USER", farmers_u);
                startActivity(intent);
            }
        });
        tv_Seeall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Most_Popular.class);
                intent.putExtra("USER", farmers_u);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void loadExpert() {
        myViewModel.getAllExpert().observe(getViewLifecycleOwner(), experts -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

            expertAdapter = new ExpertAdapter(requireContext(), new ArrayList<>(), farmers_u);
            expertRecyclerView.setLayoutManager(layoutManager);
            expertAdapter.setExpertList(experts);
            expertRecyclerView.setAdapter(expertAdapter);

        });
    }

    private void onButtonClicked(Button clickedButton) {
        if (clickedButton == null) return;

        for (Button button : buttonUnderlineMap.keySet()) {
            button.setBackgroundResource(R.drawable.shape_selected_btn);

        }

        clickedButton.setBackgroundResource(R.drawable.catigories_btn);

        activeButton = clickedButton;
    }


    private void fetchWeatherData() {
        myViewModel.getAllFarmerByUser(farmers_u).observe(getViewLifecycleOwner(), farmers -> {
            lat = farmers.get(0).getLatitude();
            lon = farmers.get(0).getLongitude();

        });

        WeatherManager.getInstance().fetchWeather(getContext(), lat, lon, new WeatherManager.WeatherCallback() {
            @Override
            public void onWeatherLoaded(WeatherResponse response) {
                weather = response;
                if (weather.getWind() != null) {
                    speed = (int) (weather.getWind().getSpeed() * 3.6);
                }
                tvLocation.setText(weather.getName());
                tvTemp.setText((int) weather.getMain().getTemp() + "°C");
                tvCondition.setText("الرياح: " + speed + "KM");
                tvHumidity.setText("الرطوبة: " + weather.getMain().getHumidity() + "%");

                String iconCode = weather.getWeather().get(0).getIcon();
                int iconRes;
                switch (iconCode) {
                    case "01d":
                        iconRes = R.drawable.sun;
                        break;
                    case "02d":
                        iconRes = R.drawable.cloudy;
                        break;
                    case "03d":
                    case "04d":
                        iconRes = R.drawable.clouds;
                        break;
                    case "09d":
                    case "10d":
                        iconRes = R.drawable.heavyrain;
                        break;
                    default:
                        iconRes = R.drawable.sun;
                }
                weatherIcon.setImageResource(iconRes);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(requireContext(), "فشل جلب بيانات الطقس", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadFarmerCrops() {
        if (!isAdded() || getContext() == null) return;

        myViewModel.getCropsByFarmer(farmers_u).observe(getViewLifecycleOwner(), localCrops -> {
            if (localCrops != null && !localCrops.isEmpty()) {
            } else {
                loadFarmerCropsForUser(farmers_u);

            }
        });
    }

    private void loadFarmerCropsForUser(String userName) {
        DatabaseReference farmerCropRef = FirebaseDatabase.getInstance("https://happy-harvest-2271a-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("farmer_crops");

        Query userQuery = farmerCropRef.orderByChild("farmer_user_name").equalTo(userName);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!isAdded()) return;

                List<Farmer_Crops> userCrops = new ArrayList<>();

                for (DataSnapshot cropSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Farmer_Crops farmerCrop = cropSnapshot.getValue(Farmer_Crops.class);
                        if (farmerCrop != null) {
                            if (cropSnapshot.getKey() != null) {
                                farmerCrop.setFirebaseKey(cropSnapshot.getKey());
                            }
                            userCrops.add(farmerCrop);
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error parsing crop: " + e.getMessage());
                    }
                }

                if (!userCrops.isEmpty()) {


                    myViewModel.insertAllFarmerCrop(userCrops);
                    Toast.makeText(requireContext(), "تم جلب البيانات بنجاح", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Load failed: " + error.getMessage());
            }
        });
    }
}