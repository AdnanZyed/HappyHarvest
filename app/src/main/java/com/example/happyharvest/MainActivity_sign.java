package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseDatabaseHelper firebaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainSignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myViewModel = new ViewModelProvider(this).get(My_View_Model.class);

//
//        database = FirebaseDatabase.getInstance("https://happy-harvest-2271a-default-rtdb.europe-west1.firebasedatabase.app/");
//        dbRef = database.getReference("crops"); // لازم تعملها قبل push()

        // الحصول على نسخة Singleton من FirebaseDatabaseHelper
        firebaseHelper = FirebaseDatabaseHelper.getInstance();
//        addSampleExperts();
//        addSampleFarmers();
        Crop onion = createOnionCrop();
        Crop tomato = createTomatoCrop();
        Crop eggplant = createEggplantCrop();
        Crop garlic = createGarlicCrop();
        Crop carrot = createCarrotCrop();


//        uploadCrop(onion);
//        uploadCrop(tomato);
//        uploadCrop(eggplant);
//        uploadCrop(garlic);
//        uploadCrop(carrot);

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

    private Crop createTomatoCrop() {

        Crop tomato = new Crop();
        tomato.setCrop_NAME("Tomato");
        tomato.setCategorie("Irrigated fruits Vegetable seasonal Highdemand");
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
        onion.setCategorie("Bulb Vegetable seasonal Highdemand");  //التصنيفاتRoot grain Irrigated fruits Vegetable Bulb seasonal Highdemand
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

    private Crop createEggplantCrop() {

        Crop eggplant = new Crop();
        eggplant.setCrop_NAME("Eggplant");
        eggplant.setCategorie("Irrigated Vegetable seasonal Highdemand");
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
        garlic.setCategorie("Bulb Vegetable seasonal Highdemand");
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
        carrot.setCategorie("Root Vegetable seasonal Highdemand");
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

//        addSampleExperts();
//
//
//        new Thread(() -> {
//            myViewModel.insertCrop(onion);
//        }).start();
//
//        List<Crop> cropsList = new ArrayList<>();
//        cropsList.add(onion);
//
//        for (Crop crop : cropsList) {
//            String id = dbRef.push().getKey();
//            if (id != null) {
//                dbRef.child(id).setValue(crop).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(MainActivity_sign.this, "✅ تم رفع المحصول", Toast.LENGTH_SHORT).show();
//                        Log.d("Firebase", "Crop successfully uploaded");
//
//                    } else {
//                        Toast.makeText(MainActivity_sign.this, "❌ فشل في رفع المحصول", Toast.LENGTH_SHORT).show();
//                        Log.e("Firebase", "Error uploading crop: " + task.getException());
//
//                    }
//                });
//            }
//        }
//
//        Toast.makeText(MainActivity_sign.this, "جاري رفع " + cropsList.size() + " محاصيل...", Toast.LENGTH_SHORT).show();
//
//        addSampleFarmers();

//        Crop onion = new Crop(
//                0,//دائما صفر
//                "Onion",//اسم المحصول
//                //   null,
//                "Seasonal crops - Root crops - High-demand crops",
//                //التصنيفات الي بيدخل فيها المحصول
//                "البصل من المحاصيل الجذرية ويُزرع في الخريف أو الشتاء ويحتاج إلى تربة جيدة التصريف.",
//                //شرح مبسط وموجز للمحصول
//                //null,
//
//                "jane_smith",//اسم الخبير مبدئيا ثابت
//                // null,
//                0,//دائما صفر
//
//                "sandy",//التربة المفضلة للمحصول
//                "muddy",//التربة المسموحة للمحصول
//                "rocky",//التربة المرفوضة للمحصول
//
//                "drip",//طريقة الري المفضلة
//                "sprinkler",//طريقة الري المسوحة
//                "immersion",//طريقة الري المرفوضة
//
//                0.3,//اقل مساحة مسموحة للزراعة
//                "spring",//الفصل المفضل زراعة المحصول الحالي فيه
//
//                "High",//الرطوبة المفضلة
//                "Low",//الرطوبة المسموحة
//                "Moderate",//الرطوبة المرفوضة
//
//                "Cool",//الحرارة المفضلة
//                "Mild",//الحرارة المسموحة
//                "Hot",//الحرارة المرفوضة
//                //اذا كانت اقل 5 درجات cool
//                //5_30  Mild
//                //5_30  Hot
//
//                0,//دائما صفر
//                3,//فترة الري
//                14,//فترة التسميد
//
//                "\uD83D\uDEB0 برنامج الري:\n" +
//                        "\n" +
//                        "مرحلة النمو: ري كل 3 أيام (تجنب تبليل الأوراق)\n" +
//                        "\n" +
//                        "مرحلة الإثمار: ري يومي في الصيف (صباحاً)",
//
//                //الري
//
//                "\uD83C\uDF31 برنامج التسميد:\n" +
//                        "\n" +
//                        "قبل الزراعة: سماد عضوي + سوبر فوسفات\n" +
//                        "\n" +
//                        "بعد 3 أسابيع: NPK (19-19-19)\n" +
//                        "\n" +
//                        "أثناء الإزهار: رش بورون + كالسيوم\n" +
//                        "\n" +
//                        "⚠\uFE0F تحذير: الإفراط في النيتروجين يقلل الإثمار!\n" +
//                        "\n",
//                //التسميد
//
//
//                "\uD83C\uDF31 دليل شامل لزراعة الطماطم: من البذور إلى الحصاد\n" +
//                        "\uD83D\uDCCC 1. اختيار الصنف المناسب\n" +
//                        "\uD83D\uDD39 أصناف محلية: مثل \"هجين ف1 448\" (مقاوم للأمراض)\n" +
//                        "\uD83D\uDD39 أصناف عالمية: مثل \"بيف ستيك\" (للعصير) أو \"تشيري\" (للسلطات)\n" +
//                        "\uD83D\uDD39 اختيار حسب الغرض:\n" +
//                        "\n" +
//                        "\uD83E\uDD6B للعصير: أصناف ذات لب كثيف (سان مارزانو)\n" +
//                        "\n" +
//
//                        "\uD83E\uDD57 للطازج: أصناف حلوة (هجين 023)\n" +
//                        "\n" +
//                        "\uD83C\uDF3F 2. تحضير التربة\n" +
//                        "✅ المواصفات المثالية:\n" +
//                        "\n" +
//                        "درجة حموضة (pH 6-6.8)\n" +
//                        "\n" +
//                        "تربة جيدة الصرف (مخلوطة بـ 30% رمل)\n" +
//                        "\n" +
//                        "غنية بالمادة العضوية (أضف 10 كجم سماد بلدي/م²)\n" +
//                        "\n" +
//                        "⚠\uFE0F تحذير: تجنب التربة الطينية الثقيلة!\n" +
//                        "\n" +
//                        "\uD83C\uDF31 3. طرق الزراعة\n" +
//                        "الطريقة\tالتفاصيل\tالوقت المناسب\n" +
//                        "البذور\tتنقع في ماء دافئ (24 ساعة) ثم تزرع في أصص\tقبل 6-8 أسابيع من الصقيع الأخير\n" +
//                        "الشتل\tنقل الشتلات عند طول 15-20 سم\tبعد زوال خطر الصقيع\n" +
//                        "الزراعة المباشرة\t3 بذور/جورة بعمق 1 سم\tعندما تصل درجة الحرارة لـ 18°م ليلاً\n" +
//                        "\uD83D\uDD39 مسافات الزراعة:\n" +
//                        "\n" +
//                        "50-60 سم بين النباتات\n" +
//                        "\n" +
//                        "90-100 سم بين الخطوط\n" +
//                        "\n",
//
//
//                //بيانات اخرى
//
//
//                "\n" +
//                        "\uD83C\uDF45 مشاكل زراعة الطماطم وحلولها\n" +
//                        "1\uFE0F⃣ مشكلة: تعفن الطرف الزهري (Blossom End Rot)\n" +
//                        "الأعراض:\n" +
//                        "\n" +
//                        "ظهور بقع سوداء أو بنية داكنة عند طرف الثمرة\n" +
//                        "\n" +
//                        "جفاف المنطقة المصابة وتصلبها\n" +
//                        "\n" +
//                        "\uD83D\uDEE0\uFE0F الحلول:\n" +
//                        "✅ التوازن الكالسيومي: أضف الجير الزراعي أو رش نترات الكالسيوم (5 جم/لتر ماء)\n" +
//                        "✅ الري المنتظم: حافظ على رطوبة التربة (لا تجف تمامًا)\n" +
//                        "✅ التسميد المتوازن: تجنب الإفراط في الأسمدة النيتروجينية\n" +
//                        "\n"
//                        + "\uD83D\uDD04 الدورة الزراعية: تغيير المحصول كل موسم\n" +
//                        "\n" +
//                        "\uD83E\uDDEA تحليل التربة: قبل الزراعة لتحديد الاحتياجات\n" +
//                        "\n" +
//                        "\uD83D\uDC1D جذب الملقحات: بزراعة نباتات الزينة حول الحقل\n" +
//                        "\n" +
//                        "\uD83D\uDCC5 الزراعة في موعدها: حسب التقويم الزراعي للمنطقة\n" +
//                        "\n" +
//                        "\uD83D\uDC69\u200D\uD83C\uDF3E المتابعة اليومية: لاكتشاف المشاكل مبكراً\n" +
//                        "\n" +
//                        "\uD83D\uDCA1 إحصائية مهمة: 80% من مشاكل المحاصيل تنتج عن سوء إدارة الري والتسميد!",
//
//                //  مشاكل وحلول
//
//                "\uD83D\uDD04  التقليم والتوجيه\n" +
//                        "✂\uFE0F تقليم السرطانات: إزالة الفروع الجانبية تحت أول عنقود زهري\n" +
//                        "\uD83C\uDF3F التدعيم: استخدام أوتاد خشبية أو شباك تعليق\n" +
//                        "\n" +
//                        "طريقة التدلي: لف الساق حول الخيط كل أسبوع\n" +
//                        "\n",
////طريفة التقليم والتوجيه
//
//
//                "Available",//وفرة المياه المضلة
//                "Limited",//وفرة المياه المسموحة
//                "Unavailable",//وفرة المياه المرفوضة
////null,
//                "\uD83D\uDC1B 5. المكافحة المتكاملة للآفات\n" +
//                        "الآفة\tالعلاج\tالوقاية\n" +
//                        "الذبابة البيضاء\tرش زيت النيم (2 مل/لتر)\tمصائد صفراء لاصقة\n" +
//                        "التعفن البكتيري\tرش نحاس (كوسيد 101)\tتعقيم الأدوات\n" +
//                        "ديدان الثمار\tBacillus thuringiensis\tتغطية الثمار بأكياس ورقية\n" +
//                        "\uD83C\uDF1E 6. الظروف البيئية المثلى\n" +
//                        "☀\uFE0F الحرارة:\n" +
//                        "\n" +
//                        "النهار: 25-30°م\n" +
//                        "\n" +
//                        "الليل: 15-18°م\n" +
//                        "\n" +
//                        "\uD83D\uDCA1 الإضاءة: 8 ساعات ضوء يومياً على الأقل\n" +
//                        "\n" +
//                        "\uD83C\uDF27\uFE0F الرطوبة: 60-70% (استخدم مراوح في البيوت المحمية إذا زادت)\n" +
//                        "\n" +
//                        "\uD83D\uDED1 8. المشاكل الشائعة وحلولها\n" +
//                        "المشكلة\tالسبب\tالحل\n" +
//                        "تساقط الأزهار\tحرارة >35°م أو رطوبة عالية\tرش هرمون عقد الثمار (NAA)\n" +
//                        "تشقق الثمار\tري غير منتظم\tالري بانتظام + تغطية التربة\n" +
//                        "بقع صفراء على الأوراق\tنقص مغنيسيوم\tرش كبريتات ماغنيسيوم (2 جم/لتر)\n" +
//                        "\uD83D\uDCC5 9. مواعيد الزراعة حسب المناطق\n" +
//                        "المنطقة\tالموعد المثالي\n" +
//                        "المناطق الدافئة\tسبتمبر-أكتوبر\n" +
//                        "المناطق المعتدلة\tفبراير-مارس\n" +
//                        "البيوت المحمية\tطوال العام (مع تحكم بالمناخ)\n" +
//                        "\uD83E\uDDFA 10. الحصاد والتخزين\n" +
//                        "\uD83D\uDCC5 موعد الحصاد: بعد 70-90 يوم من الزراعة\n" +
//                        "\uD83D\uDD39 علامات النضج:\n" +
//                        "\n" +
//                        "لون أحمر متجانس\n" +
//                        "\n" +
//                        "ملمس طري عند الضغط الخفيف\n" +
//                        "\n" +
//                        "❄\uFE0F التخزين:\n" +
//                        "\n" +
//                        "الطازجة: 10-12°م (تجنب الثلاجة!)\n" +
//                        "\n" +
//                        "التجفيف: تقطع شرائح وتجفف بالشمس 3 أيام\n" +
//                        "\n" +
//                        "\uD83C\uDFAF نصائح الخبراء\n" +
//                        "لثمار أكثر حلاوة: أضف ملعقة صغيرة ملح إنجليزي/لتر ماء عند الري\n" +
//                        "\n" +
//                        "لمكافحة النيماتودا: زراعة القطيفة (Marigold) بين الخطوط\n" +
//                        "\n" +
//                        "لزيادة العقد: هز النباتات برفق عند الصباح لنقل اللقاح\n" +
//                        "\n" +
//                        "حقيقة ممتعة: \uD83E\uDDEA الطماطم تنتج هرمون \"الإيثيلين\" الذي يساعد في نضج الفواكه الأخرى!\n" +
//                        "\n" +
//                        "باستخدام هذا الدليل، يمكنك الحصول على إنتاج وفير بجودة عالية \uD83C\uDF1F\uD83C\uDF45."
//
//
//                , 20//درجة الحرارة المفضلة
//                , 50// نسبة الرطوبة المفضلة
//                , 3//متطلبات الضوء

    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                "",
    /// /                ""
//                , 1500
//                //عدد الشتلات التي يمكن زراعتها في الدونوم الواحد
//                , "ارض مكشوفة_بيت بلاستيكي_زراعة مائية_زراعة عضوية"
//                //طريقة الزراعة ممكن ازرعو باكثر من طريقة حسب نوع المحصول
//
//                , 12.0
//                // كمية السماد الكيميائي بالقرام لكل شتلة
//                , "Chemical",
//                //نوع السماد المستحسن للمحصول الحالي ممكن يتم تسميدو باكثر بالطريقتين
//
//                150.0
//                //كمية السماد العضوي بالقرام لكل شتلة
//                , "الخس، السبانخ، الجرجير، البقدونس، الكزبرة، النعناع، البرسيم، الحلبة، الشوفان (كغطاء أخضر)، الذرة الرفيعة، الدخن، عباد الشمس",
//                "الذرة، القمح، الشعير، الشوفان، العدس، الفول، الحمص، الفاصوليا، البازلاء، البطيخ، الشمام، الخيار، الكوسا، الباذنجان، الفلفل، الطماطم"
//
//                , "الثوم، الكراث، البصل، البطاطا، البطاطس، الفجل، اللفت، الجزر، الشمندر، الكرنب، القرنبيط، البروكلي",
//                "حرث الأرض مرتين على الأقل بعمق 25–30 سم، حتى تهوي التربة وتتفتت الكتل.\n" +
//                        "\n" +
//                        "تنظيف الأرض من الحشائش والجذور اللي ممكن تعيق نمو الشتلات.\n" +
//                        "\n" +
//                        "إضافة السماد العضوي المتحلل (بلدي أو كمبوست) بمعدل 4–5 طن للدونم، وخلطه جيدًا بالتربة.\n" +
//                        "\n" +
//                        "لو الأرض ثقيلة، يفضل إضافة رمل أو بيتموس لتحسين التصريف.\n" +
//                        "\n" +
//                        "تأكد إن التربة فيها صرف جيد، لأن الطماطم ما بتحب تجمع المياه.\n" +
//                        "\n" +
//                        "اضبط حموضة التربة (pH) لتكون بين 6 و6.8، لأنها الأنسب للطماطم.\n" +
//                        "\n" +
//                        "أعمل تسوية جيدة للتربة بعد الحراثة حتى تكون الأرض مستوية وسهلة للري والزراعة.\n" +
//                        "\n", "إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها."
//
//                , "إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها."
//                , "بذور محسنة ومعتمدة.\n" +
//                "\n" +
//                "مقاومة للأمراض الشائعة.\n" +
//                "\n" +
//                "نسبة إنبات عالية.\n" +
//                "\n" +
//                "شراءها من مصدر موثوق."
//                , "إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.",
//
//                3,
//                "اختر بذور بصل عالية الجودة ومعتمدة، خالية من الأمراض، وذات حجم متوسط للحصول على نمو متجانس. \uD83E\uDDC5",
//                "قم بنقع البذور في ماء فاتر لمدة 12 ساعة قبل الزراعة، ويمكن تجهيز الشتلات في مشتل لمدة 6 أسابيع قبل النقل. \uD83C\uDF31",
//                "اترك مسافة 10-15 سم بين كل نبات، و30-40 سم بين الخطوط لضمان التهوية الجيدة ونمو البصل بشكل مناسب. \uD83D\uDCCF",
//                "ازرع البذور أو الشتلات على عمق 2-3 سم فقط في التربة لتسهيل الإنبات والنمو. ⛏\uFE0F",
//                "\"بعد الزراعة، اسقِ الأرض مباشرة بريّة غمر خفيفة لتثبيت البذور أو الشتلات في التربة. \uD83D\uDCA7",
//                100, "الابيض ",
//                "الاحمر ",
//                "الاصفر"
//                , 10, 30
//        );
//
    private byte[] convertImageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void uploadCrop(Crop crop) {
        Toast.makeText(this, "جاري رفع المحصول...", Toast.LENGTH_SHORT).show();

        firebaseHelper.addCrop(crop, new FirebaseDatabaseHelper.OnCompleteListener<Void>() {
            @Override
            public void onComplete(boolean success, Exception exception) {
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(MainActivity_sign.this, "✅ تم رفع المحصول", Toast.LENGTH_SHORT).show();
                        Log.d("Firebase", "Crop successfully uploaded");
                    } else {
                        Toast.makeText(MainActivity_sign.this, "❌ فشل في رفع المحصول", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error uploading crop", exception);
                    }
                });
            }
        });

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
        Farmer farmer1 = new Farmer("user_1", "password_1", 1234, 1231231211, "Farmer 1", imageBytes, "");
        myViewModel.insertFarmer(farmer1);


        Farmer_Expert farmerExpert1 = new Farmer_Expert(0, "user_1", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert1);

        Farmer farmer2 = new Farmer("user_2", "password_2", 1235, 1231231212, "Farmer 2", null, "");
        myViewModel.insertFarmer(farmer2);
        Farmer_Expert farmerExpert2 = new Farmer_Expert(0, "user_2", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert2);

        Farmer farmer3 = new Farmer("user_3", "password_3", 1236, 1231231213, "Farmer 3", null, "");
        myViewModel.insertFarmer(farmer3);
        Farmer_Expert farmerExpert3 = new Farmer_Expert(0, "user_3", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert3);

        Farmer farmer4 = new Farmer("user_4", "password_4", 1237, 1231231214, "Farmer 4", null, "");
        myViewModel.insertFarmer(farmer4);
        Farmer_Expert farmerExpert4 = new Farmer_Expert(0, "user_4", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert4);

        Farmer farmer5 = new Farmer("user_5", "password_5", 1238, 1231231215, "Farmer 5", null, "");
        myViewModel.insertFarmer(farmer5);
        Farmer_Expert farmerExpert5 = new Farmer_Expert(0, "user_5", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert5);

        Farmer farmer6 = new Farmer("user_6", "password_6", 1239, 1231231216, "Farmer 6", null, "");
        myViewModel.insertFarmer(farmer6);
        Farmer_Expert farmerExpert6 = new Farmer_Expert(0, "user_6", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert6);

        Farmer farmer7 = new Farmer("user_7", "password_7", 1240, 1231231217, "Farmer 7", null, "");
        myViewModel.insertFarmer(farmer7);
        Farmer_Expert farmerExpert7 = new Farmer_Expert(0, "user_7", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert7);

        Farmer farmer8 = new Farmer("user_8", "password_8", 1241, 1231231218, "Farmer 8", null, "");
        myViewModel.insertFarmer(farmer8);
        Farmer_Expert farmerExpert8 = new Farmer_Expert(0, "user_8", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert8);

        Farmer farmer9 = new Farmer("user_9", "password_9", 1242, 1231231219, "Farmer 9", null, "");
        myViewModel.insertFarmer(farmer9);
        Farmer_Expert farmerExpert9 = new Farmer_Expert(0, "user_9", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert9);

        Farmer farmer10 = new Farmer("user_10", "password_10", 1243, 1231231220, "Farmer 10", null, "");
        myViewModel.insertFarmer(farmer10);
        Farmer_Expert farmerExpert10 = new Farmer_Expert(0, "user_10", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert10);

        Farmer farmer11 = new Farmer("user_11", "password_11", 1244, 1231231221, "Farmer 11", null, "");
        myViewModel.insertFarmer(farmer11);
        Farmer_Expert farmerExpert11 = new Farmer_Expert(0, "user_11", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert11);

        Farmer farmer12 = new Farmer("user_12", "password_12", 1245, 1231231222, "Farmer 12", null, "");
        myViewModel.insertFarmer(farmer12);
        Farmer_Expert farmerExpert12 = new Farmer_Expert(0, "user_12", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert12);

        Farmer farmer13 = new Farmer("user_13", "password_13", 1246, 1231231223, "Farmer 13", null, "");
        myViewModel.insertFarmer(farmer13);
        Farmer_Expert farmerExpert13 = new Farmer_Expert(0, "user_13", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert13);

        Farmer farmer14 = new Farmer("user_14", "password_14", 1247, 1231231224, "Farmer 14", null, "");
        myViewModel.insertFarmer(farmer14);
        Farmer_Expert farmerExpert14 = new Farmer_Expert(0, "user_14", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert14);

        Farmer farmer15 = new Farmer("user_15", "password_15", 1248, 1231231225, "Farmer 15", null, "");
        myViewModel.insertFarmer(farmer15);
        Farmer_Expert farmerExpert15 = new Farmer_Expert(0, "user_15", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert15);

        Farmer farmer16 = new Farmer("user_16", "password_16", 1249, 1231231226, "Farmer 16", null, "");
        myViewModel.insertFarmer(farmer16);
        Farmer_Expert farmerExpert16 = new Farmer_Expert(0, "user_16", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert16);

        Farmer farmer17 = new Farmer("user_17", "password_17", 1250, 1231231227, "Farmer 17", null, "");
        myViewModel.insertFarmer(farmer17);
        Farmer_Expert farmerExpert17 = new Farmer_Expert(0, "user_17", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert17);

        Farmer farmer18 = new Farmer("user_18", "password_18", 1251, 1231231228, "Farmer 18", null, "");
        myViewModel.insertFarmer(farmer18);
        Farmer_Expert farmerExpert18 = new Farmer_Expert(0, "user_18", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert18);

        Farmer farmer19 = new Farmer("user_19", "password_19", 1252, 1231231229, "Farmer 19", null, "");
        myViewModel.insertFarmer(farmer19);
        Farmer_Expert farmerExpert19 = new Farmer_Expert(0, "user_19", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert19);

        Farmer farmer20 = new Farmer("user_20", "password_20", 1253, 1231231230, "Farmer 20", null, "");
        myViewModel.insertFarmer(farmer20);
        Farmer_Expert farmerExpert20 = new Farmer_Expert(0, "user_20", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert20);

        Farmer farmer21 = new Farmer("user_21", "password_21", 1254, 1231231231, "Farmer 21", null, "");
        myViewModel.insertFarmer(farmer21);
        Farmer_Expert farmerExpert21 = new Farmer_Expert(0, "user_21", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert21);

        Farmer farmer22 = new Farmer("user_22", "password_22", 1255, 1231231232, "Farmer 22", null, "");
        myViewModel.insertFarmer(farmer22);
        Farmer_Expert farmerExpert22 = new Farmer_Expert(0, "user_22", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert22);

        Farmer farmer23 = new Farmer("user_23", "password_23", 1256, 1231231233, "Farmer 23", null, "");
        myViewModel.insertFarmer(farmer23);
        Farmer_Expert farmerExpert23 = new Farmer_Expert(0, "user_23", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert23);

        Farmer farmer24 = new Farmer("user_24", "password_24", 1257, 1231231234, "Farmer 24", null, "");
        myViewModel.insertFarmer(farmer24);
        Farmer_Expert farmerExpert24 = new Farmer_Expert(0, "user_24", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert24);

        Farmer farmer25 = new Farmer("user_25", "password_25", 1258, 1231231235, "Farmer 25", null, "");
        myViewModel.insertFarmer(farmer25);
        Farmer_Expert farmerExpert25 = new Farmer_Expert(0, "user_25", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert25);

        Farmer farmer26 = new Farmer("user_26", "password_26", 1259, 1231231236, "Farmer 26", null, "");
        myViewModel.insertFarmer(farmer26);
        Farmer_Expert farmerExpert26 = new Farmer_Expert(0, "user_26", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert26);

        Farmer farmer27 = new Farmer("user_27", "password_27", 1260, 1231231237, "Farmer 27", null, "");
        myViewModel.insertFarmer(farmer27);
        Farmer_Expert farmerExpert27 = new Farmer_Expert(0, "user_27", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert27);

        Farmer farmer28 = new Farmer("user_28", "password_28", 1261, 1231231238, "Farmer 28", null, "");
        myViewModel.insertFarmer(farmer28);
        Farmer_Expert farmerExpert28 = new Farmer_Expert(0, "user_28", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert28);

        Farmer farmer29 = new Farmer("user_29", "password_29", 1262, 1231231239, "Farmer 29", null, "");
        myViewModel.insertFarmer(farmer29);
        Farmer_Expert farmerExpert29 = new Farmer_Expert(0, "user_29", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert29);

        Farmer farmer30 = new Farmer("user_30", "password_30", 1263, 1231231240, "Farmer 30", null, "");
        myViewModel.insertFarmer(farmer30);
        Farmer_Expert farmerExpert30 = new Farmer_Expert(0, "user_30", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert30);

        Farmer farmer31 = new Farmer("user_31", "password_31", 1264, 1231231241, "Farmer 31", null, "");
        myViewModel.insertFarmer(farmer31);
        Farmer_Expert farmerExpert31 = new Farmer_Expert(0, "user_31", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert31);

        Farmer farmer32 = new Farmer("user_32", "password_32", 1265, 1231231242, "Farmer 32", null, "");
        myViewModel.insertFarmer(farmer32);
        Farmer_Expert farmerExpert32 = new Farmer_Expert(0, "user_32", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert32);

        Farmer farmer33 = new Farmer("user_33", "password_33", 1266, 1231231243, "Farmer 33", null, "");
        myViewModel.insertFarmer(farmer33);
        Farmer_Expert farmerExpert33 = new Farmer_Expert(0, "user_33", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert33);

        Farmer farmer34 = new Farmer("user_34", "password_34", 1267, 1231231244, "Farmer 34", null, "");
        myViewModel.insertFarmer(farmer34);
        Farmer_Expert farmerExpert34 = new Farmer_Expert(0, "user_34", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert34);

        Farmer farmer35 = new Farmer("user_35", "password_35", 1268, 1231231245, "Farmer 35", null, "");
        myViewModel.insertFarmer(farmer35);
        Farmer_Expert farmerExpert35 = new Farmer_Expert(0, "user_35", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert35);

        Farmer farmer36 = new Farmer("user_36", "password_36", 1269, 1231231246, "Farmer 36", null, "");
        myViewModel.insertFarmer(farmer36);
        Farmer_Expert farmerExpert36 = new Farmer_Expert(0, "user_36", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert36);

        Farmer farmer37 = new Farmer("user_37", "password_37", 1270, 1231231247, "Farmer 37", null, "");
        myViewModel.insertFarmer(farmer37);
        Farmer_Expert farmerExpert37 = new Farmer_Expert(0, "user_37", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert37);

        Farmer farmer38 = new Farmer("user_38", "password_38", 1271, 1231231248, "Farmer 38", null, "");
        myViewModel.insertFarmer(farmer38);
        Farmer_Expert farmerExpert38 = new Farmer_Expert(0, "user_38", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert38);

        Farmer farmer39 = new Farmer("user_39", "password_39", 1272, 1231231249, "Farmer 39", null, "");
        myViewModel.insertFarmer(farmer39);
        Farmer_Expert farmerExpert39 = new Farmer_Expert(0, "user_39", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert39);

        Farmer farmer40 = new Farmer("user_40", "password_40", 1273, 1231231250, "Farmer 40", null, "");
        myViewModel.insertFarmer(farmer40);
        Farmer_Expert farmerExpert40 = new Farmer_Expert(0, "user_40", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert40);

        Farmer farmer41 = new Farmer("user_41", "password_41", 1274, 1231231251, "Farmer 41", null, "");
        myViewModel.insertFarmer(farmer41);
        Farmer_Expert farmerExpert41 = new Farmer_Expert(0, "user_41", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert41);

        Farmer farmer42 = new Farmer("user_42", "password_42", 1275, 1231231252, "Farmer 42", null, "");
        myViewModel.insertFarmer(farmer42);
        Farmer_Expert farmerExpert42 = new Farmer_Expert(0, "user_42", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert42);

        Farmer farmer43 = new Farmer("user_43", "password_43", 1276, 1231231253, "Farmer 43", null, "");
        myViewModel.insertFarmer(farmer43);
        Farmer_Expert farmerExpert43 = new Farmer_Expert(0, "user_43", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert43);

        Farmer farmer44 = new Farmer("user_44", "password_44", 1277, 1231231254, "Farmer 44", null, "");
        myViewModel.insertFarmer(farmer44);
        Farmer_Expert farmerExpert44 = new Farmer_Expert(0, "user_44", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert44);

        Farmer farmer45 = new Farmer("user_45", "password_45", 1278, 1231231255, "Farmer 45", null, "");
        myViewModel.insertFarmer(farmer45);
        Farmer_Expert farmerExpert45 = new Farmer_Expert(0, "user_45", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert45);

        Farmer farmer46 = new Farmer("user_46", "password_46", 1279, 1231231256, "Farmer 46", null, "");
        myViewModel.insertFarmer(farmer46);
        Farmer_Expert farmerExpert46 = new Farmer_Expert(0, "user_46", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert46);

        Farmer farmer47 = new Farmer("user_47", "password_47", 1280, 1231231257, "Farmer 47", null, "");
        myViewModel.insertFarmer(farmer47);
        Farmer_Expert farmerExpert47 = new Farmer_Expert(0, "user_47", "john_doe");
        myViewModel.insertFarmerExpert(farmerExpert47);

        Farmer farmer48 = new Farmer("user_48", "password_48", 1281, 1231231258, "Farmer 48", null, "");
        myViewModel.insertFarmer(farmer48);
        Farmer_Expert farmerExpert48 = new Farmer_Expert(0, "user_48", "alice_brown");
        myViewModel.insertFarmerExpert(farmerExpert48);

        Farmer farmer49 = new Farmer("user_49", "password_49", 1282, 1231231259, "Farmer 49", null, "");
        myViewModel.insertFarmer(farmer49);
        Farmer_Expert farmerExpert49 = new Farmer_Expert(0, "user_49", "bob_white");
        myViewModel.insertFarmerExpert(farmerExpert49);

        Farmer farmer50 = new Farmer("user_50", "password_50", 1283, 1231231260, "Farmer 50", null, "");
        myViewModel.insertFarmer(farmer50);
        Farmer_Expert farmerExpert50 = new Farmer_Expert(0, "user_50", "jane_smith");
        myViewModel.insertFarmerExpert(farmerExpert50);

    }
}


