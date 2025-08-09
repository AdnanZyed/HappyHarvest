package com.example.happyharvest;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.happyharvest.Crop;
import com.example.happyharvest.My_View_Model;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyCareFragment extends Fragment {
    private static final String ARG_CROP_ID = "crop_id";
    private static final String ARG_FARMER_USERNAME = "farmer_username";
    private static final String CHANNEL_ID = "fertilizer_channel";
    private TextView textSoilComparison, textFertilizing, textPreviousCrop, textSeedlingsCount, textFertilizerInfo, fetchWeatherData, textWatering, textCountdown, textPruning, textPestControl;
    private My_View_Model myViewModel;
    private int cropId;
    public Call<WeatherResponseOneCall> call1;
    private Crop cropM;
    private ImageView icon1, icon2, icon3, icon4, clock_f;

    private View line1, line2, line3;

    private CardView card_elements, card_irrigation, card_fertilizing, card_pruning;
    private final String API_KEY = "001bfc6226ca41a7c7b095524a7cf212";
    private Farmer_Crops farmerCropM;
    private NotificationManagerCompat notificationManager;
    private String farmerUsername;
    private AlarmManager alarmManager;
    private CheckBox fertCheckbox;
    private CountDownTimer countDownTimer;

    public static DailyCareFragment newInstance(int cropId, String farmerUsername) {
        DailyCareFragment fragment = new DailyCareFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CROP_ID, cropId);
        args.putString(ARG_FARMER_USERNAME, farmerUsername);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cropId = getArguments().getInt(ARG_CROP_ID);
            farmerUsername = getArguments().getString(ARG_FARMER_USERNAME);
        }

        createNotificationChannel();
        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        notificationManager = NotificationManagerCompat.from(requireContext());

    }

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_care, container, false);

        textWatering = view.findViewById(R.id.text_watering);
        textFertilizing = view.findViewById(R.id.text_fertilizing);
        textPruning = view.findViewById(R.id.text_pruning);
        fetchWeatherData = view.findViewById(R.id.fetchWeatherData);
        clock_f = view.findViewById(R.id.clock_f);
        textPestControl = view.findViewById(R.id.text_pest_control);
        textSoilComparison = view.findViewById(R.id.text_soil_comparison);
        textPreviousCrop = view.findViewById(R.id.text_elements_crop);
        textSeedlingsCount = view.findViewById(R.id.text_seedlings_count);
        textFertilizerInfo = view.findViewById(R.id.text_fertilizer_info);
        //Button btnSchedule = view.findViewById(R.id.btn_schedule);
        fertCheckbox = view.findViewById(R.id.checkbox_fert_done_f);
        textCountdown = view.findViewById(R.id.text_counter_f);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line3 = view.findViewById(R.id.line3);
        ProgressBar progressBar = view.findViewById(R.id.circularProgressBar);
        progressBar.setProgress(50);
        icon1 = view.findViewById(R.id.icon1);
        icon2 = view.findViewById(R.id.icon2);
        icon3 = view.findViewById(R.id.icon3);
        icon4 = view.findViewById(R.id.icon4);

        card_elements = view.findViewById(R.id.card_elements);
        card_irrigation = view.findViewById(R.id.card_irrigation);
        card_fertilizing = view.findViewById(R.id.card_fertilizing);
        card_pruning = view.findViewById(R.id.card_pruning);

        icon1.setEnabled(true);
        icon1.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));

        icon2.setEnabled(false);
        //icon2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));

        icon3.setEnabled(false);
        // icon2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));

        icon4.setEnabled(false);
        //  icon3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));


        fertCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //handleFertilizationCompleted();

                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                farmerCropM.setLastFertilizerDate(currentDate);
                myViewModel.updateCropFarmer(farmerCropM);
                // textFertilizerInfo.setText(updateFertilizerInfo(cropM, farmerCropM));
                textFertilizing.setText(updateFertilizingInfo(cropM, farmerCropM));
                scheduleNextFertilization(cropM, farmerCropM);
            }
        });

        card_pruning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), Pruning.class);
                intent.putExtra("USER", farmerCropM.getFarmer_user_name());
                intent.putExtra("ID", cropM.getCrop_ID());
                startActivity(intent);
            }
        });

        card_fertilizing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), FertilizingDetailActivity.class);
                intent.putExtra("USER", farmerCropM.getFarmer_user_name());
                intent.putExtra("ID", cropM.getCrop_ID());
                startActivity(intent);
            }
        });

        PeriodicWorkRequest fertilizerWork =
                new PeriodicWorkRequest.Builder(
                        FertilizerWorker.class,
                        5, TimeUnit.HOURS,
                        1, TimeUnit.HOURS
                ).build();


        WorkManager.getInstance(requireContext()).enqueue(fertilizerWork);
        return view;
    }

    private void setStepDone(ImageView icon) {
        icon.setImageResource(R.drawable.check1);
        icon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
        icon.setEnabled(false);
    }

    //    private void markAsDone(ImageView icon) {
