package com.example.happyharvest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.happyharvest.databinding.ActivityMainSignBinding;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity_sign extends AppCompatActivity {
    private My_View_Model myViewModel;
    private ActivityMainSignBinding binding;
    // private FirebaseDatabase database;
    //  private DatabaseReference dbRef;
    private FirebaseDatabaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainSignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);


        firebaseHelper = FirebaseDatabaseHelper.getInstance();

//        myViewModel.getAllExpert().observe(this, count -> {
//            if (count.isEmpty()) {
//                new Thread(() -> addSampleExperts()).start();  // تشغيل في ثريد منفصل
//            }
//        });

//        myViewModel.getAllFarmer().observe(this, count1 -> {
//            if (count1.isEmpty()) {
//                new Thread(() -> addSampleFarmers()).start();
//            }
//        });
        addSampleExperts();
    //    addSampleFarmers();

//        uploadCrop(createOnionCrop());
//        uploadCrop(createTomatoCrop());
//        uploadCrop(createEggplantCrop());
//        uploadCrop(createGarlicCrop());
//        uploadCrop(createCarrotCrop());
//        uploadCrop(createWatermelonCrop());
//        uploadCrop(createWheatCrop());


        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_sign.this, ActivityMainSignIn.class);
                startActivity(intent);
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_sign.this, Sign_up.class);
                startActivity(intent);

            }
        });
        binding.Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity_sign.this, MainActivity_Main.class);
                intent.putExtra("USER_NAME2", "");
                startActivity(intent);
            }
        });
    }

    private void uploadFarmer(Farmer farmer) {


        FirebaseDatabaseHelper.getInstance().addFarmer(farmer, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {

            @Override
            public void onComplete(boolean success, Exception exception) {
                runOnUiThread(() -> {
                    if (success) {
                        //  Toast.makeText(MainActivity_sign.this, "✅ تم تسجيل المزارع بنجاح", Toast.LENGTH_SHORT).show();

                        // myViewModel.insertFarmer(farmer);

                    } else {
                        Toast.makeText(MainActivity_sign.this, "❌ فشل التسجيل: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }

    private void uploadCrop(Crop crop) {
        //    Toast.makeText(this, "جاري رفع المحصول...", Toast.LENGTH_SHORT).show();

        firebaseHelper.addCrop(crop, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {
            @Override
            public void onComplete(boolean success, Exception exception) {
                runOnUiThread(() -> {
                    if (success) {
                        //   Toast.makeText(MainActivity_sign.this, "✅ تم رفع المحصول", Toast.LENGTH_SHORT).show();
                        Log.d("Firebase", "Crop successfully uploaded");
                    } else {
                        Toast.makeText(MainActivity_sign.this, "❌ فشل في رفع المحصول", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error uploading crop", exception);
                    }
                });
            }
        });

    }

    private Crop createTomatoCrop() {

        Crop tomato = new Crop();
        tomato.setCrop_NAME("Tomato");
        tomato.setCategorie("Irrigated_fruits_Vegetable_seasonal_Highdemand");
        tomato.setDescription("الطماطم من أهم محاصيل الخضر الموسمية، تزرع في المناطق المعتدلة والحارة، تحتاج إلى عناية فائقة في الري والتسميد وتعتبر من المحاصيل عالية القيمة التجارية.");
        tomato.setExpert_USER_Name("jane_smith");
        tomato.setPreferredSoil("sandy loam with organic matter");
        tomato.setAllowedSoil("clay loam with good drainage");
        tomato.setForbiddenSoil("heavy clay or waterlogged soil");
        tomato.setPreferredIrrigation("drip irrigation with pressure control");
        tomato.setAllowedIrrigation("soaker hoses or careful sprinkler");
        tomato.setForbiddenIrrigation("flood irrigation or overhead sprinklers");
        tomato.setMinArea(0.25);
        tomato.setSeason("spring to summer");
        tomato.setPreferredHumidity("Moderate");
        tomato.setAllowedHumidity("Low");
        tomato.setForbiddenHumidity("High");
        tomato.setPreferredTemp("Cool");
        tomato.setAllowedTemp("Mild");
        tomato.setForbiddenTemp("Hot");
        tomato.setWateringFrequencyDays(2);
        tomato.setFertilizingFrequencyDays(15);
        tomato.setWateringInstructions("\uD83D\uDEB0 برنامج الري المتكامل:\n\n"
                + "1. مرحلة النمو الأولى (0-30 يوم):\n   - الري كل 3 أيام\n   - كمية الماء: 4 لتر/نبات\n\n"
                + "2. مرحلة الإزهار (30-60 يوم):\n   - الري كل يومين\n   - كمية الماء: 6 لتر/نبات\n\n"
                + "3. مرحلة الإثمار (60-90 يوم):\n   - الري يوميًا\n   - كمية الماء: 8 لتر/نبات\n\n"
                + "⚠️ تحذير: تجنب تبليل الأوراق لمنع الأمراض الفطرية");

        tomato.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد الشامل:\n\n"
                + "الأسبوع 1-3:\n   - سماد نيتروجيني (NPK 20-10-10)\n   - 50 جم/نبات\n\n"
                + "الأسبوع 4-6:\n   - سماد متوازن (NPK 15-15-15)\n   - 75 جم/نبات\n\n"
                + "الأسبوع 7-12:\n   - سماد عالي البوتاسيوم (NPK 10-10-30)\n   - 100 جم/نبات\n\n"
                + "✳️ نصائح:\n   - أضف الكالسيوم لمنع تعفن الطرف الزهري\n   - استخدم المخصبات الحيوية لتحسين الامتصاص");

        tomato.setPlantingMethod("\uD83C\uDF31 دليل الزراعة التفصيلي:\n\n"
                + "1. إعداد المشتل:\n   - تنقع البذور في ماء دافئ (50°م) لمدة 30 دقيقة\n   - تزرع في أصص صغيرة بعمق 1 سم\n\n"
                + "2. نقل الشتلات:\n   - عندما تصل لطول 15-20 سم\n   - قبل النقل بأسبوع تخفف ريها للتقسية\n\n"
                + "3. في الأرض الدائمة:\n   - مسافات الزراعة: 50 سم بين النباتات، 100 سم بين الخطوط\n   - تثبيت الدعامات قبل الزراعة\n\n"
                + "4. التقليم:\n   - إزالة السرطانات تحت العنقود الزهري الأول\n   - تقليم الأوراق السفلية بعد نضج أول عنقود");

        tomato.setCropProblems("\uD83D\uDC1B المشاكل والحلول المتكاملة:\n\n"
                + "1. ذبول الفيوزاريوم:\n   - الأعراض: اصفرار الأوراق من الأسفل\n   - العلاج: استخدام أصول مقاومة + تعقيم التربة\n\n"
                + "2. حشرة الذبابة البيضاء:\n   - الأعراض: تجعد الأوراق ووجود ندوة عسلية\n   - العلاج: مصائد صفراء + زيت النيم\n\n"
                + "3. تشقق الثمار:\n   - السبب: تذبذب الري\n   - الوقاية: ري منتظم + تغطية التربة\n\n"
                + "4. تعفن الطرف الزهري:\n   - السبب: نقص الكالسيوم\n   - العلاج: رش نترات الكالسيوم (5 جم/لتر)");

        tomato.setPruning_and_guidance("\uD83D\uDD04 إدارة النبات المتكاملة:\n\n"
                + "1. التدعيم:\n   - استخدام أوتاد طول 2 متر\n   - ربط الساق كل 20 سم\n\n"
                + "2. التقليم:\n   - إزالة 2-3 سرطانات أسبوعيًا\n   - تقليم الأوراق الصفراء\n\n"
                + "3. التلقيح:\n   - هز النباتات صباحًا\n   - استخدام فرشاة ناعمة لنقل اللقاح");

        tomato.setOptimalHumidity(65);
        tomato.setOptimalTemperature(25);
        tomato.setLightRequirements(8);
        tomato.setNumber_Plant_per_dunum(2500);
        tomato.setOrganicFertilizer("كمبوست + سماد بلدي متحلل + مخلفات نباتية");
        tomato.setChemicalPerPlant(50.0);
        tomato.setChemicalFertilizer("NPK + عناصر صغرى");
        tomato.setOrganicPerPlant(300.0);
        tomato.setPrevious_crop_preferred("البصل - الثوم - البقوليات");
        tomato.setPrevious_crop_allowed("الخس - الجزر - السبانخ");
        tomato.setPrevious_crop_forbidden("البطاطس - الباذنجان - الفلفل");
        tomato.setSoil_preparation_Favorite("حرث عميق 40 سم + تعقيم شمسي + إضافة 10 طن سماد عضوي للدونم");
        tomato.setPreparing_irrigation_tools_F("تركيب شبكة ري بالتنقيط مع فلتر رملي + حقن السماد");
        tomato.setPreparing_irrigation_tools_P("استخدام خراطيم النقع مع موزعات");
        tomato.setPreparing_irrigation_tools_A("تجنب الرشاشات العلوية");
        tomato.setSoil_preparation_allowed("حرث سطحى 20 سم + 5 طن سماد عضوي");
        tomato.setWeight_seeds_per_dunum(0.5);
        tomato.setSeedSpecifications("بذور هجين F1 معتمدة - نسبة إنبات >90% - خالية من الأمراض");
        tomato.setSeedlingPreparation("نقع البذور في ماء دافئ + معاملتها بالمبيدات الحيوية قبل الزراعة");
        tomato.setPlantingDistance("50 سم بين النباتات - 100 سم بين الخطوط - 2000 نبات/دونم");
        tomato.setPlantingDepth("2-3 سم للبذور - حتى أول ورقة حقيقية للشتلات");
        tomato.setInitialIrrigation("ري غزير مباشر بعد الزراعة + رية دمك بعد 3 أيام");
        tomato.setDaysToMaturity(90);
        tomato.setHigh("صنف هايبرد 6000");
        tomato.setMid("صنف سوبر ستار");
        tomato.setLow("صنف محلي");
        tomato.setTemperatureTolerance(12);
        tomato.setHumidityTolerance(35);
        return tomato;
    }

    private Crop createOnionCrop() {
        Crop onion = new Crop();
        onion.setCrop_NAME("Onion");//الاسم
        onion.setCategorie("Bulb_Vegetable_seasonal_Highdemand");  //التصنيفاتRoot grain Irrigated fruits Vegetable Bulb seasonal Highdemand
        onion.setDescription("البصل من المحاصيل الجذرية ويُزرع في الخريف أو الشتاء ويحتاج إلى تربة جيدة التصريف.");//الوصف
        onion.setExpert_USER_Name("jane_smith");//اسم الخبير
        onion.setPreferredSoil("sandy");//التربة المفضلة للمحصول
        onion.setAllowedSoil("muddy");//التربة المسموحة للمحصول
        onion.setAllowedSoil("rocky");//الت ربة المرفوضة للمحصول
        onion.setPreferredIrrigation("drip");//طريقة الري المفضلة للمحصول
        onion.setAllowedIrrigation("sprinkler");//طريقة الري المسموحة للمحصول
        onion.setForbiddenIrrigation("immersion");//طريقة الري المرفوضة للمحصول
        onion.setMinArea(0.3);//اقل مساحة مسموحة للزراعة
        onion.setSeason("spring");//الفصل المفضل لزراعة المحصول
        onion.setPreferredHumidity("High");//الرطوبة المفضلة للمحصول
        onion.setAllowedHumidity("Low");//الرطوبة المسموحة للمحصول
        onion.setForbiddenHumidity("Moderate");//الرطوبة المرفوضة للمحصول
        onion.setPreferredTemp("Cool");//درجة الحرارة المفضلة للمحصول
        onion.setAllowedTemp("Mild");//درجة الحرارة المسموحة للمحصول
        onion.setForbiddenTemp("Hot");//درجة الحرارة المرفوضة للمحصول
        onion.setWateringFrequencyDays(3);//عدد ايام دورة الري
        onion.setFertilizingFrequencyDays(14);//عدد ايام دورة التسميد
        onion.setWateringInstructions("\uD83D\uDEB0 برنامج الري:\n" +
                "\n" +
                "مرحلة النمو: ري كل 3 أيام (تجنب تبليل الأوراق)\n" +
                "\n" +
                "مرحلة الإثمار: ري يومي في الصيف (صباحاً)");//برنامج الري

        onion.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد:\n" +
                "\n" +
                "قبل الزراعة: سماد عضوي + سوبر فوسفات\n" +
                "\n" +
                "بعد 3 أسابيع: NPK (19-19-19)\n" +
                "\n" +
                "أثناء الإزهار: رش بورون + كالسيوم\n" +
                "\n" +
                "⚠\uFE0F تحذير: الإفراط في النيتروجين يقلل الإثمار!\n" +
                "\n");//برنامج التسميد
        onion.setPlantingMethod("\uD83C\uDF31 دليل شامل لزراعة الطماطم: من البذور إلى الحصاد\n" +
                "\uD83D\uDCCC 1. اختيار الصنف المناسب\n" +
                "\uD83D\uDD39 أصناف محلية: مثل \"هجين ف1 448\" (مقاوم للأمراض)\n" +
                "\uD83D\uDD39 أصناف عالمية: مثل \"بيف ستيك\" (للعصير) أو \"تشيري\" (للسلطات)\n" +
                "\uD83D\uDD39 اختيار حسب الغرض:\n" +
                "\n" +
                "\uD83E\uDD6B للعصير: أصناف ذات لب كثيف (سان مارزانو)\n" +
                "\n" +

                "\uD83E\uDD57 للطازج: أصناف حلوة (هجين 023)\n" +
                "\n" +
                "\uD83C\uDF3F 2. تحضير التربة\n" +
                "✅ المواصفات المثالية:\n" +
                "\n" +
                "درجة حموضة (pH 6-6.8)\n" +
                "\n" +
                "تربة جيدة الصرف (مخلوطة بـ 30% رمل)\n" +
                "\n" +
                "غنية بالمادة العضوية (أضف 10 كجم سماد بلدي/م²)\n" +
                "\n" +
                "⚠\uFE0F تحذير: تجنب التربة الطينية الثقيلة!\n" +
                "\n" +
                "\uD83C\uDF31 3. طرق الزراعة\n" +
                "الطريقة\tالتفاصيل\tالوقت المناسب\n" +
                "البذور\tتنقع في ماء دافئ (24 ساعة) ثم تزرع في أصص\tقبل 6-8 أسابيع من الصقيع الأخير\n" +
                "الشتل\tنقل الشتلات عند طول 15-20 سم\tبعد زوال خطر الصقيع\n" +
                "الزراعة المباشرة\t3 بذور/جورة بعمق 1 سم\tعندما تصل درجة الحرارة لـ 18°م ليلاً\n" +
                "\uD83D\uDD39 مسافات الزراعة:\n" +
                "\n" +
                "50-60 سم بين النباتات\n" +
                "\n" +
                "90-100 سم بين الخطوط\n" +
                "\n");//دليل شامل للزراعة
        onion.setCropProblems("\n" +
                "\uD83C\uDF45 مشاكل زراعة الطماطم وحلولها\n" +
                "1\uFE0F⃣ مشكلة: تعفن الطرف الزهري (Blossom End Rot)\n" +
                "الأعراض:\n" +
                "\n" +
                "ظهور بقع سوداء أو بنية داكنة عند طرف الثمرة\n" +
                "\n" +
                "جفاف المنطقة المصابة وتصلبها\n" +
                "\n" +
                "\uD83D\uDEE0\uFE0F الحلول:\n" +
                "✅ التوازن الكالسيومي: أضف الجير الزراعي أو رش نترات الكالسيوم (5 جم/لتر ماء)\n" +
                "✅ الري المنتظم: حافظ على رطوبة التربة (لا تجف تمامًا)\n" +
                "✅ التسميد المتوازن: تجنب الإفراط في الأسمدة النيتروجينية\n" +
                "\n"
                + "\uD83D\uDD04 الدورة الزراعية: تغيير المحصول كل موسم\n" +
                "\n" +
                "\uD83E\uDDEA تحليل التربة: قبل الزراعة لتحديد الاحتياجات\n" +
                "\n" +
                "\uD83D\uDC1D جذب الملقحات: بزراعة نباتات الزينة حول الحقل\n" +
                "\n" +
                "\uD83D\uDCC5 الزراعة في موعدها: حسب التقويم الزراعي للمنطقة\n" +
                "\n" +
                "\uD83D\uDC69\u200D\uD83C\uDF3E المتابعة اليومية: لاكتشاف المشاكل مبكراً\n" +
                "\n" +
                "\uD83D\uDCA1 إحصائية مهمة: 80% من مشاكل المحاصيل تنتج عن سوء إدارة الري والتسميد!");//المشاكل التي قد تواجه المحصول

        onion.setPruning_and_guidance("\uD83D\uDD04  التقليم والتوجيه\n" +
                "✂\uFE0F تقليم السرطانات: إزالة الفروع الجانبية تحت أول عنقود زهري\n" +
                "\uD83C\uDF3F التدعيم: استخدام أوتاد خشبية أو شباك تعليق\n" +
                "\n" +
                "طريقة التدلي: لف الساق حول الخيط كل أسبوع\n" +
                "\n");//التقليم والتوجيه

        onion.setPreferredAbundance("Available");//وفرة المياه المفضلة
        onion.setAllowedAbundance("Limited");//وفرة المياه المسموحة
        onion.setForbiddenAbundance("Unavailable");//وفرة المياه المرفوضة
        onion.setLearnMore("\uD83D\uDC1B 5. المكافحة المتكاملة للآفات\n" +
                "الآفة\tالعلاج\tالوقاية\n" +
                "الذبابة البيضاء\tرش زيت النيم (2 مل/لتر)\tمصائد صفراء لاصقة\n" +
                "التعفن البكتيري\tرش نحاس (كوسيد 101)\tتعقيم الأدوات\n" +
                "ديدان الثمار\tBacillus thuringiensis\tتغطية الثمار بأكياس ورقية\n" +
                "\uD83C\uDF1E 6. الظروف البيئية المثلى\n" +
                "☀\uFE0F الحرارة:\n" +
                "\n" +
                "النهار: 25-30°م\n" +
                "\n" +
                "الليل: 15-18°م\n" +
                "\n" +
                "\uD83D\uDCA1 الإضاءة: 8 ساعات ضوء يومياً على الأقل\n" +
                "\n" +
                "\uD83C\uDF27\uFE0F الرطوبة: 60-70% (استخدم مراوح في البيوت المحمية إذا زادت)\n" +
                "\n" +
                "\uD83D\uDED1 8. المشاكل الشائعة وحلولها\n" +
                "المشكلة\tالسبب\tالحل\n" +
                "تساقط الأزهار\tحرارة >35°م أو رطوبة عالية\tرش هرمون عقد الثمار (NAA)\n" +
                "تشقق الثمار\tري غير منتظم\tالري بانتظام + تغطية التربة\n" +
                "بقع صفراء على الأوراق\tنقص مغنيسيوم\tرش كبريتات ماغنيسيوم (2 جم/لتر)\n" +
                "\uD83D\uDCC5 9. مواعيد الزراعة حسب المناطق\n" +
                "المنطقة\tالموعد المثالي\n" +
                "المناطق الدافئة\tسبتمبر-أكتوبر\n" +
                "المناطق المعتدلة\tفبراير-مارس\n" +
                "البيوت المحمية\tطوال العام (مع تحكم بالمناخ)\n" +
                "\uD83E\uDDFA 10. الحصاد والتخزين\n" +
                "\uD83D\uDCC5 موعد الحصاد: بعد 70-90 يوم من الزراعة\n" +
                "\uD83D\uDD39 علامات النضج:\n" +
                "\n" +
                "لون أحمر متجانس\n" +
                "\n" +
                "ملمس طري عند الضغط الخفيف\n" +
                "\n" +
                "❄\uFE0F التخزين:\n" +
                "\n" +
                "الطازجة: 10-12°م (تجنب الثلاجة!)\n" +
                "\n" +
                "التجفيف: تقطع شرائح وتجفف بالشمس 3 أيام\n" +
                "\n" +
                "\uD83C\uDFAF نصائح الخبراء\n" +
                "لثمار أكثر حلاوة: أضف ملعقة صغيرة ملح إنجليزي/لتر ماء عند الري\n" +
                "\n" +
                "لمكافحة النيماتودا: زراعة القطيفة (Marigold) بين الخطوط\n" +
                "\n" +
                "لزيادة العقد: هز النباتات برفق عند الصباح لنقل اللقاح\n" +
                "\n" +
                "حقيقة ممتعة: \uD83E\uDDEA الطماطم تنتج هرمون \"الإيثيلين\" الذي يساعد في نضج الفواكه الأخرى!\n" +
                "\n" +
                "باستخدام هذا الدليل، يمكنك الحصول على إنتاج وفير بجودة عالية \uD83C\uDF1F\uD83C\uDF45."
        );//المكافحة المتكاملة للآفات لهذا المحصول
        onion.setOptimalHumidity(50);//درجة الرطوبة المفضلة
        onion.setOptimalTemperature(20);//درجة الحرارة المفضلة
        onion.setLightRequirements(3);//متطلبات الاضاءة  للحمام الشمسي
        onion.setNumber_Plant_per_dunum(1500);//عدد الشتلات لكل دونوم
        onion.setOrganicFertilizer("ارض مكشوفة_بيت بلاستيكي_زراعة مائية_زراعة عضوية");

        onion.setChemicalPerPlant(12.0);//كمية السماد الكيميائي لكل شتلة بالغرام
        onion.setChemicalFertilizer("Chemical");//نوع السماد الكيميائي
        onion.setOrganicPerPlant(150.0);//كمية السماد العضوي بالغرام

        onion.setPrevious_crop_preferred("الخس، السبانخ، الجرجير، البقدونس، الكزبرة، النعناع، البرسيم، الحلبة، الشوفان (كغطاء أخضر)، الذرة الرفيعة، الدخن، عباد الشمس");//محاصيل سابقة من المفضل زراعة المحصول الحالي بعدها او بعد موسمها
        onion.setPrevious_crop_allowed("الذرة، القمح، الشعير، الشوفان، العدس، الفول، الحمص، الفاصوليا، البازلاء، البطيخ، الشمام، الخيار، الكوسا، الباذنجان، الفلفل، الطماطم");//محاصيل سابقة من المسموح زراعة المحصول الحالي بعدها او بعد موسمها

        onion.setPrevious_crop_forbidden("الثوم، الكراث، البصل، البطاطا، البطاطس، الفجل، اللفت، الجزر، الشمندر، الكرنب، القرنبيط، البروكلي");//محاصيل سابقة مرفوض زراعة المحصول الحالي بعدها او بعد موسمها
        onion.setSoil_preparation_Favorite("حرث الأرض مرتين على الأقل بعمق 25–30 سم، حتى تهوي التربة وتتفتت الكتل.\n" +
                "\n" +
                "تنظيف الأرض من الحشائش والجذور اللي ممكن تعيق نمو الشتلات.\n" +
                "\n" +
                "إضافة السماد العضوي المتحلل (بلدي أو كمبوست) بمعدل 4–5 طن للدونم، وخلطه جيدًا بالتربة.\n" +
                "\n" +
                "لو الأرض ثقيلة، يفضل إضافة رمل أو بيتموس لتحسين التصريف.\n" +
                "\n" +
                "تأكد إن التربة فيها صرف جيد، لأن الطماطم ما بتحب تجمع المياه.\n" +
                "\n" +
                "اضبط حموضة التربة (pH) لتكون بين 6 و6.8، لأنها الأنسب للطماطم.\n" +
                "\n" +
                "أعمل تسوية جيدة للتربة بعد الحراثة حتى تكون الأرض مستوية وسهلة للري والزراعة.\n" +
                "\n" +
                "إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.");//طريقة تحضير التربة اذا كانت مفضلة
        onion.setPreparing_irrigation_tools_F("إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.");//تحضير ادوات الري والزراعة لهذا المحصول اذا كانت التربة مفضلة وبناءا على ما اذا كانت شتلات او بذور
        onion.setPreparing_irrigation_tools_P("إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.");// وبناءا على ما اذا كانت شتلات او بذور تحضير ادوات الري والزراعة لهذا المحصول اذا كانت التربة مسموحة
        onion.setPreparing_irrigation_tools_A("إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.");//تحضير ادوات الري والزراعة لهذا المحصول اذا كانت التربة مرفوضة  وبناءا على ما اذا كانت شتلات او بذور

        onion.setSoil_preparation_allowed("بذور محسنة ومعتمدة.\n" +
                "\n" +
                "مقاومة للأمراض الشائعة.\n" +
                "\n" +
                "نسبة إنبات عالية.\n" +
                "\n" +
                "شراءها من مصدر موثوق.");//طريقة تحضير التربة اذا كانت مسموحة
        onion.setWeight_seeds_per_dunum(3);//كمية البذور بالكيلو غرام لدونوم
        onion.setSeedSpecifications("اختر بذور بصل عالية الجودة ومعتمدة، خالية من الأمراض، وذات حجم متوسط للحصول على نمو متجانس. \uD83E\uDDC5");
        onion.setSeedlingPreparation("قم بنقع البذور في ماء فاتر لمدة 12 ساعة قبل الزراعة، ويمكن تجهيز الشتلات في مشتل لمدة 6 أسابيع قبل النقل. \uD83C\uDF31");
        onion.setPlantingDistance("اترك مسافة 10-15 سم بين كل نبات، و30-40 سم بين الخطوط لضمان التهوية الجيدة ونمو البصل بشكل مناسب. \uD83D\uDCCF");
        onion.setPlantingDepth("ازرع البذور أو الشتلات على عمق 2-3 سم فقط في التربة لتسهيل الإنبات والنمو. ⛏\uFE0F");
        onion.setInitialIrrigation("\"بعد الزراعة، اسقِ الأرض مباشرة بريّة غمر خفيفة لتثبيت البذور أو الشتلات في التربة. \uD83D\uDCA7");
        onion.setDaysToMaturity(100);//عدد الايام حتى النضج
        onion.setHigh("ابيض");//المحصول الاكثر ملائمة اذا توفر ثلاث انواع مثلا من هذا المحصول
        onion.setMid("احمر");//المحصول متوسط الملائمة اذا توفر ثلاث انواع مثلا من هذا المحصول
        onion.setLow("اصفر");//المحصول الاقل ملائمة اذا توفر ثلاث انواع مثلا من هذا المحصول
        onion.setTemperatureTolerance(10);//فرق الحرارة الذي يتحمله المحصول
        onion.setHumidityTolerance(30);//فرق الرطوبة الذي يتحمله المحصول
        return onion;
    }

    private Crop createWatermelonCrop() {
        Crop watermelon = new Crop();
        watermelon.setCrop_NAME("Watermelon");
        watermelon.setCategorie("Fruit_Summer_HighValue");
        watermelon.setDescription("البطيخ من فصيلة القرعيات، يحتاج إلى موسم طويل دافئ وتربة رملية جيدة الصرف.");
        watermelon.setExpert_USER_Name("jane_smith");

        // التربة
        watermelon.setPreferredSoil("sandy");
        watermelon.setAllowedSoil("loamy");
        watermelon.setForbiddenSoil("clay");

        // الري
        watermelon.setPreferredIrrigation("drip");
        watermelon.setAllowedIrrigation("sprinkler");
        watermelon.setForbiddenIrrigation("flood");

        // المساحة والموسم
        watermelon.setMinArea(0.5);
        watermelon.setSeason("summer");

        // المناخ
        watermelon.setPreferredHumidity("Low");
        watermelon.setAllowedHumidity("Moderate");
        watermelon.setForbiddenHumidity("High");
        watermelon.setPreferredTemp("Hot");
        watermelon.setAllowedTemp("Mild");
        watermelon.setForbiddenTemp("Cool");

        // البرامج الزراعية
        watermelon.setWateringFrequencyDays(5);
        watermelon.setFertilizingFrequencyDays(14);
        watermelon.setWateringInstructions("⚡ ري منتظم مع تقليل الماء قبل الحصاد ب أسبوع لزيادة السكر");
        watermelon.setFertilizingInstructions("• أساسي: سماد عضوي\n• مرحلة النمو: NPK 20-20-20\n• نضج الثمار: بوتاسيوم");

        // الزراعة
        watermelon.setPlantingMethod("1. زراعة الشتلات في أكواب\n2. نقلها للأرض بعد 3 أسابيع\n3. مسافات 2 م بين النباتات");
        watermelon.setDaysToMaturity(90);
        watermelon.setNumber_Plant_per_dunum(800);

        // المشاكل
        watermelon.setCropProblems("• ذبول الفيوزاريوم: تعقيم التربة\n• حشرات المن: رش صابون زراعي");

        // الأصناف
        watermelon.setHigh("بطيخ شمامي (Sugar Baby)");
        watermelon.setMid("بطيخ أصفر");
        watermelon.setLow("بطيخ بري");

        return watermelon;
    }

    private Crop createWheatCrop() {
        Crop wheat = new Crop();
        wheat.setCrop_NAME("Wheat");
        wheat.setCategorie("Grain_Staple_HighYield");
        wheat.setDescription("القمح من المحاصيل الأساسية في العالم، يُزرع في موسم الشتاء ويحتاج إلى تربة خصبة وجو معتدل.");
        wheat.setExpert_USER_Name("jane_smith");

        // التربة
        wheat.setPreferredSoil("loamy");
        wheat.setAllowedSoil("clay");
        wheat.setForbiddenSoil("sandy");

        // الري
        wheat.setPreferredIrrigation("flood");
        wheat.setAllowedIrrigation("sprinkler");
        wheat.setForbiddenIrrigation("drip");

        // المساحة والموسم
        wheat.setMinArea(1.0);
        wheat.setSeason("winter");

        // المناخ
        wheat.setPreferredHumidity("Moderate");
        wheat.setAllowedHumidity("Low");
        wheat.setForbiddenHumidity("High");
        wheat.setPreferredTemp("Cool");
        wheat.setAllowedTemp("Mild");
        wheat.setForbiddenTemp("Hot");

        // البرامج الزراعية
        wheat.setWateringFrequencyDays(7);
        wheat.setFertilizingFrequencyDays(21);
        wheat.setWateringInstructions("✅ الري عند الزراعة وبعد كل تسميد\n⚠️ تجنب الري الغزير أثناء الإزهار");
        wheat.setFertilizingInstructions("• قبل الزراعة: سماد سوبر فوسفات\n• بعد 3 أسابيع: يوريا\n• أثناء التزهير: بورون");

        // الزراعة
        wheat.setPlantingMethod("1. حرث التربة بعمق 20 سم\n2. بذر 120 كغ/دونم\n3. تغطية البذور بطبقة خفيفة من التربة");
        wheat.setDaysToMaturity(180);
        wheat.setNumber_Plant_per_dunum(2000000); // عدد البذور/دونم

        // المشاكل
        wheat.setCropProblems("• الصدأ الأصفر: رش مبيد فطري\n• النيماتودا: تعقيم التربة");

        // الأصناف
        wheat.setHigh("قمح دقيق (Hard Wheat)");
        wheat.setMid("قمح عادي (Soft Wheat)");
        wheat.setLow("قمح علفي");

        return wheat;
    }

    private Crop createEggplantCrop() {

        Crop eggplant = new Crop();
        eggplant.setCrop_NAME("Eggplant");
        eggplant.setCategorie("Irrigated_Vegetable_seasonal_Highdemand");
        eggplant.setDescription("الباذنجان من الخضروات الموسمية ذات القيمة الغذائية العالية، يمتاز بتحمله للحرارة ويحتاج إلى موسم نمو طويل ودافئ لإنتاج ثمار عالية الجودة. يُزرع في المناطق المعتدلة والحارة ويعد من المحاصيل ذات العائد الاقتصادي الجيد.");
        eggplant.setExpert_USER_Name("jane_smith");
        eggplant.setPreferredSoil("deep sandy loam rich in organic matter");
        eggplant.setAllowedSoil("clay loam with perfect drainage");
        eggplant.setForbiddenSoil("heavy clay or saline soils");
        eggplant.setPreferredIrrigation("drip irrigation with daily monitoring");
        eggplant.setAllowedIrrigation("soaker hoses or careful sprinkler");
        eggplant.setForbiddenIrrigation("flood irrigation or overhead sprinklers");
        eggplant.setMinArea(0.3);
        eggplant.setSeason("spring to summer (requires 100-140 frost-free days)");
        eggplant.setPreferredHumidity("Moderate (50-65%) during fruit set");
        eggplant.setAllowedHumidity("Low (40-50%) in early stages");
        eggplant.setForbiddenHumidity("High (>80%) especially during flowering");
        eggplant.setPreferredTemp("Warm (25-32°C day / 18-24°C night)");
        eggplant.setAllowedTemp("Mild (20-35°C) for limited periods");
        eggplant.setForbiddenTemp("Cool (<15°C) or extreme heat (>40°C)");
        eggplant.setWateringFrequencyDays(2);
        eggplant.setFertilizingFrequencyDays(14);
        eggplant.setWateringInstructions("\uD83D\uDEB0 برنامج الري المتكامل:\n\n"
                + "1. مرحلة النمو الخضري (0-40 يوم):\n   - الري كل 3 أيام\n   - كمية الماء: 5 لتر/نبات\n\n"
                + "2. مرحلة الإزهار والإثمار (40-100 يوم):\n   - الري يومياً\n   - كمية الماء: 7 لتر/نبات\n\n"
                + "3. مرحلة النضج (100-140 يوم):\n   - تقليل الري تدريجياً\n   - تجنب التذبذب في الري لمنع تساقط الأزهار\n\n"
                + "⚠️ تحذير: نقص الري أثناء الإزهار يؤدي لسقوط الأزهار وضعف العقد");

        eggplant.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد الشامل:\n\n"
                + "قبل الزراعة:\n   - 20 طن سماد عضوي متحلل/هكتار\n   - سوبر فوسفات (300 كجم/هكتار)\n\n"
                + "بعد 3 أسابيع:\n   - نترات الأمونيوم (150 كجم/هكتار)\n\n"
                + "أثناء الإثمار:\n   - سماد NPK 12-12-17 (250 كجم/هكتار)\n\n"
                + "✳️ نصائح:\n   - رش أوراق بالكالسيوم أسبوعياً لمنع تعفن الطرف الزهري\n   - إضافة الماغنيسيوم لتحسين لون الثمار");

        eggplant.setPlantingMethod("\uD83C\uDF31 دليل الزراعة التفصيلي:\n\n"
                + "1. إعداد الشتلات:\n   - زراعة البذور في أصص صغيرة بعمق 1 سم\n   - نقل الشتلات عند وصولها لطول 15-20 سم\n\n"
                + "2. الزراعة الدائمة:\n   - مسافات الزراعة: 60 سم بين النباتات، 100 سم بين الخطوط\n   - إضافة الأسمدة الأساسية في قاع الجورة\n\n"
                + "3. العناية:\n   - تركيب دعامات خشبية للنباتات\n   - إزالة الأوراق السفلية بعد نضج أول ثمرة\n\n"
                + "4. الحصاد:\n   - عند اكتمال حجم الثمار ولمعان القشرة\n   - القص بالسكين مع ترك جزء من الساق");

        eggplant.setCropProblems("\uD83D\uDC1B المشاكل والحلول المتكاملة:\n\n"
                + "1. حشرة المن:\n   - الأعراض: تجعد الأوراق ووجود مادة لزجة\n   - العلاج: صابون زراعي + زيت النيم\n\n"
                + "2. ذبابة الفاكهة:\n   - الأعراض: ثقوب صغيرة في الثمار\n   - العلاج: مصائد فرمونية + شباك حماية\n\n"
                + "3. اللفحة المبكرة:\n   - السبب: فطر Alternaria\n   - الوقاية: رش أكسيد النحاس كل أسبوعين\n\n"
                + "4. تشوه الثمار:\n   - السبب: نقص البورون أو سوء التلقيح\n   - العلاج: رش بوراكس (2 جم/لتر) + هز النباتات صباحاً");

        eggplant.setPruning_and_guidance("\uD83D\uDD04 إدارة النبات:\n\n"
                + "1. التقليم:\n   - إزالة السرطانات الجانبية تحت أول تفرع\n   - تقليم الأوراق الصفراء فقط\n\n"
                + "2. التدعيم:\n   - استخدام أوتاد طول 1.5 متر\n   - ربط الساق الرئيسية كل 30 سم\n\n"
                + "3. التغطية:\n   - استخدام القش الأسود للحفاظ على الرطوبة\n   - منع نمو الأعشاب الضارة");

        eggplant.setPreferredAbundance("Available (6000 m³/season)");
        eggplant.setAllowedAbundance("Limited (4000 m³/season)");
        eggplant.setForbiddenAbundance("Unavailable (<2000 m³/season)");
        eggplant.setLearnMore("تفاصيل إضافية:\n- الأصناف المقاومة للأمراض\n- تقنيات الزراعة بدون تربة\n- معايير الجودة للتصدير");
        eggplant.setOptimalHumidity(60);
        eggplant.setOptimalTemperature(28);
        eggplant.setLightRequirements(8);
        eggplant.setNumber_Plant_per_dunum(1500);
        eggplant.setOrganicFertilizer("سماد دواجن + كمبوست + رماد خشب");
        eggplant.setChemicalPerPlant(25.0);
        eggplant.setChemicalFertilizer("NPK + عناصر صغرى");
        eggplant.setOrganicPerPlant(300.0);
        eggplant.setPrevious_crop_preferred("البقوليات - الذرة - القمح");
        eggplant.setPrevious_crop_allowed("الخس - السبانخ - الجزر");
        eggplant.setPrevious_crop_forbidden("الطماطم - الفلفل - البطاطس");
        eggplant.setSoil_preparation_Favorite("حرث عميق 50 سم + تعقيم بالبخار + 15 طن سماد عضوي/هكتار");
        eggplant.setPreparing_irrigation_tools_F("شبكة ري بالتنقيط مع فلتر شبكي + مضخة حقن");
        eggplant.setPreparing_irrigation_tools_P("خراطيم النقع ذات المنقاطات");
        eggplant.setPreparing_irrigation_tools_A("الرشاشات العلوية أو الري بالغمر");
        eggplant.setSoil_preparation_allowed("حرث متوسط 30 سم + 10 طن سماد عضوي");
        eggplant.setWeight_seeds_per_dunum(0.2);
        eggplant.setSeedSpecifications("بذور هجين F1 معتمدة - نسبة إنبات >90% - مقاومة للذبول");
        eggplant.setSeedlingPreparation("نقع البذور في ماء دافئ (50°م) لمدة 24 ساعة");
        eggplant.setPlantingDistance("60 سم بين النباتات - 100 سم بين الخطوط - 16,000 نبات/هكتار");
        eggplant.setPlantingDepth("1-2 سم للبذور - حتى أول ورقة حقيقية للشتلات");
        eggplant.setInitialIrrigation("ري غزير بعد الزراعة + رية دمك بعد 3 أيام");
        eggplant.setDaysToMaturity(100);
        eggplant.setHigh("صنف Black Beauty");
        eggplant.setMid("صنف Long Purple");
        eggplant.setLow("صنف محلي دائري");
        eggplant.setTemperatureTolerance(12);
        eggplant.setHumidityTolerance(35);
        return eggplant;
    }

    private Crop createGarlicCrop() {
        Crop garlic = new Crop();
        garlic.setCrop_NAME("Garlic");
        garlic.setCategorie("Bulb_Vegetable_seasonal_Highdemand");
        garlic.setDescription("الثوم من المحاصيل البصلية ذات القيمة الطبية والتجارية العالية، يمتاز بفترة نمو طويلة واحتياجاته الخاصة من البرودة خلال مرحلة التبصير. يعتبر من المحاصيل الإستراتيجية في العديد من الدول بسبب خصائصه المضادة للبكتيريا والفطريات.");
        garlic.setExpert_USER_Name("jane_smith");
        garlic.setPreferredSoil("sandy loam with perfect drainage and high organic content");
        garlic.setAllowedSoil("clay loam with added sand and compost");
        garlic.setForbiddenSoil("heavy clay or waterlogged soils");
        garlic.setPreferredIrrigation("drip irrigation with pressure-compensating emitters");
        garlic.setAllowedIrrigation("low-pressure sprinkler in early morning");
        garlic.setForbiddenIrrigation("flood irrigation or overhead sprinklers");
        garlic.setMinArea(0.4);
        garlic.setSeason("autumn planting (Oct-Nov) for winter chilling requirement");
        garlic.setPreferredHumidity("Moderate (60-70%) during bulb formation");
        garlic.setAllowedHumidity("Low (40-50%) in early growth stages");
        garlic.setForbiddenHumidity("High (>80%) especially near harvest");
        garlic.setPreferredTemp("Cool (13-24°C) for optimal growth");
        garlic.setAllowedTemp("Cold (4-30°C) for limited periods");
        garlic.setForbiddenTemp("Freezing (<-3°C) or extreme heat (>35°C)");
        garlic.setWateringFrequencyDays(5);
        garlic.setFertilizingFrequencyDays(21);
        garlic.setWateringInstructions("\uD83D\uDEB0 برنامج الري المتكامل:\n\n"
                + "1. مرحلة التأسيس (0-30 يوم):\n   - الري كل 5 أيام\n   - كمية الماء: 3 لتر/نبات\n\n"
                + "2. مرحلة النمو النشط (30-90 يوم):\n   - الري كل 3 أيام\n   - كمية الماء: 5 لتر/نبات\n\n"
                + "3. مرحلة النضج (90-150 يوم):\n   - إيقاف الري تماماً قبل 3 أسابيع من الحصاد\n   - ترك التربة تجف تماماً\n\n"
                + "⚠️ تحذير: الري الزائد في المراحل المتأخرة يقلل من جودة التخزين");

        garlic.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد الشامل:\n\n"
                + "قبل الزراعة:\n   - 20 طن سماد عضوي متحلل/هكتار\n   - سوبر فوسفات (400 كجم/هكتار)\n\n"
                + "بعد 6 أسابيع:\n   - نترات الكالسيوم (150 كجم/هكتار)\n\n"
                + "أثناء تكوين الفصوص:\n   - سلفات البوتاسيوم (300 كجم/هكتار)\n\n"
                + "✳️ نصائح:\n   - تجنب الأسمدة النيتروجينية بعد مرحلة تكوين الفصوص\n   - إضافة الكبريت الزراعي لتحسين النكهة والخصائص الطبية");

        garlic.setPlantingMethod("\uD83C\uDF31 دليل الزراعة التفصيلي:\n\n"
                + "1. إعداد الفصوص:\n   - تفكيك الرؤوس إلى فصوص قبل الزراعة بيوم\n   - اختيار الفصوص الكبيرة الخالية من العيوب\n\n"
                + "2. المعالجة:\n   - نقع الفصوص في ماء دافئ (45°م) + مبيد فطري لمدة 20 دقيقة\n\n"
                + "3. الزراعة:\n   - عمق الزراعة: 3-5 سم (في التربة الرملية) / 2-3 سم (في التربة الطينية)\n   - المسافات: 10 سم بين النباتات، 30 سم بين الخطوط\n\n"
                + "4. العناية:\n   - تغطية التربة بالقش للحفاظ على الرطوبة\n   - إزالة الأعشاب يدوياً لتجنب إيذاء الجذور");

        garlic.setCropProblems("\uD83D\uDC1B المشاكل والحلول المتكاملة:\n\n"
                + "1. عفن العنق الأبيض:\n   - الأعراض: بقع بيضاء على الساق\n   - العلاج: تناوب زراعي + معاملة الفصوص قبل الزراعة\n\n"
                + "2. حفار ساق الثوم:\n   - الأعراض: أنفاق في الساق\n   - العلاج: مصائد فرمونية + Bacillus thuringiensis\n\n"
                + "3. الصدأ:\n   - السبب: فطر Puccinia allii\n   - الوقاية: رش مانكوزيب كل أسبوعين\n\n"
                + "4. صغر حجم الفصوص:\n   - السبب: زراعة فصوص صغيرة أو نقص تغذية\n   - العلاج: استخدام فصوص كبيرة + برنامج تسميد متوازن");

        garlic.setPruning_and_guidance("\uD83D\uDD04 إدارة المحصول:\n\n"
                + "1. إزالة السويقات:\n   - قطع ساق الزهرة عند ظهورها لتحسين حجم الفصوص\n   - يمكن استخدام السويقات في الطهي\n\n"
                + "2. التغطية:\n   - استخدام القش أو البلاستيك الأسود\n   - الحفاظ على رطوبة التربة ومنع الأعشاب\n\n"
                + "3. الحصاد:\n   - عندما تذبل 50% من الأوراق\n   - قلع النباتات في الصباح الباكر");

        garlic.setPreferredAbundance("Available (3500 m³/season)");
        garlic.setAllowedAbundance("Limited (2500 m³/season)");
        garlic.setForbiddenAbundance("Unavailable (<1500 m³/season)");
        garlic.setLearnMore("تفاصيل إضافية:\n- طرق التخزين المثلى (0-1°C ورطوبة 60-70%)\n- معالجة ما بعد الحصاد\n- الأصناف ذات المحتوى العالي من الأليسين");
        garlic.setOptimalHumidity(65);
        garlic.setOptimalTemperature(18);
        garlic.setLightRequirements(10);
        garlic.setNumber_Plant_per_dunum(50000);
        garlic.setOrganicFertilizer("سماد بلدي متحلل + كمبوست + رقائق خشب");
        garlic.setChemicalPerPlant(12.0);
        garlic.setChemicalFertilizer("NPK + الكبريت + الزنك");
        garlic.setOrganicPerPlant(150.0);
        garlic.setPrevious_crop_preferred("البقوليات - القمح - الذرة");
        garlic.setPrevious_crop_allowed("الخس - السبانخ - الجزر");
        garlic.setPrevious_crop_forbidden("البصل - الكراث - البطاطس");
        garlic.setSoil_preparation_Favorite("حرث عميق 40 سم + تعقيم شمسي + 12 طن سماد عضوي/هكتار");
        garlic.setPreparing_irrigation_tools_F("شبكة ري بالتنقيط مع فلتر دقيق + مضخة حقن");
        garlic.setPreparing_irrigation_tools_P("خراطيم النقع ذات الضغط المنخفض");
        garlic.setPreparing_irrigation_tools_A("الرشاشات العلوية أو الري بالغمر");
        garlic.setSoil_preparation_allowed("حرث متوسط 25 سم + 8 طن سماد عضوي");
        garlic.setWeight_seeds_per_dunum(200);
        garlic.setSeedSpecifications("فصوص كبيرة الحجم (5-7 جم) - خالية من الأمراض - من أصناف معتمدة");
        garlic.setSeedlingPreparation("تقشير الفصوص الخارجية قبل الزراعة بيوم واحد");
        garlic.setPlantingDistance("10 سم بين النباتات - 30 سم بين الخطوط - 500,000 فص/هكتار");
        garlic.setPlantingDepth("3-5 سم في التربة الرملية - 2-3 سم في التربة الطينية");
        garlic.setInitialIrrigation("ري غزير بعد الزراعة مباشرة + رية دمك بعد 5 أيام");
        garlic.setDaysToMaturity(150);
        garlic.setHigh("صنف Egyptian White");
        garlic.setMid("صنف Purple Stripe");
        garlic.setLow("صنف محلي صغير");
        garlic.setTemperatureTolerance(15);
        garlic.setHumidityTolerance(20);
        return garlic;
    }

    private Crop createCarrotCrop() {

        Crop carrot = new Crop();
        carrot.setCrop_NAME("Carrot");
        carrot.setCategorie("Root_Vegetable_seasonal_Highdemand");
        carrot.setDescription("الجزر من أهم المحاصيل الجذرية الغنية بالبيتا كاروتين، يمتاز بألوانه المختلفة (أحمر، أصفر، بنفسجي) ويحتاج إلى تربة عميقة خفيفة لضمان نمو جذور مستقيمة. يعتبر من المحاصيل ذات القيمة الغذائية العالية وفترة النمو المتوسطة.");
        carrot.setExpert_USER_Name("jane_smith");
        carrot.setPreferredSoil("deep sandy loam free from stones");
        carrot.setAllowedSoil("loamy sand with good structure");
        carrot.setForbiddenSoil("heavy clay or rocky soils");
        carrot.setPreferredIrrigation("low-pressure sprinkler irrigation");
        carrot.setAllowedIrrigation("drip irrigation with careful management");
        carrot.setForbiddenIrrigation("flood irrigation or irregular watering");
        carrot.setMinArea(0.2);
        carrot.setSeason("autumn spring");
        carrot.setPreferredHumidity("Moderate");
        carrot.setAllowedHumidity("Low");
        carrot.setForbiddenHumidity("High");
        carrot.setPreferredTemp("Cool");
        carrot.setAllowedTemp("Mild");
        carrot.setForbiddenTemp("Hot");
        carrot.setWateringFrequencyDays(4);
        carrot.setFertilizingFrequencyDays(21);
        carrot.setWateringInstructions("\uD83D\uDEB0 برنامج الري المتكامل:\n\n"
                + "1. مرحلة ما قبل الإنبات (0-10 يوم):\n   - الري الخفيف يومياً\n   - الحفاظ على سطح التربة رطباً\n\n"
                + "2. مرحلة النمو الخضري (10-50 يوم):\n   - الري كل 3 أيام\n   - كمية الماء: 4 لتر/م²\n\n"
                + "3. مرحلة تكوين الجذور (50-90 يوم):\n   - الري العميق كل 5 أيام\n   - تقليل الري قبل الحصاد بأسبوعين\n\n"
                + "⚠️ تحذير: التذبذب في الري يؤدي إلى تشقق الجذور");

        carrot.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد الشامل:\n\n"
                + "قبل الزراعة:\n   - 15 طن سماد عضوي متحلل/هكتار\n   - سوبر فوسفات (250 كجم/هكتار)\n\n"
                + "بعد 4 أسابيع:\n   - نترات البوتاسيوم (150 كجم/هكتار)\n\n"
                + "أثناء تكوين الجذور:\n   - سماد NPK منخفض النيتروجين (8-12-24)\n\n"
                + "✳️ نصائح:\n   - تجنب الأسمدة النيتروجينية الزائدة\n   - إضافة البورون لمنع القلب الأسود");

        carrot.setPlantingMethod("\uD83C\uDF31 دليل الزراعة التفصيلي:\n\n"
                + "1. إعداد التربة:\n   - حراثة عميقة (40-50 سم)\n   - إزالة جميع الحجارة والعوائق\n\n"
                + "2. الزراعة:\n   - البذر في سطور بعمق 1-1.5 سم\n   - معدل البذر: 3-4 كجم/هكتار\n\n"
                + "3. الخف:\n   - عند وصول النباتات لطول 5 سم\n   - ترك مسافة 5-7 سم بين النباتات\n\n"
                + "4. الحصاد:\n   - عندما يصل قطر الجذور لـ 2-3 سم\n   - الحصاد في الصباح الباكر");

        carrot.setCropProblems("\uD83D\uDC1B المشاكل والحلول المتكاملة:\n\n"
                + "1. ذبابة الجزر:\n   - الأعراض: أنفاق في الجذور\n   - العلاج: شباك حماية + زراعة مبكرة\n\n"
                + "2. البياض الدقيقي:\n   - الأعراض: بقع بيضاء على الأوراق\n   - العلاج: رش الكبريت الميكروني\n\n"
                + "3. تشوه الجذور:\n   - السبب: تربة غير مناسبة أو حشرات\n   - الوقاية: تحضير التربة جيداً\n\n"
                + "4. القلب الأسود:\n   - السبب: نقص البورون\n   - العلاج: رش بوراكس (2 جم/لتر)");

        carrot.setPruning_and_guidance("\uD83D\uDD04 إدارة المحصول:\n\n"
                + "1. التغطية:\n   - استخدام القش الخفيف\n   - الحفاظ على رطوبة التربة\n\n"
                + "2. إزالة الأعشاب:\n   - يدوياً أو ميكانيكياً\n   - تجنب الأعشاب الضارة\n\n"
                + "3. التدرج في الحصاد:\n   - حصاد جزئي لتحسين جودة المنتج");

        carrot.setPreferredAbundance("Available (4000 m³/season)");
        carrot.setAllowedAbundance("Limited (3000 m³/season)");
        carrot.setForbiddenAbundance("Unavailable (<2000 m³/season)");
        carrot.setLearnMore("تفاصيل إضافية:\n- أصناف الجزر المختلفة (نانتس، شانتينيه)\n- تقنيات التعبئة والتخزين\n- القيمة الغذائية للجزر الملون");
        carrot.setOptimalHumidity(65);
        carrot.setOptimalTemperature(18);
        carrot.setLightRequirements(10);
        carrot.setNumber_Plant_per_dunum(1000000);
        carrot.setOrganicFertilizer("سماد بلدي متحلل + كمبوست + رمال");
        carrot.setChemicalPerPlant(8.0);
        carrot.setChemicalFertilizer("NPK + عناصر صغرى");
        carrot.setOrganicPerPlant(100.0);
        carrot.setPrevious_crop_preferred("البقوليات - الذرة - القمح");
        carrot.setPrevious_crop_allowed("الخس - السبانخ - البصل");
        carrot.setPrevious_crop_forbidden("البقدونس - الكرفس - الشمندر");
        carrot.setSoil_preparation_Favorite("حرث عميق 50 سم + تنعيم التربة + 10 طن سماد عضوي/هكتار");
        carrot.setPreparing_irrigation_tools_F("شبكة ري بالرشاشات الدقيقة");
        carrot.setPreparing_irrigation_tools_P("خراطيم النقع ذات الضغط المنخفض");
        carrot.setPreparing_irrigation_tools_A("الري بالغمر أو الأنظمة غير المنتظمة");
        carrot.setSoil_preparation_allowed("حرث متوسط 30 سم + 7 طن سماد عضوي");
        carrot.setWeight_seeds_per_dunum(1.5);
        carrot.setSeedSpecifications("بذور معالجة ومطلية - نسبة إنبات >85% - خالية من الأمراض");
        carrot.setSeedlingPreparation("نقع البذور في ماء دافئ لمدة 12 ساعة");
        carrot.setPlantingDistance("5 سم بين النباتات - 30 سم بين الخطوط");
        carrot.setPlantingDepth("1-1.5 سم في التربة الرملية");
        carrot.setInitialIrrigation("ري خفيف يومياً حتى الإنبات");
        carrot.setDaysToMaturity(80);
        carrot.setHigh("صنف Nantes Superior");
        carrot.setMid("صنف Chantenay Red Core");
        carrot.setLow("صنف محلي قصير");
        carrot.setTemperatureTolerance(10);
        carrot.setHumidityTolerance(25);
        return carrot;
    }

    private byte[] convertImageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


//        حرث الأرض مرتين على الأقل بعمق 25–30 سم، حتى تهوي التربة وتتفتت الكتل.
//
//                تنظيف الأرض من الحشائش والجذور اللي ممكن تعيق نمو الشتلات.
//
//                إضافة السماد العضوي المتحلل (بلدي أو كمبوست) بمعدل 4–5 طن للدونم، وخلطه جيدًا بالتربة.
//
//                لو الأرض ثقيلة، يفضل إضافة رمل أو بيتموس لتحسين التصريف.
//
//                تأكد إن التربة فيها صرف جيد، لأن الطماطم ما بتحب تجمع المياه.
//
//        اضبط حموضة التربة (pH) لتكون بين 6 و6.8، لأنها الأنسب للطماطم.
//
//                أعمل تسوية جيدة للتربة بعد الحراثة حتى تكون الأرض مستوية وسهلة للري والزراعة.
//
//        إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها. بدي الكلام هذا اطبقو في الجافا في مشروع اندرويد تطبيق بس بدي البيانات هذي تتغير بشكل ذكي يعني تكون عبارة عن نص مخزن في ابجكت الطماطم لكن لما بستدعي هذا النص يتم التغيير عليه بناءا على عدة امور منها اضافة السماد الي تم تحديدو اصلا من قبل المزارع في جدول وسيط اس  ويكون ذلك مناسب لاي محصول مش بس الطماطم


    private void addSampleExperts() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.unnamed);
        byte[] imageBytes = convertImageToByteArray(bitmap);
        Expert Expert1 = new Expert("jane_smith", "jane_smith", "asdasdasd", "asdasd", imageBytes, "", "", "", 0);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.te6);
        byte[] imageBytes2 = convertImageToByteArray(bitmap2);
        Expert Expert2 = new Expert("bob_white", "bob_white", "asdasdasd", "asdasd", imageBytes2, "", "", "", 0);
        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.unnamed);
        byte[] imageBytes3 = convertImageToByteArray(bitmap3);
        Expert Expert3 = new Expert("alice_brown", "alice_brown", "asdasdasd", "asdasd", imageBytes3, "", "", "", 0);
        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.unnamed);
        byte[] imageBytes4 = convertImageToByteArray(bitmap4);
        Expert Expert4 = new Expert("john_doe", "john_doe", "asdasdasd", "asdasd", imageBytes4, "", "", "", 0);
        myViewModel.insertExpert(Expert1);
        myViewModel.insertExpert(Expert2);
        myViewModel.insertExpert(Expert3);
        myViewModel.insertExpert(Expert4);


    }


    private void addSampleFarmers() {

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.unnamed);
        byte[] imageBytes = convertImageToByteArray(bitmap);
        //"user_1", "password_1", 1234, 1231231211, "Farmer 1", imageBytes, ""
        Farmer farmer1 = new Farmer();
        farmer1.setFarmer_user_name("user_1");
        farmer1.setFarmer_Password("password_1");
        farmer1.setPhone_nomber(1234);
        farmer1.setS_name("Farmer");
        farmer1.setBio("");
        farmer1.setCard_Number(1231231211);
        myViewModel.insertFarmer(farmer1);
        uploadFarmer(farmer1);


        Farmer farmer2 = new Farmer("user_2", "password_2", 1235, 1231231212, "Farmer 2", null, "");
        myViewModel.insertFarmer(farmer2);
        uploadFarmer(farmer2);


        Farmer farmer3 = new Farmer("user_3", "password_3", 1236, 1231231213, "Farmer 3", null, "");
        myViewModel.insertFarmer(farmer3);

        uploadFarmer(farmer3);


        Farmer farmer4 = new Farmer("user_4", "password_4", 1237, 1231231214, "Farmer 4", null, "");
        myViewModel.insertFarmer(farmer4);

        uploadFarmer(farmer4);


        Farmer farmer5 = new Farmer("user_5", "password_5", 1238, 1231231215, "Farmer 5", null, "");
        myViewModel.insertFarmer(farmer5);
        uploadFarmer(farmer5);


        Farmer farmer6 = new Farmer("user_6", "password_6", 1239, 1231231216, "Farmer 6", null, "");
        myViewModel.insertFarmer(farmer6);
        uploadFarmer(farmer6);


        Farmer farmer7 = new Farmer("user_7", "password_7", 1240, 1231231217, "Farmer 7", null, "");
        myViewModel.insertFarmer(farmer7);
        uploadFarmer(farmer7);


        Farmer farmer8 = new Farmer("user_8", "password_8", 1241, 1231231218, "Farmer 8", null, "");
        myViewModel.insertFarmer(farmer8);
        uploadFarmer(farmer8);


        Farmer farmer9 = new Farmer("user_9", "password_9", 1242, 1231231219, "Farmer 9", null, "");
        myViewModel.insertFarmer(farmer9);

        uploadFarmer(farmer9);

        Farmer farmer10 = new Farmer("user_10", "password_10", 1243, 1231231220, "Farmer 10", null, "");
        myViewModel.insertFarmer(farmer10);

        uploadFarmer(farmer10);

        Farmer farmer11 = new Farmer("user_11", "password_11", 1244, 1231231221, "Farmer 11", null, "");
        myViewModel.insertFarmer(farmer11);
        uploadFarmer(farmer11);


        Farmer farmer12 = new Farmer("user_12", "password_12", 1245, 1231231222, "Farmer 12", null, "");
        myViewModel.insertFarmer(farmer12);

        uploadFarmer(farmer12);


        Farmer farmer13 = new Farmer("user_13", "password_13", 1246, 1231231223, "Farmer 13", null, "");
        myViewModel.insertFarmer(farmer13);

        uploadFarmer(farmer13);


        Farmer farmer14 = new Farmer("user_14", "password_14", 1247, 1231231224, "Farmer 14", null, "");
        myViewModel.insertFarmer(farmer14);
        uploadFarmer(farmer14);


        Farmer farmer15 = new Farmer("user_15", "password_15", 1248, 1231231225, "Farmer 15", null, "");
        myViewModel.insertFarmer(farmer15);
        uploadFarmer(farmer15);


        Farmer farmer16 = new Farmer("user_16", "password_16", 1249, 1231231226, "Farmer 16", null, "");
        myViewModel.insertFarmer(farmer16);
        uploadFarmer(farmer16);


        Farmer farmer17 = new Farmer("user_17", "password_17", 1250, 1231231227, "Farmer 17", null, "");
        myViewModel.insertFarmer(farmer17);

        uploadFarmer(farmer17);


        Farmer farmer18 = new Farmer("user_18", "password_18", 1251, 1231231228, "Farmer 18", null, "");
        myViewModel.insertFarmer(farmer18);

        uploadFarmer(farmer18);


        Farmer farmer19 = new Farmer("user_19", "password_19", 1252, 1231231229, "Farmer 19", null, "");
        myViewModel.insertFarmer(farmer19);
        uploadFarmer(farmer19);


        Farmer farmer20 = new Farmer("user_20", "password_20", 1253, 1231231230, "Farmer 20", null, "");
        myViewModel.insertFarmer(farmer20);
        uploadFarmer(farmer20);


        Farmer farmer21 = new Farmer("user_21", "password_21", 1254, 1231231231, "Farmer 21", null, "");
        myViewModel.insertFarmer(farmer21);
        uploadFarmer(farmer21);

        Farmer farmer22 = new Farmer("user_22", "password_22", 1255, 1231231232, "Farmer 22", null, "");
        myViewModel.insertFarmer(farmer22);
        uploadFarmer(farmer22);


        Farmer farmer23 = new Farmer("user_23", "password_23", 1256, 1231231233, "Farmer 23", null, "");
        myViewModel.insertFarmer(farmer23);
        uploadFarmer(farmer23);

        Farmer farmer24 = new Farmer("user_24", "password_24", 1257, 1231231234, "Farmer 24", null, "");
        myViewModel.insertFarmer(farmer24);
        uploadFarmer(farmer24);


        Farmer farmer25 = new Farmer("user_25", "password_25", 1258, 1231231235, "Farmer 25", null, "");
        myViewModel.insertFarmer(farmer25);
        uploadFarmer(farmer25);

        Farmer farmer26 = new Farmer("user_26", "password_26", 1259, 1231231236, "Farmer 26", null, "");
        myViewModel.insertFarmer(farmer26);
        uploadFarmer(farmer26);


        Farmer farmer27 = new Farmer("user_27", "password_27", 1260, 1231231237, "Farmer 27", null, "");
        myViewModel.insertFarmer(farmer27);
        uploadFarmer(farmer27);


        Farmer farmer28 = new Farmer("user_28", "password_28", 1261, 1231231238, "Farmer 28", null, "");
        myViewModel.insertFarmer(farmer28);
        uploadFarmer(farmer28);


        Farmer farmer29 = new Farmer("user_29", "password_29", 1262, 1231231239, "Farmer 29", null, "");
        myViewModel.insertFarmer(farmer29);
        uploadFarmer(farmer29);


        Farmer farmer30 = new Farmer("user_30", "password_30", 1263, 1231231240, "Farmer 30", null, "");
        myViewModel.insertFarmer(farmer30);
        uploadFarmer(farmer30);


        Farmer farmer31 = new Farmer("user_31", "password_31", 1264, 1231231241, "Farmer 31", null, "");
        myViewModel.insertFarmer(farmer31);
        uploadFarmer(farmer31);


        Farmer farmer32 = new Farmer("user_32", "password_32", 1265, 1231231242, "Farmer 32", null, "");
        myViewModel.insertFarmer(farmer32);
        uploadFarmer(farmer32);


        Farmer farmer33 = new Farmer("user_33", "password_33", 1266, 1231231243, "Farmer 33", null, "");
        myViewModel.insertFarmer(farmer33);
        uploadFarmer(farmer33);

        Farmer farmer34 = new Farmer("user_34", "password_34", 1267, 1231231244, "Farmer 34", null, "");
        myViewModel.insertFarmer(farmer34);
        uploadFarmer(farmer34);


        Farmer farmer35 = new Farmer("user_35", "password_35", 1268, 1231231245, "Farmer 35", null, "");
        myViewModel.insertFarmer(farmer35);
        uploadFarmer(farmer35);


        Farmer farmer36 = new Farmer("user_36", "password_36", 1269, 1231231246, "Farmer 36", null, "");
        myViewModel.insertFarmer(farmer36);
        uploadFarmer(farmer36);


        Farmer farmer37 = new Farmer("user_37", "password_37", 1270, 1231231247, "Farmer 37", null, "");
        myViewModel.insertFarmer(farmer37);
        uploadFarmer(farmer37);


        Farmer farmer38 = new Farmer("user_38", "password_38", 1271, 1231231248, "Farmer 38", null, "");
        myViewModel.insertFarmer(farmer38);
        uploadFarmer(farmer38);


        Farmer farmer39 = new Farmer("user_39", "password_39", 1272, 1231231249, "Farmer 39", null, "");
        myViewModel.insertFarmer(farmer39);
        uploadFarmer(farmer39);


        Farmer farmer40 = new Farmer("user_40", "password_40", 1273, 1231231250, "Farmer 40", null, "");
        myViewModel.insertFarmer(farmer40);
        uploadFarmer(farmer40);


        Farmer farmer41 = new Farmer("user_41", "password_41", 1274, 1231231251, "Farmer 41", null, "");
        myViewModel.insertFarmer(farmer41);
        uploadFarmer(farmer41);


        Farmer farmer42 = new Farmer("user_42", "password_42", 1275, 1231231252, "Farmer 42", null, "");
        myViewModel.insertFarmer(farmer42);
        uploadFarmer(farmer42);


        Farmer farmer43 = new Farmer("user_43", "password_43", 1276, 1231231253, "Farmer 43", null, "");
        myViewModel.insertFarmer(farmer43);
        uploadFarmer(farmer43);


        Farmer farmer44 = new Farmer("user_44", "password_44", 1277, 1231231254, "Farmer 44", null, "");
        myViewModel.insertFarmer(farmer44);
        uploadFarmer(farmer44);


        Farmer farmer45 = new Farmer("user_45", "password_45", 1278, 1231231255, "Farmer 45", null, "");
        myViewModel.insertFarmer(farmer45);
        uploadFarmer(farmer45);


        Farmer farmer46 = new Farmer("user_46", "password_46", 1279, 1231231256, "Farmer 46", null, "");
        myViewModel.insertFarmer(farmer46);
        uploadFarmer(farmer46);


        Farmer farmer47 = new Farmer("user_47", "password_47", 1280, 1231231257, "Farmer 47", null, "");
        myViewModel.insertFarmer(farmer47);
        uploadFarmer(farmer47);


        Farmer farmer48 = new Farmer("user_48", "password_48", 1281, 1231231258, "Farmer 48", null, "");
        myViewModel.insertFarmer(farmer48);
        uploadFarmer(farmer48);


        Farmer farmer49 = new Farmer("user_49", "password_49", 1282, 1231231259, "Farmer 49", null, "");
        myViewModel.insertFarmer(farmer49);
        uploadFarmer(farmer49);


        Farmer farmer50 = new Farmer("user_50", "password_50", 1283, 1231231260, "Farmer 50", null, "");
        myViewModel.insertFarmer(farmer50);
        uploadFarmer(farmer50);


    }
}