//        cropsList.add(wheat);
//        cropsList.add(saffron);
//        cropsList.add(lentils);
//        cropsList.add(barley);
//        cropsList.add(carrot)
//        cropsList.add(onion);
//        cropsList.add(pumpkin);
//        cropsList.add(sunflower);
//        cropsList.add(broccoli);
//        cropsList.add(peas);
//        cropsList.add(alfalfa);
//        cropsList.add(bellPepper);
//        cropsList.add(mint);
//        cropsList.add(cauliflower);
//        cropsList.add(banana);
//        cropsList.add(watermelon);
//        cropsList.add(cucumber);
//        cropsList.add(garlic);
//        cropsList.add(potato);
//        cropsList.add(corn);
//        cropsList.add(lettuce);
//        cropsList.add(spinach);
//        cropsList.add(tomato);
//        cropsList.add(strawberry);
//        cropsList.add(eggplant);
//        cropsList.add(peach);
//        cropsList.add(zucchini);
//        cropsList.add(cabbage);
//        cropsList.add(melon);
//        cropsList.add(cucumber);
//        cropsList.add(pumpkin);
//        cropsList.add(sunflower);
//        cropsList.add(broccoli);
//        cropsList.add(peas);

//        dbRef = database.getReference("crops"); // مسار المزروعات
//        Crop onion = new Crop(
//                0,
//                "Onion",
//                null,
//                "Seasonal crops - Root crops - High-demand crops",
//                "البصل من المحاصيل الجذرية ويُزرع في الخريف أو الشتاء ويحتاج إلى تربة جيدة التصريف.",
//                "",
//                null,
//                false,
//                false,
//                false,
//                "jane_smith",
//                null,
//                0,
//                "sandy",
//                "loamy",
//                "clay",
//                "drip",
//                "sprinkler",
//                "immersion",
//                0.3,
//                "spring",/*fall*/
//                "Moderate",
//                "Low",
//                "High",
//                "Cool",
//                "Mild",
//                "Hot",
//                0,
//                3,
//                14,
//                "Irrigate every few days during bulb formation.",
//                "Use phosphorus-rich fertilizer.",
//                "Transplanting or seed",
//                "Available",
//                "Limited",
//                "Unavailable"
//        );
//        String id = dbRef.push().getKey(); // إنشاء ID تلقائي
//        if (id != null) {
//            dbRef.child(id).setValue(onion).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity_sign.this, "✅ تم رفع المحصول", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity_sign.this, "❌ فشل في رفع المحصول", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("crops");
//
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Crop> cropsFromFirebase = new ArrayList<>();
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    Crop crop = snap.getValue(Crop.class);
//                    if (crop != null) cropsFromFirebase.add(crop);
//                }
//
//                // إدخال البيانات إلى Room
//                new Thread(() -> {
//                    //Crop_Dao.syncWithFirebase(cropsFromFirebase);
//                    myViewModel.syncWithFirebase();
//
//                }).start();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("FirebaseError", "فشل في تحميل المحاصيل: ", error.toException());
//            }
//        });
//        GenerativeModel ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
//                .generativeModel("gemini-2.0-flash");
//        GenerativeModelFutures model = GenerativeModelFutures.from(ai);


