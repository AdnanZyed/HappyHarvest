package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private List<Button> buttons = new ArrayList<>();
    private Button activeButton = null;
    Button btnAll;
    Button Vegetable_Crops;
    Button btn_fruits;
    Button Bulb_Crops;
    int speed;
    ViewPager2 viewPager;
    Button btn3DDesign;
    Bundle args;
    Bundle bundle;
    RecyclerView expertRecyclerView;
    ExpertAdapter expertAdapter;
    Button btnProgramming;
    Button btnBusiness;
    Button btnArt;
    TextView tv_Seeall;
    TextView tv_Seeall2;
    TextView tv_Welcom;
    TextView tv_Name;
    TextView tv_wind;
    EditText e_searsh;
    ImageView Iv_notification;
    ImageView Iv_Bookmark;
    String farmers_u;
    ImageView iv_S;
    String bio;
    WeatherResponse weather;

    private My_View_Model myViewModel;

    private final double LAT = 31.5;
    private final double LON = 34.47;
    private final String API_KEY = "9e269c7c20355e9e8bba48b0ad2cd52c";

    private TextView tvLocation, tvTemp, tvCondition, tvHumidity;
    private ImageView weatherIcon;
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


        List<Integer> images = Arrays.asList(R.drawable.vp3, R.drawable.vp2, R.drawable.vp1);
        ImageAdapter adapter = new ImageAdapter(images);

        viewPager2.setAdapter(adapter);

        bundle = new Bundle();
        bundle.putString("USER_NAME_R", farmers_u);

        btnAll = rootView.findViewById(R.id.btn_all);
        Vegetable_Crops = rootView.findViewById(R.id.btn_Vegetable);
        Bulb_Crops = rootView.findViewById(R.id.btn_Bulb);
        btn_fruits = rootView.findViewById(R.id.btn_fruits);
        Iv_notification = rootView.findViewById(R.id.iv_notification_h);
        iv_S = rootView.findViewById(R.id.iv_s);
        e_searsh = rootView.findViewById(R.id.et_searsh);
        Iv_Bookmark = rootView.findViewById(R.id.iv_Bookmark);
        tv_Seeall = rootView.findViewById(R.id.tv_seeall);
        tv_Seeall2 = rootView.findViewById(R.id.tv_seeall2);
        tv_Name = rootView.findViewById(R.id.tv_name);
        tv_Welcom = rootView.findViewById(R.id.tv_welcom);
        btn3DDesign = rootView.findViewById(R.id.btn_3d_design);
        btnProgramming = rootView.findViewById(R.id.btn_programming);
        expertRecyclerView = rootView.findViewById(R.id.rv_t);
        btnBusiness = rootView.findViewById(R.id.btn_business);
        btnArt = rootView.findViewById(R.id.btn_art);

        buttonUnderlineMap.put(btnAll, rootView.findViewById(R.id.underline_all));
        buttonUnderlineMap.put(Vegetable_Crops, rootView.findViewById(R.id.underline_vegetable));
        buttonUnderlineMap.put(btn_fruits, rootView.findViewById(R.id.underline_root));
        buttonUnderlineMap.put(Bulb_Crops, rootView.findViewById(R.id.underline_bulb));
        buttonUnderlineMap.put(btnBusiness, rootView.findViewById(R.id.underline_business));
        buttonUnderlineMap.put(btn3DDesign, rootView.findViewById(R.id.underline_3d));
        buttonUnderlineMap.put(btnArt, rootView.findViewById(R.id.underline_art));
        buttonUnderlineMap.put(btnProgramming, rootView.findViewById(R.id.underline_programming));
        CropFragment fragment = (CropFragment) getParentFragmentManager()
                .findFragmentById(R.id.fram_corse);


        if (fragment != null) {
            fragment.loadCrops();
            onButtonClicked(btnAll);
        }


        buttons.add(btnAll);
        buttons.add(btnProgramming);
        buttons.add(Vegetable_Crops);
        buttons.add(Bulb_Crops);
        buttons.add(btn_fruits);
        buttons.add(btn3DDesign);
        buttons.add(btnBusiness);
        buttons.add(btnArt);

        Button defaultButton = btnAll;
//        defaultButton.setBackgroundResource(R.drawable.catigories_btn_selected);
//        defaultButton.setTextColor(R.color.white);
        for (Button button : buttons) {
            button.setOnClickListener(v -> onButtonClicked(button));
        }


        tvLocation = rootView.findViewById(R.id.tv_location);
        tvTemp = rootView.findViewById(R.id.tv_temp);
        tvCondition = rootView.findViewById(R.id.tv_wind);
        tvHumidity = rootView.findViewById(R.id.tv_humidity);
        tv_wind = rootView.findViewById(R.id.tv_wind);
        weatherIcon = rootView.findViewById(R.id.weather_icon_h);

        fetchWeatherData();

        // إعادة التحديث كل ساعة
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

        e_searsh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                e_searsh.setBackgroundResource(R.drawable.shap_selected);

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
        btnArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_Art(weather);
                    onButtonClicked(btnArt);


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
        }); btn_fruits.setOnClickListener(new View.OnClickListener() {
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

        btnBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_Business(weather);
                    onButtonClicked(btnBusiness);


                }

            }
        });

        btnProgramming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_Programming(weather);
                    onButtonClicked(btnProgramming);


                }

            }
        });

        btn3DDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropFragment fragment = (CropFragment) getFragmentManager()
                        .findFragmentById(R.id.fram_corse);

                if (fragment != null) {
                    fragment.loadCrops_Categorie_3D_Design(weather);
                    onButtonClicked(btn3DDesign);


                }

            }
        });


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

        loadExpert();
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
    @SuppressLint("ResourceAsColor")
    private void onButtonClicked(Button clickedButton) {
        for (Map.Entry<Button, View> entry : buttonUnderlineMap.entrySet()) {
            View underline = entry.getValue();
            underline.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
        }

        View selectedUnderline = buttonUnderlineMap.get(clickedButton);
        if (selectedUnderline != null) {
            selectedUnderline.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        activeButton = clickedButton;
    }
    private void fetchWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi service = retrofit.create(WeatherApi.class);

        Call<WeatherResponse> call = service.getCurrentWeather(LAT, LON, API_KEY, "metric", "ar");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                     weather = response.body();
                    if (weather.getWind() != null) {
                         speed = (int) (weather.getWind().getSpeed()* 3.6);
                    }
                    tvLocation.setText(weather.getName());
                    tvTemp.setText((int) weather.getMain().getTemp() + "°C");
                    tvCondition.setText("الرياح: " + speed + "KM");
                    tvHumidity.setText("الرطوبة: " + weather.getMain().getHumidity() + "%");

                    // أيقونة الطقس
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
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "فشل جلب بيانات الطقس", Toast.LENGTH_SHORT).show();
            }
        });

    }}