//        icon.setEnabled(false);
//        icon.setImageResource(R.drawable.check1);
//        icon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
//    }
    private void updateRoom(String step, boolean value) {
        switch (step) {
            case "Soil":
                farmerCropM.setSoil(value);
                break;
            case "Agriculture":
                farmerCropM.setAgriculture(value);
                break;
            case "Care":
                farmerCropM.setCare(value);
                break;
            case "Done":
                farmerCropM.setDone(value);
                break;
        }
        new Thread(() -> {
            myViewModel.updateCropFarmer(farmerCropM);
        }).start();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);

        myViewModel.getAllCropsById(cropId).observe(getViewLifecycleOwner(), crops -> {
            if (crops != null && !crops.isEmpty()) {
                cropM = crops.get(0);

                myViewModel.getFarmersByCropAndFarmer(farmerUsername, cropId).observe(getViewLifecycleOwner(), farmerCrops -> {
                    if (farmerCrops != null && !farmerCrops.isEmpty()) {
                        farmerCropM = (Farmer_Crops) farmerCrops.get(0);

                        requireActivity().runOnUiThread(() -> {
                            if (farmerCropM.isSoil()) {
                                setStepDone(icon1);
                                icon2.setEnabled(true);
                                icon2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                                textSoilComparison.setVisibility(VISIBLE);
                                textSeedlingsCount.setVisibility(GONE);
                                textSeedlingsCount.setVisibility(GONE);
                            }
                            if (farmerCropM.isAgriculture()) {
                                setStepDone(icon2);
                                icon3.setEnabled(true);
                                icon3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                                textSoilComparison.setVisibility(GONE);
                                textSeedlingsCount.setVisibility(VISIBLE);
                                textSeedlingsCount.setVisibility(VISIBLE);
                            }
                            if (farmerCropM.isCare()) {
                                setStepDone(icon3);
                                icon4.setEnabled(true);
                                icon4.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));


                            }
                            if (farmerCropM.isDone()) {
                                setStepDone(icon4);
                            }

                            icon1.setOnClickListener(v -> {
                                setStepDone(icon1);
                                icon2.setEnabled(true);
                                line1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
                                icon2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                                updateRoom("Soil", true);
                            });

                            icon2.setOnClickListener(v -> {
                                setStepDone(icon2);
                                icon3.setEnabled(true);
                                line2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
                                icon3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                                updateRoom("Agriculture", true);
                            });

                            icon3.setOnClickListener(v -> {
                                setStepDone(icon3);
                                icon4.setEnabled(true);
                                line3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
                                icon4.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                                updateRoom("Care", true);
                            });

                            icon4.setOnClickListener(v -> {
                                setStepDone(icon4);
                                updateRoom("Done", true);
                            });

//                            if (farmerCropM.getSoil()) {
//                                Toast.makeText(requireContext(), "true", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(requireContext(), "false", Toast.LENGTH_SHORT).show();
//                            }

                            updateUI(cropM, farmerCropM);

                            //textFertilizerInfo.setText(updateFertilizerInfo(cropM, farmerCropM));
                            textFertilizing.setText(updateFertilizingInfo(cropM, farmerCropM));
                            scheduleNextFertilization(cropM, farmerCropM);
                        });
                    }
                });
            }
        });


    }


    private void updateUI(Crop crop, Farmer_Crops farmerCrop) {
        // Update all UI elements based on crop and farmer data

        updateSoilComparison(crop, farmerCrop);//بعد الانتهاء من هذه المهمة يحدد يوم للزراعة بناءا على ايام الطقس والتي تؤثر في متغير لعدد الايام بعد تحضير التربة لسا ما عرفت هالمتغير
        /*العرض مع التربة*/
        updateSeedlingsCount(crop, farmerCrop);//تحديد بذور او شتل وتحديد موعد الزراعة بناءا على الطقس
        /*لعرض اليوم المناسب للزراعة */
        // fetchWeatherData();// تحديد موعد الزراعة بناءا على بيانات الطقس اول مرة بس بستدعيها بعد م يحدد خيار التربة

        //  updateWateringInfo(crop, farmerCrop);//التحديث المستمر لتغير تردد الايام واظهار الاشعار

        updatePruningInfo(crop);//جدولة اشعارات في الخلفية لاضافة العناصر وذلك بالتعامل مع عدد مرات تقسم على عدد الايام حتى النضج عدد المرات تختلف من محصول لاخر وتوضيح الطريقة ايضا
        updatePestControlInfo(crop);// ايش بي اي يوضح الافة القادمة وبناءا على هيك بنقلو ايش يعمل
        updatePreviousCropInfo(crop, farmerCrop);//جدولة اشعارات في الخلفية لاضافة العناصر وذلك بالتعامل مع عدد مرات تقسم على عدد الايام حتى النضج عدد المرات تختلف من محصول لاخر وكذلك بنضيف متغير يشرح الكمية والنوع اي   ا يختلف من محصول لاخر
        //   updateFertilizingInfo(crop, farmerCrop);//التحديث المستمر لتغير تردد الايام واظهار الاشعار
        //   /*العرض مع التسميد*/ updateFertilizerInfo(crop, farmerCrop);//معلومات التسميد الثابتة
    }

    private void updateWateringInfo(Crop crop, Farmer_Crops farmerCrop) {
        int adjustedDays = getAdjustedWateringDays(crop, farmerCrop);
        String nextDate = getNextActionDate(farmerCrop.getLastWaterDate(), adjustedDays);

        String text = String.format(Locale.getDefault(),
                "تعليمات الري:\n%s\n\nالتكرار: كل %d يوم اذا كانت الظروف مثالية\nالموعد التالي: %s",
                crop.getWateringInstructions(), adjustedDays, nextDate);

        //  textWatering.setText(text);
        Farmer_Crops farmerCrops1 = new Farmer_Crops();
        farmerCrops1.setWateringFrequencyDays_F(adjustedDays);
        myViewModel.updateCropFarmer(farmerCrops1);
    }

    private int getAdjustedWateringDays(Crop crop, Farmer_Crops farmerCrop) {
        int baseDays = crop.getWateringFrequencyDays();
        if (baseDays <= 0) return 1;

        float adjustment = 1.0f;

        // عوامل الطقس
        adjustment *= getWeatherAdjustment(farmerCrop);

        // عوامل التربة والمحصول
        adjustment *= getSoilAndCropAdjustment(crop, farmerCrop);

        // تطبيق الحدود الدنيا والقصوى
        adjustment = Math.max(0.5f, Math.min(2.0f, adjustment));

        int result = Math.max(1, (int) (baseDays * adjustment));
        Log.d("WateringCalc", "Base: " + baseDays + ", Adjusted: " + result);
        return result;
    }

    private float getWeatherAdjustment(Farmer_Crops farmerCrop) {
        float adjustment = 1.0f;

        // رياح
        if (farmerCrop.getWindSpeed() > 25) adjustment *= 0.9f;

        // رطوبة
        if (farmerCrop.getHumidity() < 40) {
            adjustment *= 0.85f;
        } else if (farmerCrop.getHumidity() > 80) {
            adjustment *= 1.1f;
        }

        // حرارة
        float tempFactor = 1 - ((farmerCrop.getAverageTemperature() - 25) * 0.02f);
        adjustment *= tempFactor;

        return adjustment;
    }

    private float getSoilAndCropAdjustment(Crop crop, Farmer_Crops farmerCrop) {
        float adjustment = 1.0f;

        // تربة
        if (!crop.getPreferredSoil().equalsIgnoreCase(farmerCrop.getSoilType())) {
            if (farmerCrop.getSoilType().equalsIgnoreCase("رملية")) {
                adjustment *= 0.7f;
            } else if (farmerCrop.getSoilType().equalsIgnoreCase("طينية")) {
                adjustment *= 1.3f;
            }
        }

        // نوع المحصول
        if (crop.getCategorie().contains("خضروات ورقية")) {
            adjustment *= 0.9f;
        } else if (crop.getCategorie().contains("أشجار مثمرة")) {
            adjustment *= 1.5f;
        }

        return adjustment;
    }

    private String getNextActionDate(String lastDateString, int frequencyDays) {//بترجع التاؤيخ القادم للتسميد
        if (lastDateString == null || lastDateString.isEmpty()) {
            return "يتم التسميد لاول مرة بناءا على البيانات في الاسفل ";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date lastDate = sdf.parse(lastDateString);
            if (lastDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(lastDate);
                cal.add(Calendar.DAY_OF_YEAR, frequencyDays);
                farmerCropM.setNextFertilizingDate(cal.getTime());
                myViewModel.updateCropFarmer(farmerCropM);
                //الاضافة على الجدول
                return sdf.format(cal.getTime());
            }
        } catch (Exception e) {
            Log.e("DateCalc", "Error calculating next date", e);
        }
        return "غير محدد";
    }

    //
    private int getAdjustedFertilizingDays(Crop crop, Farmer_Crops farmerCrop) {
        int baseDays = crop.getFertilizingFrequencyDays();

//        if (farmerCrop.getCustomFertilizingDays() != null) {
//            return farmerCrop.getCustomFertilizingDays();
//        }

        float adjustment = 1.0f;
        if (farmerCrop.getSeason().equalsIgnoreCase("صيفي")) {
            adjustment *= 0.95f;
        } else if (farmerCrop.getSeason().equalsIgnoreCase("شتوي")) {
            adjustment *= 1.05f;
        }

        if (farmerCrop.getHumidity() > 80) {
            adjustment *= 1.1f;
        }


        if (farmerCrop.getWindSpeed() > 30) {
            adjustment *= 1.05f;
        }


        // Adjust based on soil type
        List<String> lessSuitableSoils = Arrays.asList("رملية","صخرية");
        if (!crop.getPreferredSoil().equalsIgnoreCase(farmerCrop.getSoilType()) &&
                lessSuitableSoils.contains(farmerCrop.getSoilType())) {
            adjustment *= 0.8f;
        }

        // Adjust based on crop growth stage
        long daysSincePlanting = getDaysSince(farmerCrop.getStartDate());
        if (daysSincePlanting > 0) {
            int maturity = crop.getDaysToMaturity();

            if (maturity > 0 && daysSincePlanting > 0) {
                float ratio = (float) daysSincePlanting / maturity;

                if (ratio < 0.33f) {
                    adjustment *= 0.8f; // مرحلة النمو الأولي
                } else if (ratio < 0.66f) {
                    adjustment *= 1.0f; // النمو النشط
                } else {
                    adjustment *= 1.2f; // الإثمار
                }
            }

        }


        return Math.max(1, (int) (baseDays * adjustment));
    }

    private long getDaysSince(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateString);
            if (date != null) {
                long diff = new Date().getTime() - date.getTime();
                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            Log.e("DateCalc", "Error calculating days", e);
        }
        return -1;
    }


    private void updatePruningInfo(Crop crop) {
        //    textPruning.setText(String.format("تعليمات التقليم:\n%s", crop.getPruning_and_guidance()));
    }

    private void updatePestControlInfo(Crop crop) {
        textPestControl.setText(String.format("مكافحة الآفات:\n%s", crop.getCropProblems()));
    }

    private void updateSoilComparison(Crop crop, Farmer_Crops farmerCrop) {

        // في حالة التربة المفضلة
        if (crop.getPreferredSoil().equals(farmerCrop.getSoilType())) {
            textSoilComparison.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bottom_nav_bg, 0, 0);
        }
// في حالة التربة المسموحة
        else if (crop.getAllowedSoil().equals(farmerCrop.getSoilType())) {
            textSoilComparison.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bottom_nav_bg, 0, 0);
        }