//private void addSampleCrops() {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.a);
//        byte[] imageBytes = convertImageToByteArray(bitmap);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.s);
//        byte[] imageBytes1 = convertImageToByteArray(bitmap1);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.d);
//        byte[] imageBytes2 = convertImageToByteArray(bitmap2);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y1);
//        byte[] imageBytes3 = convertImageToByteArray(bitmap3);
//        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c1);
//        byte[] imageBytes4 = convertImageToByteArray(bitmap4);
//        Bitmap bitmap5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y2);
//        byte[] imageBytes5 = convertImageToByteArray(bitmap5);
//        Bitmap bitmap6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y4);
//        byte[] imageBytes6 = convertImageToByteArray(bitmap6);
//        Bitmap bitmap7 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y5);
//        byte[] imageBytes7 = convertImageToByteArray(bitmap7);
//        Bitmap bitmap8 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y6);
//        byte[] imageBytes8 = convertImageToByteArray(bitmap8);
//        Bitmap bitmap9 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c6);
//        byte[] imageBytes9 = convertImageToByteArray(bitmap9);
//        Bitmap bitmap10 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c7);
//        byte[] imageBytes10 = convertImageToByteArray(bitmap10);
//        Bitmap bitmap11 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c8);
//        byte[] imageBytes11 = convertImageToByteArray(bitmap11);
//        Bitmap bitmap12 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y7);
//        byte[] imageBytes12 = convertImageToByteArray(bitmap12);
//        Bitmap bitmap13 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c4);
//        byte[] imageBytes13 = convertImageToByteArray(bitmap13);
//        Bitmap bitmap14 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c11);
//        byte[] imageBytes14 = convertImageToByteArray(bitmap14);
//        Bitmap bitmap15 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c12);
//        byte[] imageBytes15 = convertImageToByteArray(bitmap15);
//        Bitmap bitmap16 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c13);
//        byte[] imageBytes16 = convertImageToByteArray(bitmap16);
//        Bitmap bitmap17 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c14);
//        byte[] imageBytes17 = convertImageToByteArray(bitmap17);
//        Bitmap bitmap18 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c15);
//        byte[] imageBytes18 = convertImageToByteArray(bitmap18);
//        Bitmap bitmap19 = BitmapFactory.decodeResource(this.getResources(), R.drawable.y8);
//        byte[] imageBytes19 = convertImageToByteArray(bitmap19);
//        Bitmap bitmap20 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c17);
//        byte[] imageBytes20 = convertImageToByteArray(bitmap20);
//        Bitmap bitmap21 = BitmapFactory.decodeResource(this.getResources(), R.drawable.i5);
//        byte[] imageBytes21 = convertImageToByteArray(bitmap21);
//        Bitmap bitmap22 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c19);
//        byte[] imageBytes22 = convertImageToByteArray(bitmap22);
//        Bitmap bitmap23 = BitmapFactory.decodeResource(this.getResources(), R.drawable.i6);
//        byte[] imageBytes23 = convertImageToByteArray(bitmap23);
//        Bitmap bitmap24 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c21);
//        byte[] imageBytes24 = convertImageToByteArray(bitmap24);
//        Bitmap bitmap25 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c22);
//        byte[] imageBytes25 = convertImageToByteArray(bitmap25);
//        Bitmap bitmap26 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c23);
//        byte[] imageBytes26 = convertImageToByteArray(bitmap26);
//        Bitmap bitmap27 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c24);
//        byte[] imageBytes27 = convertImageToByteArray(bitmap27);
//        Bitmap bitmap28 = BitmapFactory.decodeResource(this.getResources(), R.drawable.i10);
//        byte[] imageBytes28 = convertImageToByteArray(bitmap28);
//        Bitmap bitmap29 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c26);
//        byte[] imageBytes29 = convertImageToByteArray(bitmap29);
//        Bitmap bitmap30 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c27);
//        byte[] imageBytes30 = convertImageToByteArray(bitmap30);
//        Bitmap bitmap31 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c28);
//        byte[] imageBytes31 = convertImageToByteArray(bitmap31);
//        Bitmap bitmap32 = BitmapFactory.decodeResource(this.getResources(), R.drawable.i8);
//        byte[] imageBytes32 = convertImageToByteArray(bitmap32);
//        Bitmap bitmap33 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c30);
//        byte[] imageBytes33 = convertImageToByteArray(bitmap33);
//        Bitmap bitmap34 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c31);
//        byte[] imageBytes34 = convertImageToByteArray(bitmap34);
//        Bitmap bitmap35 = BitmapFactory.decodeResource(this.getResources(), R.drawable.i7);
//        byte[] imageBytes35 = convertImageToByteArray(bitmap35);
//        Bitmap bitmap36 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c33);
//        byte[] imageBytes36 = convertImageToByteArray(bitmap36);
//        Bitmap bitmap37 = BitmapFactory.decodeResource(this.getResources(), R.drawable.c34);
//        byte[] imageBytes37 = convertImageToByteArray(bitmap37);
//        Crop strawberry = new Crop(0, "Strawberry", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الفراولة من المحاصيل الموسمية التي تُزرع في فصل الربيع، وتحتاج إلى ري منتظم وتربة غنية بالمواد العضوية. تُستخدم في الحلويات والمشروبات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop eggplant = new Crop(0, "Eggplant", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الباذنجان من المحاصيل الصيفية التي تحتاج إلى ري منتظم وتربة خصبة. يُستخدم في الطهي وله إنتاجية عالية في الأجواء الحارة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop pumpkin = new Crop(0, "Pumpkin", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Food crops - High-yield crops - Commercial crops",
//                "اليقطين من المحاصيل الصيفية التي تُزرع في تربة خصبة وتحتاج إلى ري منتظم. يُستخدم في صناعة الحلويات والطبخ.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop garlic = new Crop(0, "Garlic", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Spices - High-yield crops - Commercial crops",
//                "الثوم من المحاصيل التي تُزرع في فصل الشتاء أو الربيع. يحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعلاج.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "winter",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop onion = new Crop(0, "Onion", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Bulb crops - High-yield crops - Commercial crops",
//                "البصل من المحاصيل الموسمية التي تُزرع في تربة خصبة وتحتاج إلى ري منتظم. يُستخدم في الطهي وله قيمة تجارية عالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop pea = new Crop(0, "Pea", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Legumes - High-yield crops - Commercial crops",
//                "البازلاء من المحاصيل التي تُزرع في الربيع وتحتاج إلى تربة خصبة وري منتظم. تُستخدم في الطهي وتُعتبر غذاءً مغذيًا.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop pepper = new Crop(0, "Pepper", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الفلفل من المحاصيل التي تُزرع في الصيف وتحتاج إلى ري منتظم. يُستخدم في الطهي ويُعد من المحاصيل التجارية ذات الإنتاجية العالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop ginger = new Crop(0, "Ginger", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Spices - High-yield crops - Commercial crops",
//                "الزنجبيل من المحاصيل المعمرة التي تحتاج إلى ري منتظم وتربة غنية. يُستخدم في الطهي وله خصائص علاجية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop basil = new Crop(0, "Basil", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Spices - High-yield crops - Commercial crops",
//                "الريحان من المحاصيل العطرية السريعة النمو التي تحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والمشروبات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop cabbage = new Crop(0, "Cabbage", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Leafy crops - High-yield crops - Commercial crops",
//                "الملفوف من المحاصيل الموسمية التي تُزرع في الربيع، وتحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي وله قيمة غذائية عالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//        Crop wheat = new Crop(0, "Wheat", imageBytes, "Seasonal crops - Rainfed crops - Nutrient-rich crops - Food crops - High-yield crops - Staple crops",
//                "القمح من المحاصيل الغذائية الأساسية التي تنمو خلال موسم زراعي واحد، ويعتمد غالبًا على مياه الأمطار، ويتطلب تربة خصبة غنية بالمواد العضوية. إنتاجيته مرتفعة ويُستخدم في صناعة الخبز والمعجنات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "winter",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "Low heat", "High temperature", 0);
//
//        Crop tomato = new Crop(0, "Tomato", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops",
//                "الطماطم من المحاصيل الموسمية التي تحتاج إلى ري منتظم وتربة غنية، وتُستخدم في الغذاء. إنتاجيتها عالية وتُعد من المحاصيل التجارية المهمة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop barley = new Crop(0, "Barley", imageBytes, "Seasonal crops - Rainfed crops - Salt-tolerant crops - Food crops - High-yield crops - Staple crops",
//                "الشعير من المحاصيل الموسمية التي تتحمل الملوحة وتعتمد غالبًا على مياه الأمطار. يُستخدم في صناعة الأعلاف وبعض أنواع الخبز. يتميز بإنتاجية جيدة ويزرع في مناطق جافة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "sandy", "muddy", "rocky", "moist", "dry", "", 0.5, "winter",
//                "Slightly Saline", "Fresh Water", "Saline Water",
//                "Medium heat", "Low heat", "High temperature", 0);
//
//        Crop corn = new Crop(0, "Corn", imageBytes, "Seasonal crops - Rainfed crops - Nutrient-rich crops - Food crops - High-yield crops - Staple crops",
//                "الذرة من المحاصيل الموسمية التي تُزرع في الصيف، وتعتمد على الأمطار أو الري الجزئي. تحتاج لتربة خصبة وتُستخدم كغذاء أساسي ولها إنتاجية عالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop mint = new Crop(0, "Mint", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Medicinal crops - Low-yield crops - Commercial crops",
//                "النعناع نبات عشبي سريع النمو يُستخدم في الطب البديل والمشروبات والمأكولات، يحتاج إلى تربة غنية ورطبة وري منتظم. له أهمية تجارية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop alfalfa = new Crop(0, "Alfalfa", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Forage crops - High-yield crops - Commercial crops",
//                "البرسيم يُستخدم كعلف عالي القيمة، يُزرع في مواسم محددة ويحتاج إلى ري منتظم وتربة خصبة. يُعتبر من المحاصيل ذات الإنتاجية العالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop quinoa = new Crop(0, "Quinoa", imageBytes, "Short-term crops - Rainfed crops - Drought-tolerant crops - Food crops - Low-yield crops - Medicinal crops",
//                "الكينوا من النباتات التي تتحمل الجفاف والملوحة، وتُزرع في مناطق قاسية التربة. تحتوي على بروتينات عالية وتُعتبر غذاءً صحيًا.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "sandy", "rocky", "muddy", "dry", "moist", "", 0.5, "spring",
//                "Slightly Saline", "Fresh Water", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop zucchini = new Crop(0, "Zucchini", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Food crops - High-yield crops - Commercial crops",
//                "الكوسا من الخضروات الموسمية التي تحتاج إلى تربة خصبة وري منتظم، تُستخدم في الطبخ وتتميز بإنتاجية جيدة خلال فترة قصيرة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop cucumber = new Crop(0, "Cucumber", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops",
//                "الخيار من المحاصيل الموسمية التي تحتاج إلى ري منتظم وتربة خصبة. يُستخدم في صناعة المخللات والسلطات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop carrot = new Crop(0, "Carrot", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops",
//                "الجزر من المحاصيل الجذرية التي تُزرع في الصيف، وتحتاج إلى ري منتظم وتربة خصبة. يُستخدم في العديد من الأطباق والمشروبات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop watermelon = new Crop(0, "Watermelon", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Food crops - High-yield crops - Commercial crops",
//                "البطيخ من المحاصيل الصيفية التي تحتاج إلى ري منتظم، وتُزرع في المناطق ذات الحرارة العالية، وتتميز بطعمه المنعش.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop spinach = new Crop(0, "Spinach", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Leafy crops - High-yield crops - Commercial crops",
//                "السبانخ من الخضروات الورقية السريعة النمو التي تحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي ويحتوي على عناصر غذائية هامة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop lettuce = new Crop(0, "Lettuce", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Leafy crops - High-yield crops - Commercial crops",
//                "الخس من الخضروات الورقية السريعة النمو التي تُزرع في مناطق باردة، وتحتاج إلى ري منتظم وتربة غنية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop broccoli = new Crop(0, "Broccoli", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops",
//                "البروكلي من الخضروات الغنية بالعناصر الغذائية والتي تُزرع في فصل الربيع، وتحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي ويحتوي على فوائد صحية متعددة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop cauliflower = new Crop(0, "Cauliflower", imageBytes, "Seasonal crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops",
//                "القرنبيط من الخضروات الغنية التي تحتاج إلى تربة خصبة وري منتظم، ويُزرع في الربيع. يُستخدم في الطهي وله فوائد صحية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop radish = new Crop(0, "Radish", imageBytes, "Short-term crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops",
//                "الفجل من المحاصيل الجذرية السريعة النمو التي تُزرع في فصل الربيع. يحتاج إلى ري منتظم وتربة خصبة. يُستخدم في الطهي ويُضاف إلى السلطات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop artichoke = new Crop(0, "Artichoke", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops",
//                "الخرشوف من المحاصيل المعمرة التي تُزرع في تربة خصبة وري منتظم. يُستخدم في الطهي وله فوائد غذائية صحية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "spring",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop pomegranate = new Crop(0, "Pomegranate", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الرمان من المحاصيل المعمرة التي تُزرع في المناطق الحارة وتحتاج إلى ري منتظم. يُستخدم في الطهي والعصائر وله قيمة غذائية عالية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//        Crop mango = new Crop(0, "Mango", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "المانجو من المحاصيل المثمرة التي تُزرع في المناطق الحارة. يحتاج إلى تربة خصبة وري منتظم. يتميز بطعمه اللذيذ وغني بالعناصر الغذائية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop peach = new Crop(0, "Peach", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الخوخ من المحاصيل المثمرة التي تُزرع في فصل الربيع والصيف. يحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعصائر.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop apple = new Crop(0, "Apple", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "التفاح من المحاصيل المثمرة التي تُزرع في المناطق المعتدلة. يحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعصائر.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "fall",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
//
//        Crop grape = new Crop(0, "Grape", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "العنب من المحاصيل المثمرة التي تُزرع في مناطق دافئة. يحتاج إلى تربة خصبة وري منتظم. يُستخدم في صناعة العصائر والصلصات.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "Medium heat", "High temperature", "Low heat", 0);
/// /
//
//        Crop banana = new Crop(0, "Banana", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الموز من المحاصيل المثمرة التي تُزرع في المناطق الحارة. يحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعصائر.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//        Crop grapefruit = new Crop(0, "Grapefruit", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الجريب فروت من المحاصيل المعمرة التي تُزرع في المناطق الحارة وتحتاج إلى ري منتظم. يُستخدم في العصائر وله فوائد صحية متنوعة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//
//        Crop fig = new Crop(0, "Fig", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "التين من المحاصيل المعمرة التي تُزرع في مناطق حارة وتحتاج إلى ري منتظم. يُستخدم في الطهي والعصائر وله فوائد صحية متعددة.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//
//
//        Crop lemon = new Crop(0, "Lemon", imageBytes, "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops",
//                "الليمون من المحاصيل المعمرة التي تُزرع في المناطق الحارة وتحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعصائر وله فوائد صحية.",
//                "", imageBytes, false, false, false, "jane_smith", null, 0,
//                "muddy", "sandy", "rocky", "moist", "dry", "", 0.5, "summer",
//                "Fresh Water", "Slightly Saline", "Saline Water",
//                "High temperature", "Medium heat", "Low heat", 0);
//        Crop lemon = new Crop(
//                0, // cropID
//                "Lemon", // cropName
//                "Perennial crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops", // category
//                "الليمون من المحاصيل المعمرة التي تُزرع في المناطق الحارة وتحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي والعصائر وله فوائد صحية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "muddy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "dotting", // preferredIrrigation
//                "Machine guns", // allowedIrrigation
//                "immersion", // forbiddenIrrigation
//                0.5, // minArea
//                "summer", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "High temperature", // preferredTemp
//                "Medium heat", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays (مثال: الري كل 7 أيام)
//                14, // fertilizingFrequencyDays (مثال: التسميد كل 14 يوم)
//                "Water regularly and ensure well-drained soil.", // wateringInstructions
//                "Fertilize with organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//
//        Crop cucumber = new Crop(
//                0, // cropID
//                "Cucumber", // cropName
//                "Seasonal crops - Irrigated crops - Nutrient-rich crops - Vegetative crops - High-yield crops - Commercial crops", // category
//                "الخيار من المحاصيل الموسمية التي تحتاج إلى ري منتظم وتربة خصبة. يُستخدم في الطهي ويُعد من المحاصيل التجارية ذات الإنتاجية العالية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "muddy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "summer", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "High temperature", // preferredTemp
//                "Medium heat", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly to keep soil moist.", // wateringInstructions
//                "Apply balanced fertilizer every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//
//        Crop spinach = new Crop(
//                0, // cropID
//                "Spinach", // cropName
//                "Short-term crops - Irrigated crops - Nutrient-rich crops - Leafy crops - High-yield crops - Commercial crops", // category
//                "السبانخ من المحاصيل السريعة النمو التي تحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي وله قيمة غذائية عالية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "muddy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "spring", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "Medium heat", // preferredTemp
//                "High temperature", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Keep soil moist and water regularly.", // wateringInstructions
//                "Fertilize with organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//
//        Crop radish = new Crop(
//                0, // cropID
//                "Radish", // cropName
//                "Short-term crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops", // category
//                "الفجل من المحاصيل السريعة النمو التي تُزرع في التربة الخفيفة. يُستخدم في السلطات والوجبات الخفيفة.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "muddy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "spring", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "Medium heat", // preferredTemp
//                "High temperature", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly to maintain soil moisture.", // wateringInstructions
//                "Fertilize with organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//
//        Crop carrot = new Crop(
//                0, // cropID
//                "Carrot", // cropName
//                "Seasonal crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops", // category
//                "الجزر من المحاصيل الموسمية التي تحتاج إلى تربة غنية بالمواد العضوية وري منتظم. يُستخدم في الطهي وله قيمة غذائية عالية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "muddy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "winter", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "Medium heat", // preferredTemp
//                "High temperature", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly to keep soil moist.", // wateringInstructions
//                "Fertilize with organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//        Crop tomato = new Crop(
//                0, // cropID
//                "Tomato", // cropName
//                "Seasonal crops - Irrigated crops - Nutrient-rich crops - Fruit crops - High-yield crops - Commercial crops", // category
//                "الطماطم من المحاصيل الموسمية التي تُزرع في التربة الخصبة وتحتاج إلى ري منتظم. يُستخدم في الطهي والعصائر وله فوائد صحية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "loamy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "summer", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "High temperature", // preferredTemp
//                "Medium heat", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly and ensure well-drained soil.", // wateringInstructions
//                "Fertilize with organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );
//
//        Crop onion = new Crop(
//                0, // cropID
//                "Onion", // cropName
//                "Seasonal crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops", // category
//                "البصل من المحاصيل الموسمية التي تحتاج إلى تربة خصبة وري منتظم. يُستخدم في الطهي وله فوائد صحية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "loamy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "spring", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "High temperature", // preferredTemp
//                "Medium heat", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly and ensure soil is not waterlogged.", // wateringInstructions
//                "Apply balanced fertilizer every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );

//        Crop potato = new Crop(
//                0, // cropID
//                "Potato", // cropName
//                "Seasonal crops - Irrigated crops - Nutrient-rich crops - Root crops - High-yield crops - Commercial crops", // category
//                "البطاطس من المحاصيل الموسمية التي تحتاج إلى تربة خصبة وري منتظم. تُستخدم في الطهي وله إنتاجية عالية.", // description
//                "", // expertName
//                false, // isBookmarked
//                false, // isAddCart
//                false, // isCompleted
//                "jane_smith", // expertUserName
//                0, // rating
//                "loamy", // preferredSoil
//                "sandy", // allowedSoil
//                "rocky", // forbiddenSoil
//                "moist", // preferredIrrigation
//                "dry", // allowedIrrigation
//                "", // forbiddenIrrigation
//                0.5, // minArea
//                "spring", // season
//                "Fresh Water", // preferredHumidity
//                "Slightly Saline", // allowedHumidity
//                "Saline Water", // forbiddenHumidity
//                "High temperature", // preferredTemp
//                "Medium heat", // allowedTemp
//                "Low heat", // forbiddenTemp
//                0, // reviews
//                7, // wateringFrequencyDays
//                14, // fertilizingFrequencyDays
//                "Water regularly and ensure well-drained soil.", // wateringInstructions
//                "Apply organic compost every 2 weeks.", // fertilizingInstructions
//                "Direct seeding or transplanting." // plantingMethod
//        );