// في حالة التربة غير المناسبة
        else {
            textSoilComparison.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bottom_nav_bg, 0, 0);
        }

        // المعلومات الأساسية عن التربة
        String comparison = String.format("مقارنة التربة:\nالمفضلة: %s\nالحالية: %s",
                crop.getPreferredSoil(), farmerCrop.getSoilType());

        // تحديد نوع التربة وإضافة التعليمات المناسبة
        String soilPreparationInstructions = "";

        if (crop.getPreferredSoil().equalsIgnoreCase(farmerCrop.getSoilType())) {
            comparison += "\n\nالتربة مناسبة تماماً";
            soilPreparationInstructions = crop.getSoil_preparation_Favorite();
        } else if (crop.getAllowedSoil().toLowerCase().contains(farmerCrop.getSoilType().toLowerCase())) {
            comparison += "\n\nالتربة مقبولة ولكن ليست الأمثل";
            soilPreparationInstructions = crop.getSoil_preparation_allowed();
        } else {
            comparison += "\n\nتحذير: التربة غير مناسبة";
            soilPreparationInstructions = "يجب تحسين التربة قبل الزراعة حسب إرشادات الخبير سيتم تعويض ذلك بالاسمدة وزيادة الري واضافة عناصر للتربة";
        }

        // دمج المعلومات مع تعليمات التحضير
        String fullText = comparison + "\n\nتعليمات تحضير التربة:\n" + soilPreparationInstructions;

        textSoilComparison.setText(fullText);
    }

    //جدزلة اشعارات لاضافة عناصر وذلك من خلال عدة اشعارات يتم ارسالها خلال فترة من الزراعة وحتى النضج تقسم
    private void updatePreviousCropInfo(Crop crop, Farmer_Crops farmerCrop) {

        String info = String.format("المحصول السابق: %s", farmerCrop.getPrevious_crop());

        if (crop.getPrevious_crop_forbidden().toLowerCase().contains(farmerCrop.getPrevious_crop().toLowerCase())) {
            info += "\n\nغير مناسب! يحتاج إلى تسميد إضافي";
        } else if (crop.getPrevious_crop_allowed().toLowerCase().contains(farmerCrop.getPrevious_crop().toLowerCase())) {
            info += "\n\nمقبول ولكن يحتاج عناية خاصة";
        } else if (crop.getPrevious_crop_preferred().toLowerCase().contains(farmerCrop.getPrevious_crop().toLowerCase())) {
            info += "\n\nمثالي! لا يحتاج تسميد إضافي";
        }

        //    textPreviousCrop.setText(info);
    }

    private void updateSeedlingsCount(Crop crop, Farmer_Crops farmerCrop) {
        if (farmerCrop.getSelecting_seeds_or_seedlings().toString().equals("شتل")) {
            double count = crop.getNumber_Plant_per_dunum() * (farmerCrop.getLand_area() / 1000);
            String IrrigationType = "";
            if (farmerCrop.getIrrigationType().toString().equals(crop.getPreferredIrrigation().toString())) {
                IrrigationType = crop.getPreparing_irrigation_tools_P().toString();

            } else if (farmerCrop.getIrrigationType().toString().equals(crop.getAllowedIrrigation().toString())) {

                IrrigationType = crop.getPreparing_irrigation_tools_A().toString();
            } else if (farmerCrop.getIrrigationType().toString().equals(crop.getForbiddenIrrigation().toString())) {
                IrrigationType = crop.getPreparing_irrigation_tools_F().toString();

            }
            textSeedlingsCount.setText(String.format(Locale.getDefault(),
                    "عدد الشتلات الكلي: %.0f شتلة (%.1f دونم)",
                    count, farmerCrop.getLand_area()) + getCultivationGuide(farmerCrop, crop).toString() + IrrigationType);

        }
        if (farmerCrop.getSelecting_seeds_or_seedlings().toString().equals("بذور")) {
            double count = crop.getWeight_seeds_per_dunum() * (farmerCrop.getLand_area() / 1000);

            textSeedlingsCount.setText(String.format(Locale.getDefault(),
                    "وزن البذور الكلي: %.0f غرام (%.1f متر)",
                    count, farmerCrop.getLand_area()) + getCultivationGuide(farmerCrop, crop).toString());

        }
    }

    //+getCultivationGuide(farmerCrop, crop).toString()
    public String updateFertilizerInfo(Crop crop, Farmer_Crops farmerCrop) {
        StringBuilder info = new StringBuilder("معلومات التسميد:\n\n");
        double seedlingsCount = crop.getNumber_Plant_per_dunum() * (farmerCrop.getLand_area() / 1000);

        if (!farmerCrop.getOrganicFertilizer().equals("لا شيئ")) {
            double amount = crop.getOrganicPerPlant() * seedlingsCount;
            info.append(String.format(Locale.getDefault(),
                    "العضوي: %s\nالكمية: %.1f غم (%.1f غم/شتلة)\n\n",
                    crop.getOrganicFertilizer(), amount, crop.getOrganicPerPlant()));
        }

        if (farmerCrop.getChemicalFertilizer().equals("لا شيئ")) {
            double amount = crop.getChemicalPerPlant() * seedlingsCount;
            if (farmerCrop.getOrganicFertilizer().equals("لا شيئ")) {
                amount *= 0.7; // تخفيض الجرعة إذا كان يستخدم النوعين
                info.append("ملاحظة: تم تخفيض جرعة الكيماوي 30% لوجود سماد عضوي\n");
            }
            info.append(String.format(Locale.getDefault(),
                    "الكيماوي: %s\nالكمية: %.1f غم (%.1f غم/شتلة)",
                    crop.getChemicalFertilizer(), amount, crop.getChemicalPerPlant()));
        }

        if (farmerCrop.getOrganicFertilizer().equals("لا شيئ") && farmerCrop.getChemicalFertilizer().equals("لا شيئ")) {
            info.append("لم يتم تحديد نوع السماد");
        }
        Farmer_Crops farmerCrops = new Farmer_Crops();
        farmerCrops.setFertilizerInfo(info.toString());
        myViewModel.updateCropFarmer(farmerCrops);
        return info.toString();

    }

    public String getCultivationGuide(Farmer_Crops farmerCrop, Crop crop) {
        // 1. اختيار النوع الأنسب بناءً على درجة الحرارة
        String selectedVariety = getTemperatureBasedRecommendation(crop, farmerCrop);

        // 2. الحصول على التفاصيل الثابتة للزراعة
        String cultivationDetails = getCultivationDetails(crop, selectedVariety);

        // 3. تحديد موعد الزراعة بناءً على الطقس
        //  String plantingTime = calculatePlantingTime(crop, farmerCrop);

        //     double totalAmount = calculateFertilizerAmount(crop, farmerCrop);

        // دمج النتائج في تقرير متكامل
        //  createFinalGuide(selectedVariety, cultivationDetails, plantingTime);
        return "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
                "✓ النوع المختار: " + selectedVariety + "\n\n" +
                "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
                cultivationDetails + "\n\n" +
                "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
                "✓ توقيت الزراعة:\n" /*+ plantingTime + "\n"*/
                ;
    }

    //المحصول الانسب اذا كان هناك اكثر من نوع
    public String getTemperatureBasedRecommendation(Crop crop, Farmer_Crops farmerData) {
        try {
            float farmerTemp = farmerData.getAverageTemperature();
            float cropTemp = crop.getOptimalTemperature();

            String recommendation;
            if (farmerTemp - cropTemp >= 3) {
                recommendation = crop.getHigh() != null ?
                        crop.getHigh() : crop.getMid();
            } else if (cropTemp - farmerTemp >= 3) {
                recommendation = crop.getLow() != null ?
                        crop.getLow() : crop.getMid();
            } else {
                recommendation = crop.getMid();
            }

            return String.format(Locale.getDefault(),
                    "توصية الزراعة:\n\n%s\n\n(درجة حرارة المزرعة: %.1f°م vs المثالية للمحصول: %.1f°م)",
                    recommendation, farmerTemp, cropTemp);

        } catch (Exception e) {
            Log.e("TempRecommendation", "Error generating recommendation", e);
            return "تعذر توليد التوصية. يرجى التحقق من البيانات.";
        }
    }

    private String getCultivationDetails(Crop crop, String variety) {//جمع كل البيانات في متغير واحد يتم عرض هذا المتغير بناءا على بذور او شتل يعني في كروب متغيرين يكون لها الشي
        StringBuilder details = new StringBuilder();

        details.append("✧ مواصفات البذور/الشتلات:\n")
                .append(crop.getSeedSpecifications()).append("\n\n")
                .append("✧ تحضير الشتلات:\n")
                .append(crop.getSeedlingPreparation()).append("\n\n")
                .append("✧ الزراعة:\n")
                .append("- المسافة بين النباتات: ").append(crop.getPlantingDistance()).append(" سم\n")
                .append("- العمق: ").append(crop.getPlantingDepth()).append(" سم\n\n")
                .append("✧ الري الأول:\n")
                .append(crop.getInitialIrrigation());

        return details.toString();
    }


    private float calculateDeviationRatio(float current, float optimal, float tolerance) {
        return Math.abs(current - optimal) / tolerance;
    }


    //في مكان واحد فقط مش اثنين استخدامها
    private String calculatePlantingTime(Crop crop, Farmer_Crops farmerCrop) {//ازالتها من سطر 490
        float currentTemp = farmerCrop.getAverageTemperature();
        float currentHumidity = (float) farmerCrop.getAverage_humidity();
        float optimalTemp = crop.getOptimalTemperature();
        float optimalHumidity = crop.getOptimalHumidity();
        float tempTolerance = crop.getTemperatureTolerance();
        float humidityTolerance = crop.getHumidityTolerance();

        // حساب نسب الانحراف
        float tempDeviation = calculateDeviationRatio(currentTemp, optimalTemp, tempTolerance);
        float humidityDeviation = calculateDeviationRatio(currentHumidity, optimalHumidity, humidityTolerance);

        // تقييم الظروف
        return evaluateConditions(tempDeviation, humidityDeviation);
    }

    private String evaluateConditions(float tempDeviation, float humidityDeviation) {
        // تقييم الحرارة
        String tempStatus = evaluateParameter(tempDeviation, "الحرارة");

        // تقييم الرطوبة
        String humidityStatus = evaluateParameter(humidityDeviation, "الرطوبة");

        // التقييم الشامل
        if (tempDeviation <= 0.3f && humidityDeviation <= 0.3f) {
            return "الوقت المثالي للزراعة الآن (ظروف مثالية)";
        } else if (tempDeviation <= 0.7f && humidityDeviation <= 0.7f) {
            return "وقت مناسب للزراعة (ظروف جيدة)";
        } else {
            return "ظروف غير مناسبة للزراعة:\n" + tempStatus + "\n" + humidityStatus;
        }
    }


    private String evaluateParameter(float deviation, String parameterName) { //الانحراف
        if (deviation > 1.0f) {
            return parameterName + " خارج المدى المسموح";
        } else if (deviation > 0.7f) {
            return parameterName + " على الحدود القصوى";
        } else if (deviation > 0.3f) {
            return parameterName + " ضمن المدى المقبول";
        } else {
            return parameterName + " مثالية";
        }
    }

    private void fetchWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/") // لازم ينتهي بـ /
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi service = retrofit.create(WeatherApi.class);
        double latitude = farmerCropM.getLatitude();
        double longitude = farmerCropM.getLongitude();

        call1 = service.getSevenDayForecast(
                latitude,
                longitude,
                "minutely,hourly,current,alerts", // استثني التفاصيل اللي مش محتاجها
                "metric",
                "ar",
                API_KEY
        );

        call1.enqueue(new Callback<WeatherResponseOneCall>() {
            @Override
            public void onResponse(Call<WeatherResponseOneCall> call, Response<WeatherResponseOneCall> response) {
                if (response.isSuccessful()) {
                    List<WeatherResponseOneCall.Daily> dailyList = response.body().getDaily();

                    List<DailyWeather> sevenDaysList = new ArrayList<>();

                    for (int i = 0; i < 7 && i < dailyList.size(); i++) {
                        WeatherResponseOneCall.Daily day = dailyList.get(i);
                        float temp = day.getTemp().getDay();
                        float humidity = day.getHumidity();
                        Date date = new Date(day.getDt() * 1000L); // تحويل من Unix Time
                        sevenDaysList.add(new DailyWeather(date, temp, humidity));
                    }

                    WeatherForecast forecast = new WeatherForecast(sevenDaysList);
                    String result = calculatePlantingTimeWithForecast(cropM, farmerCropM, forecast);

                    Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show();
                    fetchWeatherData.setText(result); // عرض النتيجة في TextView
                } else {
                    Toast.makeText(requireContext(), "فشل في جلب البيانات: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseOneCall> call, Throwable t) {
                Log.e("WeatherAPI", "فشل الاتصال: " + t.getMessage());
                Toast.makeText(requireContext(), "فشل الاتصال: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String calculatePlantingTimeWithForecast(Crop crop, Farmer_Crops farmerCrop, WeatherForecast forecast) {
        StringBuilder advice = new StringBuilder(calculatePlantingTime(crop, farmerCrop));

        if (forecast != null) {
            List<DailyWeather> suitableDays = findSuitableDays(crop, forecast);

            if (!suitableDays.isEmpty()) {
                appendForecastAdvice(advice, suitableDays);
            } else {
                advice.append("\n\nلا توجد أيام مناسبة للزراعة خلال الأسبوع القادم");
            }
        }

        return advice.toString();
    }

    private List<DailyWeather> findSuitableDays(Crop crop, WeatherForecast forecast) {
        List<DailyWeather> suitableDays = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("ar"));

        for (DailyWeather day : forecast.getWeeklyForecast()) {
            float tempDiff = Math.abs(day.getTemperature() - crop.getOptimalTemperature());
            float humidityDiff = Math.abs(day.getHumidity() - crop.getOptimalHumidity());

            if (tempDiff <= crop.getTemperatureTolerance() &&
                    humidityDiff <= crop.getHumidityTolerance()) {
                suitableDays.add(day);
            }
        }

        return suitableDays;
    }

    private void appendForecastAdvice(StringBuilder advice, List<DailyWeather> suitableDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("ar"));

        advice.append("\n\nأيام مناسبة للزراعة خلال الأسبوع:");
        for (DailyWeather day : suitableDays) {
            advice.append("\n- ")
                    .append(sdf.format(day.getDate()))
                    .append(" (")
                    .append(String.format(Locale.getDefault(), "%.1f°C", day.getTemperature()))
                    .append(", ")
                    .append(String.format(Locale.getDefault(), "%.0f%%", day.getHumidity()))
                    .append(")");
        }

        if (!suitableDays.isEmpty()) {
            advice.append("\n\nنوصي بالزراعة في أقرب يوم مناسب");
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


//    private void scheduleFertilizingNotifications(Crop crop, Farmer_Crops farmerCrop) {/*حساب كل ايام التسميد معالجة التأخير التناوب*/
//        Context context = getContext();
//        if (context == null) return;
//        try {
//            int AdjustedFertilizingDays = getAdjustedFertilizingDays(crop, farmerCrop); // عدد الأيام بعد التعديل
//
//            // تحديد تاريخ البداية: إما آخر تسميد أو تاريخ الزراعة
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            Date startDate = (farmerCrop.getLastFertilizerDate() != null && !farmerCrop.getLastFertilizerDate().isEmpty())
//                    ? sdf.parse(farmerCrop.getLastFertilizerDate())
//                    : sdf.parse(farmerCrop.getStartDate());
//
//            if (startDate == null) return;
//
//            Calendar baseCalendar = Calendar.getInstance();
//            baseCalendar.setTime(startDate);
//
//            // معالجة التأخير إن وجد
//            if (startDate.before(new Date()) && !isFertilizationDone(farmerCrop)) {/*   الدالة بترجع هل اخر يوم تسميد هو اليوم يعني لو تاريخ البداية قبل اليوم يعني فات وتاريخ اليوم لا يساوي اخر يوم تسميد*/
//                sendDelayNotification(crop);
//                long daysLate = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - startDate.getTime());
//                baseCalendar.add(Calendar.DAY_OF_YEAR, (int) daysLate);//اذا تأخر بيبدأمن اليوم
//            } else {
//                baseCalendar.setTime(new Date()); // نبدأ من اليوم اذا اليوم اخر يوم تسميد
//            }
//
//            for (int i = 0; i < crop.getDaysToMaturity() / crop.getFertilizingFrequencyDays(); i++) {/* عمل لوب على ايام التسميد ككل بناءا على الفترة الكلية حتى النضج ومعرفة تاريخ كل عملية تسميد لارسال اشعار*/
//                Calendar eventCalendar = (Calendar) baseCalendar.clone();
//                eventCalendar.add(Calendar.DAY_OF_YEAR, AdjustedFertilizingDays * (i + 1));
//
//                if (eventCalendar.after(Calendar.getInstance())) {//
//                    boolean isOrganic = (i % 2 == 0); // تناوب بين عضوي وكيميائي الطريقة غير فعالة وغير منطقية لازم اعرف متغير يدل على فترة او مرات للعضوي وكذلك متغير اخر للكيماوي اذا الاثنين مع بعض يقسمهم ع2
//                    scheduleNotification(
//                            crop.getCrop_NAME(),
//                            isOrganic ? crop.getOrganicFertilizer() : crop.getChemicalFertilizer(),
//                            isOrganic ? FertilizerType.ORGANIC : FertilizerType.CHEMICAL,
//                            isOrganic ? crop.getOrganicPerPlant() : crop.getChemicalPerPlant(),
//                            eventCalendar.getTimeInMillis(),
//                            i
//                    );
//                }
//            }
//
//            // تنبيه التسميد القادم فقط (أول إشعار)
//            Calendar nextFertilizingDate = Calendar.getInstance();
//            nextFertilizingDate.setTime(new Date());//التسميد التالي بعطيه قيمة التاريخ الحالية
//            nextFertilizingDate.add(Calendar.DAY_OF_YEAR, AdjustedFertilizingDays);//وبضيف عدد الايام على القيمة الحالية
//
//
//        } catch (Exception e) {
//            Log.e("Notification", "Error scheduling", e);
//        }
//    }
//
//
//    private boolean isFertilizationDone(Farmer_Crops farmerCrop) {//التاريخ الحالي هل يساوي اخر يوم تسميد يعني اليوم سمد؟
//        if (farmerCrop == null) return false;
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            Date lastFertDate = sdf.parse(farmerCrop.getLastFertilizerDate());
//            if (lastFertDate == null) {
//                lastFertDate = sdf.parse(farmerCrop.getStartDate());
//            }
//
//            Calendar cal1 = Calendar.getInstance();
//            cal1.setTime(lastFertDate);
//            cal1.set(Calendar.HOUR_OF_DAY, 0);
//            cal1.set(Calendar.MINUTE, 0);
//            cal1.set(Calendar.SECOND, 0);
//            cal1.set(Calendar.MILLISECOND, 0);
//
//            Calendar cal2 = Calendar.getInstance(); // اليوم الحالي
//            cal2.set(Calendar.HOUR_OF_DAY, 0);
//            cal2.set(Calendar.MINUTE, 0);
//            cal2.set(Calendar.SECOND, 0);
//            cal2.set(Calendar.MILLISECOND, 0);
//
//            return cal1.equals(cal2);
//        } catch (Exception e) {
//            Log.e("DateCheck", "Error parsing dates", e);
//            return false;
//        }
//    }


    @SuppressLint("MissingPermission")
    private void sendDelayNotification(Crop crop) {//اسرال اشعار عند التاخير
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    1001
            );
            Toast.makeText(requireContext(),
                    "الإشعارات ضرورية لتذكيرك بمواعيد التسميد",
                    Toast.LENGTH_LONG
            ).show();
            return;

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.unnamed)
                .setContentTitle("تأخر في التسميد!")
                .setContentText("لم يتم تأكيد تسميد " + crop.getCrop_NAME())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat.from(requireContext()).notify(100, builder.build());
    }


    private void scheduleNotification(String cropName, String fertilizerName,
                                      FertilizerType type, double amount,
                                      long triggerTime, int notificationId) {//التحقق من الصلاحية وانشاء نوتوفيكيشن التسميد واعطائه قيم وارساله في الخلفية وتشغبل منبه
        // التحقق من صلاحية SCHEDULE_EXACT_ALARM لأندرويد 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                // إذا لم تكن الصلاحية متوفرة، اطلبها من المستخدم
                requestExactAlarmPermission();
                return;
            }
        }
        // التحقق من صلاحية الإشعارات لأندرويد 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            return;
        }

        Intent intent = new Intent(requireContext(), NotificationReceiver.class);
        intent.putExtra("cropName", cropName);
        intent.putExtra("fertilizerName", fertilizerName);
        intent.putExtra("type", type.toString());
        intent.putExtra("amount", amount);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // استخدام AlarmManager لجدولة الإشعار
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,/*المنبه حتى لو الجهاز نايم*/
                    triggerTime,/*وقت العملية */
                    pendingIntent/*العملية الي هتتنفذ*/
            );

        } else {
            alarmManager.setExact(/*للاصدارا القديمة جدا */
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
            );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestExactAlarmPermission() {
        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
        intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
        startActivity(intent);

        // يمكنك إضافة Snackbar لإعلام المستخدم
        Snackbar.make(requireView(),
                        "يحتاج التطبيق إلى صلاحية جدولة المنبهات الدقيقة لإرسال التذكيرات",
                        Snackbar.LENGTH_LONG)
                .setAction("فتح الإعدادات", v -> {
                    startActivity(intent);
                })
                .show();
    }


    // private void handleFertilizationCompleted() {
    // 1. عرض تأكيد للمستخدم
//        if (farmerCropM.getFertilizingFrequencyDays_F() == 0 || (farmerCropM.getFertilizingFrequencyDays_F() + "").isEmpty()) {
//            Snackbar.make(requireView(), "لقد تم ضبط مواعيد التسميد بنجاح", Snackbar.LENGTH_SHORT).show();
//        } else {
//            Snackbar.make(requireView(), "تم تسجيل التسميد بنجاح", Snackbar.LENGTH_SHORT).show();
//        }
//        // 2. تحديث تاريخ التسميد الأخير
//        myViewModel.getFarmersByCropAndFarmer(farmerUsername, cropId).observe(getViewLifecycleOwner(), farmerCrops -> {
//            if (farmerCrops != null && !farmerCrops.isEmpty()) {
//                Farmer_Crops currentFarmerCrop = farmerCrops.get(0);
//
//                // تحديث تاريخ التسميد
//                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                currentFarmerCrop.setLastFertilizerDate(currentDate);
//                myViewModel.updateCropFarmer(currentFarmerCrop);
//                updateFertilizerInfo(cropM, farmerCropM);
//                // 3. جدولة الإشعارات للتسميد القادم
//                scheduleNextFertilization(cropM, currentFarmerCrop);
//
//                // 4. تحديث واجهة المستخدم
//                updateFertilizingInfo(cropM, currentFarmerCrop);
//
//            }
//        });
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        farmerCropM.setLastFertilizerDate(currentDate);
//        myViewModel.updateCropFarmer(farmerCropM);
    //  updateFertilizerInfo(cropM, farmerCropM);
    // 3. جدولة الإشعارات للتسميد القادم
    //scheduleNextFertilization(cropM, farmerCropM);

    // 4. تحديث واجهة المستخدم
    // updateFertilizingInfo(cropM, farmerCropM);
    //  }


    private void scheduleNextFertilization(Crop crop, Farmer_Crops farmerCrop) {
        if (crop == null || farmerCrop == null) {
            Log.e("Schedule", "Crop or FarmerCrop is null");
            return;
        }

        int adjustedDays = getAdjustedFertilizingDays(crop, farmerCrop);
        String lastFertDateStr = farmerCrop.getLastFertilizerDate();

        if (lastFertDateStr == null || lastFertDateStr.isEmpty()) {
            lastFertDateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date lastFertDate = sdf.parse(lastFertDateStr);

            if (lastFertDate != null) {
                Calendar nextFertCalendar = Calendar.getInstance();
                nextFertCalendar.setTime(lastFertDate);
                nextFertCalendar.add(Calendar.DAY_OF_YEAR, adjustedDays);

                cancelAllFertilizationNotifications();

                boolean isOrganic = true;
                scheduleNotification(
                        crop.getCrop_NAME(),
                        isOrganic ? crop.getOrganicFertilizer() : crop.getChemicalFertilizer(),
                        isOrganic ? FertilizerType.ORGANIC : FertilizerType.CHEMICAL,
                        isOrganic ? crop.getOrganicPerPlant() : crop.getChemicalPerPlant(),
                        nextFertCalendar.getTimeInMillis(),
                        0
                        //تمكين العملية بحساب الوقت المنقضي بناءا على تاريخ التسجيل والتاريخ الحالي وبدئ الحساب والعد في حال اشتغال التطبيق


                );
            }
        } catch (ParseException e) {
            Log.e("Fertilization", "Error parsing date: " + lastFertDateStr, e);
        }
    }

    private void cancelAllFertilizationNotifications() {
        // إلغاء جميع الإشعارات السابقة
        NotificationManagerCompat.from(requireContext()).cancelAll();

        // إلغاء جميع التنبيهات في AlarmManager
        Intent intent = new Intent(requireContext(), NotificationReceiver.class);
        PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        ).cancel();
    }

    public String updateFertilizingInfo(Crop crop, Farmer_Crops farmerCrop) {
        int adjustedDays = getAdjustedFertilizingDays(crop, farmerCrop);
        String nextDate = getNextActionDate(farmerCrop.getLastFertilizerDate(), adjustedDays);

        // حساب الوقت المتبقي حتى التسميد القادم
        long remainingMillis = calculateRemainingTime(farmerCrop.getLastFertilizerDate(), adjustedDays);

        // بدء العداد التنازلي
        startCountdown(remainingMillis);

        String text = String.format(Locale.getDefault(),
                "تعليمات التسميد:\n%s\n\nالتكرار: كل %d يوم\nالموعد التالي: %s",
                crop.getFertilizingInstructions(), adjustedDays, nextDate);

        Farmer_Crops farmerCrops = new Farmer_Crops();
        farmerCrops.setFertilizingInfo(text);

        myViewModel.updateCropFarmer(farmerCrops);
        return "Fertilizing";
    }

    private long calculateRemainingTime(String lastDateString, int frequencyDays) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date lastDate = sdf.parse(lastDateString);

            if (lastDate != null) {
                Calendar nextDate = Calendar.getInstance();
                nextDate.setTime(lastDate);
                nextDate.add(Calendar.DAY_OF_YEAR, frequencyDays);

                return nextDate.getTimeInMillis() - System.currentTimeMillis();
            }
        } catch (Exception e) {
            Log.e("DateCalc", "Error calculating remaining time", e);
        }
        return 0;
    }

    private void startCountdown(long millisInFuture) {
        cancelCountdown();

        countDownTimer = new CountDownTimer(millisInFuture, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);

            }

            @Override
            public void onFinish() {
                fertCheckbox.setEnabled(true);
                fertCheckbox.setVisibility(VISIBLE);
                clock_f.setVisibility(GONE);

            }

            // التنفيذ
        }.start();

        // تفعيل/تعطيل CheckBox بناءً على الوقت المتبقي
        fertCheckbox.setEnabled(millisInFuture <= 0);
    }

    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (call1 != null) call1.cancel();

        cancelCountdown();
    }


    private void updateCountdownText(long millisUntilFinished) {

        if (millisUntilFinished <= 0) {
            fertCheckbox.setEnabled(true);
            fertCheckbox.setVisibility(VISIBLE);
            clock_f.setVisibility(GONE);
            textCountdown.setVisibility(GONE);


            return;
        }
        long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
        //  long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

        String countdownText = String.format(Locale.getDefault(),
                "%d %02d:%02d",
                days, hours, minutes);
        textCountdown.setVisibility(VISIBLE);

        textCountdown.setText(countdownText);
        fertCheckbox.setEnabled(false);
        fertCheckbox.setVisibility(GONE);
        clock_f.setVisibility(VISIBLE);


    }
    //        btnSchedule.setOnClickListener(v -> scheduleFertilizingForAllCrops());

//        fertCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                Snackbar.make(requireView(),
//                        "تم تسجيل التسميد بنجاح",
//                        Snackbar.LENGTH_SHORT
//                ).show();
//                myViewModel.getFarmersByCropAndFarmer(farmerUsername, cropId).observe(getViewLifecycleOwner(), farmerCrops -> {
//
//                    farmerCrops.get(0).setLastFertilizerDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    myViewModel.updateCropFarmer(farmerCrops.get(0)); // حفظ في قاعدة البيانات
//                   // scheduleFertilizingForAllCrops();
//                    updateFertilizingInfo(cropM, farmerCrops.get(0));
//                  //  updateUI(cropM, farmerCrops.get(0)); // إضافة هذه السطر
//
//                });
//            }
//        });

//        btnConfirmFertilizing.setOnClickListener(v -> {
//            farmerCrop.setLastFertilizerDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            myViewModel.updateFarmerCrop(farmerCrop); // حفظ التاريخ في قاعدة البيانات
//            scheduleFertilizingNotifications(crop, farmerCrop); // إعادة الجدولة
//        });
    // في onCreateView():

    //        progressBar.setProgress(70);
//        progressBar.setMax(100);


//        if (farmerCropM.getSoil()==true){
//
//            icon1.setEnabled(false);
//            icon1.setImageResource(R.drawable.check1);
//        }
//        if (farmerCropM.getSoil()==true){
//
//            icon1.setEnabled(false);
//            icon1.setImageResource(R.drawable.check1);
//        }
//
//
//        icon1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                icon1.setEnabled(false);
//                icon1.setImageResource(R.drawable.check1);
//           //     icon1.setBackgroundColor(Color.GRAY);
//                farmerCropM.setSoil(true);
//                myViewModel.updateCropFarmer(farmerCropM);
//            }
//        });
    //  private void updateFertilizingInfo(Crop crop, Farmer_Crops farmerCrop) {