//        Crop crop2 = new Crop(0, "Advanced Animation", imageBytes1, 150, "3D Design", "Master Java programming with a focus on object-oriented design, advanced algorithms, and best practices.", "Jane Smith", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop3 = new Crop(0, "3D Sculpting Techniques", imageBytes2, 200, "3D Design", "Learn to build responsive and interactive websites using HTML, CSS, JavaScript, and modern frameworks.", "Alice Brown", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop4 = new Crop(0, "Lighting Scenes", imageBytes3, 180, "3D Design", "Develop cross-platform mobile applications with a focus on performance, user experience, and scalability.", "Bob White", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop5 = new Crop(0, "Lighting Scenes", imageBytes4, 250, "3D Design", "Dive into data science concepts, including data analysis, visualization, and machine learning techniques.", "Charlie Green", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop6 = new Crop(0, "3D Rendering Essentials", imageBytes5, 220, "3D Design", "Understand machine learning fundamentals, including supervised and unsupervised learning, and model evaluation.", "Dave Black", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop7 = new Crop(0, "Character Modeling", imageBytes6, 300, "3D Design", "Explore blockchain technology, its use cases, and how it is revolutionizing industries.", "Eve Blue", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop8 = new Crop(0, "Environmental 3D Design", imageBytes7, 120, "3D Design", "Learn the principles of user interface and user experience design with hands-on projects.", "Frank Pink", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop9 = new Crop(0, "3D Printing and Prototyping", imageBytes8, 350, "3D Design", "Gain knowledge in cybersecurity, covering topics like ethical hacking, threat analysis, and mitigation strategies.", "Grace Yellow", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop10 = new Crop(0, "Architectural Visualization", imageBytes9, 280, "3D Design", "Understand cloud computing services, deployment models, and the benefits of cloud infrastructure.", "Harry Red", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop11 = new Crop(0, "3D Motion Graphics", imageBytes10, 230, "3D Design", "Learn the basics of artificial intelligence, covering neural networks, natural language processing, and applications.", "Ivy Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop12 = new Crop(0, "Game Asset Creation in 3D", imageBytes11, 170, "3D Design", "Develop fully functional apps for Android and iOS using industry-standard tools and techniques.", "Jack White", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop13 = new Crop(0, "VFX in 3D", imageBytes12, 210, "3D Design", "Learn how to design, develop, and deploy engaging games for various platforms.", "Kylie Blue", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop14 = new Crop(0, "3D Animation for Advertising", imageBytes13, 240, "3D Design", "Understand database design principles, normalization, and the use of SQL in managing databases.", "Leo Brown", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop15 = new Crop(0, "Digital Marketing", imageBytes14, 160, "Business", "Learn about digital marketing strategies, including SEO, social media marketing, and analytics.", "Mia Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop16 = new Crop(0, "Cloud Security", imageBytes15, 300, "Business", "Discover the essentials of cloud security, including securing cloud environments and managing risks effectively.", "Nathan Yellow", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop17 = new Crop(0, "Network Fundamentals", imageBytes16, 130, "Business", "Understand the fundamentals of networking, including protocols, hardware, and troubleshooting techniques.", "Olivia White", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop18 = new Crop(0, "Robotics", imageBytes17, 330, "Business", "Explore the world of robotics, covering automation, programming, and real-world applications.", "Paul Black", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop19 = new Crop(0, "Full Stack Development", imageBytes18, 260, "Business", "Master full-stack development, covering frontend, backend, and database integration.", "Quinn Blue", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop20 = new Crop(0, "Business Fundamentals", imageBytes19, 220, "Business", "Learn to extract insights from data, including statistical analysis and visualization tools.", "Rachel Green", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop21 = new Crop(0, "Growth and Expansion Strategies", imageBytes20, 220, "Business", "Delve deeper into data analytics with advanced techniques for decision-making and strategy.", "Rachel Green", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop22 = new Crop(0, "Project Management: From Idea to Execution", imageBytes21, 220, "Business", "Explore big data concepts, tools, and frameworks to handle complex datasets efficiently.", "Rachel Green", null, false, false, false, "bob_white", null, 0, 0);
//        Crop crop23 = new Crop(0, "Financial Analysis for Businesses", imageBytes22, 220, "Business", "Learn predictive analytics techniques to forecast trends and patterns in various domains.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop24 = new Crop(0, "Digital Marketing", imageBytes23, 220, "Business", "Get hands-on experience with data cleaning, wrangling, and visualization using modern tools.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop25 = new Crop(0, "Customer Relationship", imageBytes24, 220, "Art", "Analyze art market trends and gain insights into the intersection of art and data analytics.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop26 = new Crop(0, "Leadership", imageBytes25, 220, "Art", "Learn how to apply data analytics techniques to understand artistic patterns and themes.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop27 = new Crop(0, "Innovation", imageBytes26, 220, "Art", "Explore data visualization in the context of art to better present and interpret creative datasets.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop28 = new Crop(0, "Art History and Appreciation", imageBytes27, 220, "Art", "Learn statistical techniques and tools to analyze data in the art domain.", "Rachel Green", null, false, false, false, "alice_brown", null, 0, 0);
//        Crop crop29 = new Crop(0, "Modern and Contemporary Art", imageBytes28, 220, "Art", "Understand how data analytics can influence modern art and design decision-making processes.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop30 = new Crop(0, "Digital Art and Media", imageBytes29, 220, "Art", "Dive into advanced data analytics methods tailored for art-related projects and markets.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop31 = new Crop(0, "Drawing Techniques", imageBytes30, 220, "Art", "Discover how to leverage data analytics to enhance creativity and innovation in art.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop32 = new Crop(0, "Programming with Python", imageBytes31, 220, "Programming", "Learn programming-focused data analytics, including Python libraries for data processing.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop33 = new Crop(0, "Data Algorithms", imageBytes32, 220, "Programming", "Master data analytics algorithms and implement them programmatically in your projects.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop34 = new Crop(0, "Web Development", imageBytes33, 220, "Programming", "Understand advanced data processing and management techniques using coding skills.", "Rachel Green", null, false, false, false, "jane_smith", null, 0, 0);
//        Crop crop35 = new Crop(0, "Object-Oriented Java", imageBytes34, 220, "Programming", "Apply machine learning and data mining techniques to programming-oriented datasets.", "Rachel Green", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop36 = new Crop(0, "Mobile App Development", imageBytes35, 220, "Programming", "Dive into real-world programming problems and solve them using advanced analytics.", "Rachel Green", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop37 = new Crop(0, "Database Management", imageBytes36, 220, "Programming", "Learn to programmatically visualize and analyze data for better decision-making.", "Rachel Green", null, false, false, false, "john_doe", null, 0, 0);
//        Crop crop38 = new Crop(0, "Functional Programming", imageBytes37, 220, "Programming", "Explore cutting-edge data analytics tools and frameworks in the programming domain.", "Rachel Green", null, false, false, false, "john_doe", null, 0, 0);