//        int adjustedDays = getAdjustedFertilizingDays(crop, farmerCrop);// عدد الايام بعد العوامل المؤثرة بس المقارنة لسبع ايام المفترض بدي اعمل عداد تنازلي ينقص بالثواني والدقائق والساعاتو والايام يبدأ ينقص من الايام المتبقية وكسورها
//        String nextDate = getNextActionDate(farmerCrop.getLastFertilizerDate(), adjustedDays);
//
//        String text = String.format(Locale.getDefault(),
//                "تعليمات التسميد:\n%s\n\nالتكرار: كل %d يوم\nالموعد التالي: %s",
//                crop.getFertilizingInstructions(), adjustedDays, nextDate);
//
//        textFertilizing.setText(text);//مش متأكد اذا يبدأ العد من القيمة للتردد بعد الحساب ولا الاصلية
//
//    / /        Farmer_Crops farmerCrops1=new Farmer_Crops();
//    / /        farmerCrops1.setWateringFrequencyDays_F(adjustedDays);
//    / /        myViewModel.updateCropFarmer(farmerCrops1);
//    }
//    private void showScheduleConfirmation() {/*ببعث اشعار لما بضغط جدولة*/
//        Context context = requireContext();
//
//        // تحقق من صلاحية الإشعارات (Android 13+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
//                        != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1002);
//            return;
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.unnamed)
//                .setContentTitle("تم جدولة التسميد")
//                .setContentText("تم جدولة جميع مواعيد التسميد للمحاصيل")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat.from(context).notify(0, builder.build());
//    }
    //    private void scheduleFertilizingForAllCrops() {
//        myViewModel.getCropsByFarmer(farmerUsername).observe(getViewLifecycleOwner(), farmerCrops -> {
//            for (Farmer_Crops farmerCrop : farmerCrops) {
//                myViewModel.getAllCropsById(farmerCrop.getCrop_ID()).observe(getViewLifecycleOwner(), crops -> {
//                    if (crops != null && !crops.isEmpty()) {
//                        Crop crop = crops.get(0);
//                        scheduleFertilizingNotifications(crop, farmerCrop);
//                    }
//                });
//            }
//            showScheduleConfirmation();
//        });
//    }

//    private void scheduleFallbackNotification(long triggerTime) {
//        // بديل عند عدم وجود صلاحية: استخدام AlarmManager غير الدقيق
//        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
//
//        // أو استخدام WorkManager كبديل
//        PeriodicWorkRequest fertilizerWork = new PeriodicWorkRequest.Builder(
//                FertilizerWorker.class,
//                adjustedDays, TimeUnit.DAYS)
//                .build();
//        WorkManager.getInstance(requireContext()).enqueue(fertilizerWork);
//    }

//    private double calculateFertilizerAmount(double amountPerPlant, double totalPlants) {
//        return amountPerPlant * totalPlants;
//    }
    //    private void setupFertilizingSchedule(Crop crop, Farmer_Crops farmerCrop) {