//        myViewModel.insertCrop(strawberry);
//        myViewModel.insertCrop(eggplant);
//        myViewModel.insertCrop(pumpkin);
//        myViewModel.insertCrop(garlic);
//        myViewModel.insertCrop(lentils);
/// /        myViewModel.insertCrop(saffron);
/// /        myViewModel.insertCrop(wheat);
/// /        myViewel.insertCrop(onion);
//        myViewModel.insertCrop(banana);
//        myViewModel.insertCrop(cauliflower);
//        myViewModel.insertCrop(mint);
//        myViewModel.insertCrop(bellPepper);
//        myViewModel.insertCrop(alfalfa);
//        myViewModel.insertCrop(peas);
//        myViewModel.insertCrop(broccoli);
//        myViewModel.insertCrop(sunflower);
//        myViewModel.insertCrop(pumpkin);
//      myViewModel.insertCrop(pea);
//        myViewModel.insertCrop(cucumber);
//        myViewModel.insertCrop(melon);
//        myViewModel.insertCrop(cabbage);
//        myViewModel.insertCrop(zucchini);
//        myViewModel.insertCrop(peach);
//        myViewModel.insertCrop(barley);
//        myViewModel.insertCrop(eggplant);
//        myViewModel.insertCrop(strawberry);
/// /        myViewModel.insertCrop(pepper);
/// /        myViewModel.insertCrop(ginger);
/// /        myViewModel.insertCrop(basil);
/// /        myViewModel.insertCrop(cabbage);
/// /        myViewModel.insertCrop(wheat);
//        myViewModel.insertCrop(tomato);
//        myViewModel.insertCrop(spinach);
//        myViewModel.insertCrop(lettuce);
//myViewModel.insertCrop(barley);
//        myViewModel.insertCrop(corn);
/// /        myViewModel.insertCrop(mint);
//        myViewModel.insertCrop(potato);
/// /        myViewModel.insertCrop(alfalfa);
/// /        myViewModel.insertCrop(quinoa);
/// /        myViewModel.insertCrop(zucchini);
///       myViewModel.insertCrop(cucumber);
//        myViewModel.insertCrop(carrot);
//        myViewModel.insertCrop(garlic);
//        myViewModel.insertCrop(watermelon);
/// /        myViewModel.insertCrop(spinach);
/// /        myViewModel.insertCrop(lettuce);
/// /        myViewModel.insertCrop(broccoli);
/// /        myViewModel.insertCrop(cauliflower);
/// /        myViewModel.insertCrop(radish);
/// /        myViewModel.insertCrop(artichoke);
/// /        myViewModel.insertCrop(pomegranate);
/// /        myViewModel.insertCrop(mango);
/// /        myViewModel.insertCrop(peach);
/// /        myViewModel.insertCrop(apple);
/// /        myViewModel.insertCrop(grape);
/// /        myViewModel.insertCrop(banana);
/// /        myViewModel.insertCrop(grapefruit);
/// /        myViewModel.insertCrop(fig);
/// /        myViewModel.insertCrop(lemon);
//}
//private void addSampleStepes() {
//
//        for (int dbId = 1; dbId <= 38; dbId++) {
//            myViewModel.insertCropStep(new CropStep(0, "مقدمة في تصميم ثلاثي الأبعاد", "https://www.youtube.com/watch?v=VG8R7QGdGp8", 10, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تعلم أساسيات النمذجة 3D Max", "https://www.youtube.com/watch?v=3Ic4kF3rdzQ", 12, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "إنشاء مشاهد واقعية في Maya", "https://www.youtube.com/watch?v=FvBqDqsmHzI", 8, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "إضاءة المشاهد   في Cinema 4D", "https://www.youtube.com/watch?v=fsAxSxp88ZQ", 15, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تحريك الشخصيات في Blender", "https://www.youtube.com/watch?v=6dXM8Gocv6k", 13, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تصميم بيئات ثلاثية 3D Max", "https://www.youtube.com/watch?v=2U5M6vI6xzY", 14, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "إضافة مؤثرات خاصة في Maya", "https://www.youtube.com/watch?v=kYgYbEo7HTo", 11, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تصدير النماذج للألعاب", "https://www.youtube.com/watch?v=6MQyZyQDRh4", 9, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "مقدمة في تصميم الشخصيات", "https://www.youtube.com/watch?v=k1pS5lfH2kc", 12, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تقنيات الإكساء في Cinema 4D", "https://www.youtube.com/watch?v=nl5mcYIu7Fk", 16, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تحريك الكاميرا في مشاهد 3D Max", "https://www.youtube.com/watch?v=6dFtkdZTkmE", 17, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "إضافة إضاءة واقعية في Blender", "https://www.youtube.com/watch?v=0RPylvtaHhE", 10, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تصميم مواد واقعية في Maya", "https://www.youtube.com/watch?v=mrctuUwEryo", 8, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "المشاهد الداخلية في 3D Max", "https://www.youtube.com/watch?v=JlxWqg7xeQU", 14, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تحريك الشخصيات في Cinema 4D", "https://www.youtube.com/watch?v=0VZqzxbzXYY", 10, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "إضافة مؤثرات خاصة في Blender", "https://www.youtube.com/watch?v=a4kDk1s5de4", 13, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تصميم بيئات طبيعية في Maya", "https://www.youtube.com/watch?v=pBiv8nq-XGA", 12, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تقنيات الإكساء في 3D Max", "https://www.youtube.com/watch?v=HkVgOlgw9yA", 11, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "تحريك الكائنات في Blender", "https://www.youtube.com/watch?v=c56y_AiEcrE", 10, false, dbId));
//            myViewModel.insertCropStep(new CropStep(0, "مقدمة لتقنيات الـ 3D في الألعاب", "https://www.youtube.com/watch?v=c7Bx6m0bxg8", 9, false, dbId));
//
//        }
//
//
//    }
//  Crop onion = new Crop();
//        onion.setCrop_NAME("Onion");
//        onion.setCategorie("Seasonal crops - Root crops - High-demand crops");
//        onion.setDescription("البصل من المحاصيل الجذرية ويُزرع في الخريف أو الشتاء ويحتاج إلى تربة جيدة التصريف.");
//        onion.setExpert_USER_Name("jane_smith");
//        onion.setPreferredSoil("sandy");
//        onion.setAllowedSoil("muddy");
//        onion.setAllowedSoil("rocky");
//        onion.setPreferredIrrigation("drip");
//        onion.setAllowedIrrigation("sprinkler");
//        onion.setForbiddenIrrigation("immersion");
//        onion.setMinArea(0.3);
//        onion.setSeason("spring");
//        onion.setPreferredHumidity("High");
//        onion.setAllowedHumidity("Low");
//        onion.setForbiddenHumidity("Moderate");
//        onion.setPreferredTemp("Cool");
//        onion.setAllowedTemp("Mild");
//        onion.setForbiddenTemp("Hot");
//        onion.setWateringFrequencyDays(3);
//        onion.setFertilizingFrequencyDays(14);
//        onion.setWateringInstructions("\uD83D\uDEB0 برنامج الري:\n" +
//                "\n" +
//                "مرحلة النمو: ري كل 3 أيام (تجنب تبليل الأوراق)\n" +
//                "\n" +
//                "مرحلة الإثمار: ري يومي في الصيف (صباحاً)");
//
//        onion.setFertilizingInstructions("\uD83C\uDF31 برنامج التسميد:\n" +
//                "\n" +
//                "قبل الزراعة: سماد عضوي + سوبر فوسفات\n" +
//                "\n" +
//                "بعد 3 أسابيع: NPK (19-19-19)\n" +
//                "\n" +
//                "أثناء الإزهار: رش بورون + كالسيوم\n" +
//                "\n" +
//                "⚠\uFE0F تحذير: الإفراط في النيتروجين يقلل الإثمار!\n" +
//                "\n");
//        onion.setPlantingMethod("\uD83C\uDF31 دليل شامل لزراعة الطماطم: من البذور إلى الحصاد\n" +
//                "\uD83D\uDCCC 1. اختيار الصنف المناسب\n" +
//                "\uD83D\uDD39 أصناف محلية: مثل \"هجين ف1 448\" (مقاوم للأمراض)\n" +
//                "\uD83D\uDD39 أصناف عالمية: مثل \"بيف ستيك\" (للعصير) أو \"تشيري\" (للسلطات)\n" +
//                "\uD83D\uDD39 اختيار حسب الغرض:\n" +
//                "\n" +
//                "\uD83E\uDD6B للعصير: أصناف ذات لب كثيف (سان مارزانو)\n" +
//                "\n" +
//
//                "\uD83E\uDD57 للطازج: أصناف حلوة (هجين 023)\n" +
//                "\n" +
//                "\uD83C\uDF3F 2. تحضير التربة\n" +
//                "✅ المواصفات المثالية:\n" +
//                "\n" +
//                "درجة حموضة (pH 6-6.8)\n" +
//                "\n" +
//                "تربة جيدة الصرف (مخلوطة بـ 30% رمل)\n" +
//                "\n" +
//                "غنية بالمادة العضوية (أضف 10 كجم سماد بلدي/م²)\n" +
//                "\n" +
//                "⚠\uFE0F تحذير: تجنب التربة الطينية الثقيلة!\n" +
//                "\n" +
//                "\uD83C\uDF31 3. طرق الزراعة\n" +
//                "الطريقة\tالتفاصيل\tالوقت المناسب\n" +
//                "البذور\tتنقع في ماء دافئ (24 ساعة) ثم تزرع في أصص\tقبل 6-8 أسابيع من الصقيع الأخير\n" +
//                "الشتل\tنقل الشتلات عند طول 15-20 سم\tبعد زوال خطر الصقيع\n" +
//                "الزراعة المباشرة\t3 بذور/جورة بعمق 1 سم\tعندما تصل درجة الحرارة لـ 18°م ليلاً\n" +
//                "\uD83D\uDD39 مسافات الزراعة:\n" +
//                "\n" +
//                "50-60 سم بين النباتات\n" +
//                "\n" +
//                "90-100 سم بين الخطوط\n" +
//                "\n");
//        onion.setCropProblems("\n" +
//                "\uD83C\uDF45 مشاكل زراعة الطماطم وحلولها\n" +
//                "1\uFE0F⃣ مشكلة: تعفن الطرف الزهري (Blossom End Rot)\n" +
//                "الأعراض:\n" +
//                "\n" +
//                "ظهور بقع سوداء أو بنية داكنة عند طرف الثمرة\n" +
//                "\n" +
//                "جفاف المنطقة المصابة وتصلبها\n" +
//                "\n" +
//                "\uD83D\uDEE0\uFE0F الحلول:\n" +
//                "✅ التوازن الكالسيومي: أضف الجير الزراعي أو رش نترات الكالسيوم (5 جم/لتر ماء)\n" +
//                "✅ الري المنتظم: حافظ على رطوبة التربة (لا تجف تمامًا)\n" +
//                "✅ التسميد المتوازن: تجنب الإفراط في الأسمدة النيتروجينية\n" +
//                "\n"
//                + "\uD83D\uDD04 الدورة الزراعية: تغيير المحصول كل موسم\n" +
//                "\n" +
//                "\uD83E\uDDEA تحليل التربة: قبل الزراعة لتحديد الاحتياجات\n" +
//                "\n" +
//                "\uD83D\uDC1D جذب الملقحات: بزراعة نباتات الزينة حول الحقل\n" +
//                "\n" +
//                "\uD83D\uDCC5 الزراعة في موعدها: حسب التقويم الزراعي للمنطقة\n" +
//                "\n" +
//                "\uD83D\uDC69\u200D\uD83C\uDF3E المتابعة اليومية: لاكتشاف المشاكل مبكراً\n" +
//                "\n" +
//                "\uD83D\uDCA1 إحصائية مهمة: 80% من مشاكل المحاصيل تنتج عن سوء إدارة الري والتسميد!");
//
//        onion.setPruning_and_guidance("\uD83D\uDD04  التقليم والتوجيه\n" +
//                "✂\uFE0F تقليم السرطانات: إزالة الفروع الجانبية تحت أول عنقود زهري\n" +
//                "\uD83C\uDF3F التدعيم: استخدام أوتاد خشبية أو شباك تعليق\n" +
//                "\n" +
//                "طريقة التدلي: لف الساق حول الخيط كل أسبوع\n" +
//                "\n");
//
//        onion.setPreferredAbundance("Available");
//        onion.setAllowedAbundance("Limited");
//        onion.setForbiddenAbundance("Unavailable");
//        onion.setLearnMore("\uD83D\uDC1B 5. المكافحة المتكاملة للآفات\n" +
//                "الآفة\tالعلاج\tالوقاية\n" +
//                "الذبابة البيضاء\tرش زيت النيم (2 مل/لتر)\tمصائد صفراء لاصقة\n" +
//                "التعفن البكتيري\tرش نحاس (كوسيد 101)\tتعقيم الأدوات\n" +
//                "ديدان الثمار\tBacillus thuringiensis\tتغطية الثمار بأكياس ورقية\n" +
//                "\uD83C\uDF1E 6. الظروف البيئية المثلى\n" +
//                "☀\uFE0F الحرارة:\n" +
//                "\n" +
//                "النهار: 25-30°م\n" +
//                "\n" +
//                "الليل: 15-18°م\n" +
//                "\n" +
//                "\uD83D\uDCA1 الإضاءة: 8 ساعات ضوء يومياً على الأقل\n" +
//                "\n" +
//                "\uD83C\uDF27\uFE0F الرطوبة: 60-70% (استخدم مراوح في البيوت المحمية إذا زادت)\n" +
//                "\n" +
//                "\uD83D\uDED1 8. المشاكل الشائعة وحلولها\n" +
//                "المشكلة\tالسبب\tالحل\n" +
//                "تساقط الأزهار\tحرارة >35°م أو رطوبة عالية\tرش هرمون عقد الثمار (NAA)\n" +
//                "تشقق الثمار\tري غير منتظم\tالري بانتظام + تغطية التربة\n" +
//                "بقع صفراء على الأوراق\tنقص مغنيسيوم\tرش كبريتات ماغنيسيوم (2 جم/لتر)\n" +
//                "\uD83D\uDCC5 9. مواعيد الزراعة حسب المناطق\n" +
//                "المنطقة\tالموعد المثالي\n" +
//                "المناطق الدافئة\tسبتمبر-أكتوبر\n" +
//                "المناطق المعتدلة\tفبراير-مارس\n" +
//                "البيوت المحمية\tطوال العام (مع تحكم بالمناخ)\n" +
//                "\uD83E\uDDFA 10. الحصاد والتخزين\n" +
//                "\uD83D\uDCC5 موعد الحصاد: بعد 70-90 يوم من الزراعة\n" +
//                "\uD83D\uDD39 علامات النضج:\n" +
//                "\n" +
//                "لون أحمر متجانس\n" +
//                "\n" +
//                "ملمس طري عند الضغط الخفيف\n" +
//                "\n" +
//                "❄\uFE0F التخزين:\n" +
//                "\n" +
//                "الطازجة: 10-12°م (تجنب الثلاجة!)\n" +
//                "\n" +
//                "التجفيف: تقطع شرائح وتجفف بالشمس 3 أيام\n" +
//                "\n" +
//                "\uD83C\uDFAF نصائح الخبراء\n" +
//                "لثمار أكثر حلاوة: أضف ملعقة صغيرة ملح إنجليزي/لتر ماء عند الري\n" +
//                "\n" +
//                "لمكافحة النيماتودا: زراعة القطيفة (Marigold) بين الخطوط\n" +
//                "\n" +
//                "لزيادة العقد: هز النباتات برفق عند الصباح لنقل اللقاح\n" +
//                "\n" +
//                "حقيقة ممتعة: \uD83E\uDDEA الطماطم تنتج هرمون \"الإيثيلين\" الذي يساعد في نضج الفواكه الأخرى!\n" +
//                "\n" +
//                "باستخدام هذا الدليل، يمكنك الحصول على إنتاج وفير بجودة عالية \uD83C\uDF1F\uD83C\uDF45."
//        );
//        onion.setOptimalHumidity(50);
//        onion.setOptimalTemperature(20);
//        onion.setLightRequirements(3);
//        onion.setNumber_Plant_per_dunum(1500);
//        onion.setOrganicFertilizer("ارض مكشوفة_بيت بلاستيكي_زراعة مائية_زراعة عضوية");
//        onion.setOrganicPerPlant(150.0);
//        onion.setChemicalFertilizer( "حرث الأرض مرتين على الأقل بعمق 25–30 سم، حتى تهوي التربة وتتفتت الكتل.\n" +
//                "\n" +
//                        "تنظيف الأرض من الحشائش والجذور اللي ممكن تعيق نمو الشتلات.\n" +
//                        "\n" +
//                        "إضافة السماد العضوي المتحلل (بلدي أو كمبوست) بمعدل 4–5 طن للدونم، وخلطه جيدًا بالتربة.\n" +
//                        "\n" +
//                        "لو الأرض ثقيلة، يفضل إضافة رمل أو بيتموس لتحسين التصريف.\n" +
//                        "\n" +
//                        "تأكد إن التربة فيها صرف جيد، لأن الطماطم ما بتحب تجمع المياه.\n" +
//                        "\n" +
//                        "اضبط حموضة التربة (pH) لتكون بين 6 و6.8، لأنها الأنسب للطماطم.\n" +
//                        "\n" +
//                        "أعمل تسوية جيدة للتربة بعد الحراثة حتى تكون الأرض مستوية وسهلة للري والزراعة.\n" +
//                        "\n" +
//                        "إذا ناوي تزرع شتلات، جهز الخطوط أو الأحواض حسب طريقة الزراعة اللي تستخدمها.");
//        onion.setChemicalPerPlant(12.0);
//        onion.setPrevious_crop_preferred("الخس، السبانخ، الجرجير، البقدونس، الكزبرة، النعناع، البرسيم، الحلبة، الشوفان (كغطاء أخضر)، الذرة الرفيعة، الدخن، عباد الشمس");
//        onion.setPrevious_crop_allowed("الذرة، القمح، الشعير، الشوفان، العدس، الفول، الحمص، الفاصوليا، البازلاء، البطيخ، الشمام، الخيار، الكوسا، الباذنجان، الفلفل، الطماطم");
//
//        onion.setPrevious_crop_forbidden( "الثوم، الكراث، البصل، البطاطا، البطاطس، الفجل، اللفت، الجزر، الشمندر، الكرنب، القرنبيط، البروكلي");
//        onion.setChemicalFertilizer("Chemical");
//        onion.setSoil_preparation_allowed( "بذور محسنة ومعتمدة.\n" +
//                "\n" +
//                "مقاومة للأمراض الشائعة.\n" +
//                "\n" +
//                "نسبة إنبات عالية.\n" +
//                "\n" +
//                "شراءها من مصدر موثوق.");
//        onion.setWeight_seeds_per_dunum(3);
//        onion.setSeedSpecifications("اختر بذور بصل عالية الجودة ومعتمدة، خالية من الأمراض، وذات حجم متوسط للحصول على نمو متجانس. \uD83E\uDDC5");
//        onion.setSeedlingPreparation("قم بنقع البذور في ماء فاتر لمدة 12 ساعة قبل الزراعة، ويمكن تجهيز الشتلات في مشتل لمدة 6 أسابيع قبل النقل. \uD83C\uDF31");
//        onion.setPlantingDistance("اترك مسافة 10-15 سم بين كل نبات، و30-40 سم بين الخطوط لضمان التهوية الجيدة ونمو البصل بشكل مناسب. \uD83D\uDCCF");
//        onion.setPlantingDepth("ازرع البذور أو الشتلات على عمق 2-3 سم فقط في التربة لتسهيل الإنبات والنمو. ⛏\uFE0F");
//        onion.setInitialIrrigation("\"بعد الزراعة، اسقِ الأرض مباشرة بريّة غمر خفيفة لتثبيت البذور أو الشتلات في التربة. \uD83D\uDCA7");
//        onion.setDaysToMaturity(100);//عدد الايام حتى النضج
//        onion.setHigh("ابيض");
//        onion.setMid("احمر");
//        onion.setLow("اصفر");
//        onion.setTemperatureTolerance(10);
//        onion.setHumidityTolerance(30);


//
//

//        myViewModelgn.getAllCrops().observe(this, crops -> {
//        });


//        addSampleCrops();
//        addSampleStepes();
//        addSampleFarmers();

//            myViewModel.getAllExpert().observe(this, experts -> {
//                if (experts == null || experts.isEmpty()) {
//  addSampleExperts();
//                }
//            });
//
//            myViewModel.getAllCrop().observe(this, crops -> {
//                if (crops == null || crops.isEmpty()) {
//                    addSampleCrops();
//                }
//            });
//
//
//            myViewModel.getTotalStepsCount().observe(this, stepCount -> {
//                if (stepCount == null || stepCount == 0) {
//                    addSampleStepes();
//                }
//            });
//
//            myViewModel.getAllFarmer().observe(this, farmers -> {
//                if (farmers == null || farmers.isEmpty()) {
//                    addSampleFarmers();
//                }
//            });