//        // التحقق من صحة المدخلات
//        if (crop == null || farmerCrop == null) {
//            Log.w("FertilizingSchedule", "بيانات المحصول أو المزارع فارغة");
//            throw new IllegalArgumentException("بيانات المحصول والمزارع مطلوبة");
//        }
//
//        // التحقق من تاريخ البدء
//        if (Strings.isNullOrEmpty(farmerCrop.getStartDate())) {
//            Log.w("FertilizingSchedule", "لا يوجد تاريخ بدء متاح للمحصول: " + crop.getCrop_NAME());
//            return;
//        }
//
//        try {
//            // إنشاء جدول التسميد
//            List<FertilizingEvent> schedule = null;
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                schedule = generateFertilizingSchedule(crop, farmerCrop);
//            }
//
//            // التحقق من وجود مواعيد في الجدول
//            if (schedule.isEmpty()) {
//                Log.i("FertilizingSchedule", "لا توجد مواعيد تسميد مستقبلية للمحصول: " + crop.getCrop_NAME());
//                return;
//            }
//
//            // جدولة الإشعارات
//            scheduleNotifications(schedule);
//
//            // تسجيل نجاح العملية
//            Log.d("FertilizingSchedule", "تم جدولة " + schedule.size() + " موعد تسميد للمحصول: " + crop.getCrop_NAME());
//
//        } catch (Exception e) {
//            Log.e("FertilizingSchedule", "فشل في إنشاء جدول التسميد للمحصول: " + crop.getCrop_NAME(), e);
//            // يمكن هنا إضافة إشعار للمستخدم عن الفشل
//        }
//    }

//
//    private void setupOrganicEvent(FertilizingEvent event, Crop crop, double totalPlants) {
//        event.setType(FertilizerType.ORGANIC);
//        event.setFertilizerName(crop.getOrganicFertilizer());
//        event.setAmount(calculateFertilizerAmount(
//                crop.getOrganicPerPlant(),
//                totalPlants
//        ));
//    }
//
//    private void setupChemicalEvent(FertilizingEvent event, Crop crop, double totalPlants) {
//        event.setType(FertilizerType.CHEMICAL);
//        event.setFertilizerName(crop.getChemicalFertilizer());
//        event.setAmount(calculateFertilizerAmount(
//                crop.getChemicalPerPlant() * 0.7,
//                totalPlants
//        ));
//    }


//    /**
//     * تقوم بإعداد جدول التسميد وجدولة الإشعارات المناسبة
//     *
//     * @param crop       بيانات المحصول الزراعي
//     * @param farmerCrop بيانات زراعة المحصول الخاصة بالمزارع
//     * @throws IllegalArgumentException إذا كانت بيانات المدخلات غير صالحة
//     */
//
//    //يتم تطبيقها كل 5 ساعات في الخلفية مش بس لما افتح الواجهة
//
//
//


//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private List<FertilizingEvent> generateFertilizingSchedule(Crop crop, Farmer_Crops farmerCrop) {//هذي الدالة الها بديل وهو scheduleFertilizingNotifications
//        int times = crop.getDaysToMaturity()/crop.getFertilizingFrequencyDays();
//        List<FertilizingEvent> schedule = new ArrayList<>(); //عدد الايام حتى النضج ناقص التردد لتسميد هذي في احسن الظروف وممكن تتغير
//
//        if (farmerCrop.getStartDate() == null || farmerCrop.getStartDate().isEmpty()) {
//            return schedule;
//        }
//
//        try {
//            LocalDate startDate = LocalDate.parse(farmerCrop.getStartDate());
//            LocalDate today = LocalDate.now();
//
//            double totalPlants = crop.getNumber_Plant_per_dunum() * farmerCrop.getLand_area();
//
//            for (int i = 0; i < times; i++) {
//                int adjustedDays = getAdjustedFertilizingDays(crop, farmerCrop);//عدد ايام تكرار كل عملية تسميد بعد معاينة الظروف
//                startDate = startDate.plusDays(adjustedDays);
//
//                if (startDate.isBefore(today)) {
//                    continue; // تخطي التواريخ الماضية
//                }
//
//                FertilizingEvent event = new FertilizingEvent();
//                event.setDate(startDate.toString());
//                event.setCropName(crop.getCrop_NAME());
//                event.setAmount(totalPlants);
//                event.setAmount(totalPlants);
//
//                if (i % 2 == 0) {
//                    setupOrganicEvent(event, crop, totalPlants);
////                    event.setType(FertilizerType.ORGANIC);
////                    event.setFertilizerName(farmerCrop.getOrganicFertilizer());
//                } else {
//                    setupChemicalEvent(event, crop, totalPlants);
////                    event.setType(FertilizerType.CHEMICAL);
////                    event.setFertilizerName(farmerCrop.getChemicalFertilizer());
//                }
//
//                schedule.add(event);
//            }
//        } catch (@SuppressLint({"NewApi", "LocalSuppress"}) DateTimeParseException e) {
//            Log.e("Schedule", "Error parsing date: " + farmerCrop.getStartDate(), e);
//        } catch (Exception e) {
//            Log.e("Schedule", "Unexpected error generating schedule", e);
//        }
//
//        return schedule;
//    }


//    private void scheduleNotifications(List<FertilizingEvent> events) {
//        for (FertilizingEvent event : events) {
//            sendFertilizerNotification(event);
//        }
//    }
//
//


//    private void sendFertilizerNotification(FertilizingEvent event) {//تشغيل الدالة في On Create
//        Context context = requireContext();
//
//        // تحقق من صلاحية الإشعارات (مطلوبة فقط من Android 13 وأعلى)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
//                        != PackageManager.PERMISSION_GRANTED) {
//            // الإذن غير ممنوح → لا ترسل الإشعار
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
//            return;
//        }
//
//        Intent intent = new Intent(context, HomeFragment.class);
//        intent.putExtra("fragment", "fertilizing");
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                event.hashCode(),
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.fertilizer)
//                .setContentTitle("موعد تسميد " + event.getCropName())
//                .setContentText("نوع السماد: " + event.getFertilizerName())
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("تفاصيل التسميد:\n" +
//                                "• المحصول: " + event.getCropName() + "\n" +
//                                "• النوع: " + event.getType().toString() + "\n" +
//                                "• السماد: " + event.getFertilizerName() + "\n" +
//                                "• الكمية: " + String.format(Locale.getDefault(), "%.1f", event.getAmount()) + " غم\n" +
//                                "• التاريخ: " + event.getDate()))
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            Date date = sdf.parse(event.getDate());
//            if (date != null) {
//                long offset = (event.getType() == FertilizerType.CHEMICAL) ? 2 : 1;
//                long triggerTime = date.getTime() - TimeUnit.DAYS.toMillis(offset);
//
//                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
//                manager.notify(event.hashCode(), notification);
//            }
//        } catch (Exception e) {
//            Log.e("Notification", "Error scheduling notification", e);
//        }
//    }
//
//
//
//
//


//    private void checkNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//                ContextCompat.checkSelfPermission(requireContext(),
//                        Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//
//            // شرح أهمية الإذن للمستخدم
//            new AlertDialog.Builder(requireContext())
//                    .setTitle("إذن الإشعارات")
//                    .setMessage("الإشعارات ضرورية لتذكيرك بمواعيد التسميد")
//                    .setPositiveButton("موافق", (d, w) ->
//                            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001))
//                    .show();
//        }
//    }
//
//
//
//


//    private void scheduleFertilizingNotifications(Crop crop, Farmer_Crops farmerCrop) {
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            Calendar calendar = Calendar.getInstance();
//
//            // تحديد تاريخ البدء (آخر تسميد أو تاريخ الزراعة)
//            Date startDate = farmerCrop.getLastFertilizerDate() != null && !farmerCrop.getLastFertilizerDate().isEmpty() ?
//                    sdf.parse(farmerCrop.getLastFertilizerDate()) :
//                    sdf.parse(farmerCrop.getStartDate());
//
//            if (startDate == null) return;
//
//            calendar.setTime(startDate);
//
//            // التحقق من التأخير
//            if (startDate.before(new Date()) && !isFertilizationDone(farmerCrop)) {
//                sendDelayNotification(crop);
//                long daysLate = TimeUnit.MILLISECONDS.toDays(
//                        new Date().getTime() - startDate.getTime()
//                );
//                calendar.add(Calendar.DAY_OF_YEAR, (int) daysLate);
//            } else {
//                calendar.setTime(new Date());
//            }
//
//            // جدولة المواعيد القادمة
//            for (int i = 0; i < 12; i++) {
//                int daysToAdd = getAdjustedFertilizingDays(crop, farmerCrop) * (i + 1);
//                Calendar eventCalendar = (Calendar) calendar.clone();
//                eventCalendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
//
//                if (eventCalendar.after(Calendar.getInstance())) {
//                    scheduleNotification(
//                            crop.getCrop_NAME(),
//                            (i % 2 == 0) ? crop.getOrganicFertilizer() : crop.getChemicalFertilizer(),
//                            (i % 2 == 0) ? FertilizerType.ORGANIC : FertilizerType.CHEMICAL,
//                            (i % 2 == 0) ? crop.getOrganicPerPlant() : crop.getChemicalPerPlant(),
//                            eventCalendar.getTimeInMillis(),
//                            i
//                    );
//                }
//            }
//        } catch (Exception e) {
//            Log.e("Notification", "Error scheduling", e);
//        }
//    }


//
//
//
//    // 1. أضف هذه الدالة لإعادة الجدولة بعد التأخير
//    private void rescheduleAfterDelay(Farmer_Crops farmerCrop, long daysLate) {
//        if(daysLate <= 0) return;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        try {
//            Date lastDate = sdf.parse(farmerCrop.getLastFertilizerDate());
//            Calendar newStart = Calendar.getInstance();
//            newStart.setTime(lastDate);
//            newStart.add(Calendar.DAY_OF_YEAR, (int) daysLate);
//
//            farmerCrop.setLastFertilizerDate(sdf.format(newStart.getTime()));
//            myViewModel.updateCropFarmer(farmerCrop);
//        } catch (Exception e) {
//            Log.e("Reschedule", "Error adjusting dates", e);
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private void showRescheduleNotification(long daysLate) {
//        Context context = getContext();
//        if (context == null) return;
//        createNotificationChannel();
//
//        String notificationText = String.format(Locale.getDefault(),
//                "تم تعديل جدول التسميد بعد تأخير %d يومًا. تمت إعادة جدولة جميع المواعيد تلقائيًا",
//                daysLate);
//
//        // 1. تحضير الـ PendingIntent لزر الإجراء
//        Intent intent = new Intent(requireContext(), DailyCareFragment.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//
//                requireContext() ,
//                0,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
//        );
//
//        // 2. بناء الإشعار مع إضافة الزر
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.unnamed)/*ic_fertilizer_notification*/
//                .setContentTitle("تم تعديل جدول التسميد")
//                .setContentText(notificationText)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
//                // إضافة زر الإجراء هنا
//                .addAction(
//
//                        R.drawable.ic_calendar, // أيقونة الزر
//                        "فتح الجدول", // نص الزر
//                        pendingIntent // الـ Intent المرتبط
//                );
//        builder.setLargeIcon(BitmapFactory.decodeResource(
//                getResources(),
//                R.drawable.unnamed
//        ));
//
//
//        // 3. إرسال الإشعار
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
//        if (ActivityCompat.checkSelfPermission(requireContext(),
//                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//            notificationManager.notify(200, builder.build());
//        }
//    }


//    @Test
//    public void testFertilizingSchedule() {
//        Crop testCrop = new Crop(...);
//        Farmer_Crops testFarmerCrop = new Farmer_Crops(...);
//
//        DailyCareFragment fragment = new DailyCareFragment();
//        List<FertilizingEvent> schedule = fragment.generateFertilizingSchedule(testCrop, testFarmerCrop);
//
//        assertFalse(schedule.isEmpty());
//        assertEquals(12, schedule.size());
//    }


//    private void handleDelay(Crop crop, Farmer_Crops farmerCrop) {
//        long daysLate = ChronoUnit.DAYS.between(
//                LocalDate.parse(farmerCrop.getLastFertilizerDate()),
//                LocalDate.now()
//        );
//
//        // يجب إضافة تحقق إذا كان daysLate موجباً فقط
//        if(daysLate > 0) {
//            sendDelayNotification(crop);
//            rescheduleAfterDelay(farmerCrop, daysLate);
//            showRescheduleNotification(daysLate);
//        }
//    }

    // 2. عدل دالة التأخير لتشمل إعادة الجدولة
//    private void handleDelay(Crop crop, Farmer_Crops farmerCrop) {
//        long daysLate = ChronoUnit.DAYS.between(
//                LocalDate.parse(farmerCrop.getLastFertilizerDate()),
//                LocalDate.now()
//        );
//
//        sendDelayNotification(crop);
//        rescheduleAfterDelay(farmerCrop, daysLate);
//
//        // أضف إشعارًا للمستخدم بالإعدادات الجديدة
//        showRescheduleNotification(daysLate);
//    }
    //    private String createFinalGuide(String variety, String details, String plantingTime) {
//        return "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
//                "✓ النوع المختار: " + variety + "\n\n" +
//                "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
//                details + "\n\n" +
//                "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯\n" +
//                "✓ توقيت الزراعة:\n" + plantingTime + "\n" +
//                "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯";
//    }

//    private String selectTemperatureBasedVariety(Crop crop, Farmer_Crops farmerCrop) {
//        if (crop == null || farmerCrop == null) {
//            return "لا توجد بيانات كافية";
//        }
//
//        // درجات الحرارة من الجدولين
//        float farmerAvgTemp = farmerCrop.getAverageTemperature();
//        float cropAvgTemp = crop.getOptimalTemperature();
//
//        // حساب الفرق بينهما
//        float tempDifference = farmerAvgTemp - cropAvgTemp;
//        float tolerance = 3.0f; // هامش ±3 درجات
//
//        // تحديد النص المناسب
//        if (tempDifference > tolerance) {
//            return crop.getHigh(); // للنصوص الأعلى من المعدل
//        } else if (tempDifference < -tolerance) {
//            return crop.getLow(); // للنصوص الأقل من المعدل
//        } else {
//            return crop.getMid(); // للنصوص المتوسطة
//        }
//    }


//       private int getAdjustedWateringDays(Crop crop, Farmer_Crops farmerCrop) {
//        int baseDays = crop.getWateringFrequencyDays();

    /// /           if (farmerCrop.getCustomWateringDays() != null) {
    /// /               return farmerCrop.getCustomWateringDays();
    /// /           }
//
//           float adjustment = 1.0f;
//
//           //بيانات حالية من الطقس شيرد بريفيرنس
//           if (farmerCrop.getWindSpeed() > 25) {
//               adjustment *= 0.9f; // رياح قوية تؤدي لجفاف أسرع
//           }
//
//        if (farmerCrop.getHumidity() < 40) {
//            adjustment *= 0.85f; // الجو جاف، نزيد الري
//        } else if (farmerCrop.getHumidity() > 80) {
//            adjustment *= 1.1f; // الجو رطب، نقلل الري
//        }
//
//
//        if (farmerCrop.getSeason().equalsIgnoreCase("صيفي")) {
//            adjustment *= 0.9f; // في الصيف نروي أكثر
//        } else if (farmerCrop.getSeason().equalsIgnoreCase("شتوي")) {
//            adjustment *= 1.1f; // في الشتاء نقلل الري
//        }
//
//
//        // Adjust based on soil type
//        if (!crop.getPreferredSoil().equalsIgnoreCase(farmerCrop.getSoilType())) {
//            if (farmerCrop.getSoilType().equalsIgnoreCase("رملية")) {
//                adjustment *= 0.7f;
//            } else if (farmerCrop.getSoilType().equalsIgnoreCase("طينية")) {
//                adjustment *= 1.3f;
//            }
//        }
//
//        // Adjust based on temperature
//        if (farmerCrop.getAverageTemperature() > 30) {
//            adjustment *= 0.8f;
//        } else if (farmerCrop.getAverageTemperature() < 15) {
//            adjustment *= 1.2f;
//        }
//
//        // Adjust based on crop category
//        if (crop.getCategorie().contains("خضروات ورقية")) {
//            adjustment *= 0.9f;
//        } else if (crop.getCategorie().contains("أشجار مثمرة")) {
//            adjustment *= 1.5f;
//        }
//
//        return Math.max(1, (int)(baseDays * adjustment));
//    }

}