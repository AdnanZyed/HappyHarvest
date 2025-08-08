package com.example.happyharvest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity_Main extends AppCompatActivity

        implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment = new HomeFragment();
    private boolean showCustomNav;
    private String userName;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private ImageView iv_S1, tv_Seeall21, iv_notification_h1, menu1;
    private FarmersProfileFragment prophileFragment = new FarmersProfileFragment();
    private CropsFragment cropsFragment = new CropsFragment();
    private AgriculturalChatFragment agriculturalChatFragment = new AgriculturalChatFragment();
    private CropFragment fragment = (CropFragment) getSupportFragmentManager()
            .findFragmentById(R.id.flFragment);
    private Fragment activeFragment = homeFragment;

    private Toolbar toolbar;
    private InboxFragment inboxFragment = new InboxFragment();
    private FloatingActionButton fabAdd;


    @SuppressLint({"UseSupportActionBar", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        //  showCustomNav = getIntent().getBooleanExtra("SHOW_CUSTOM_NAVIGATION", false);

        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME2");


        HomeFragment homeFragment = new HomeFragment();
        CropsFragment cropsFragment1 = new CropsFragment();
        iv_S1 = findViewById(R.id.iv_s1);
        tv_Seeall21 = findViewById(R.id.tv_Seeall21);

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity_Main.this, ExpertProfileActivity.class);
                intent1.putExtra("USER","jane_smith");
                intent1.putExtra("USERF",userName);
                startActivity(intent1);
            }
        });
        //   toolbar = findViewById(R.id.toolbar);
        menu1 = findViewById(R.id.menu1);
        iv_notification_h1 = findViewById(R.id.iv_notification_h1);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //    setActionBar(toolbar);

//        if (showCustomNav) {
//            cropsFragment.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.flFragment, cropsFragment1)
//                    .commit();
//
//            bottomNavigationView = findViewById(R.id.bottomNavigationView);
//            bottomNavigationView.setOnNavigationItemSelectedListener(this);
//            bottomNavigationView.setSelectedItemId(R.id.crops1);
//        } else {
        Bundle bundle = new Bundle();
        bundle.putString("USER_NAME", userName);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, homeFragment)
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home1);
        tv_Seeall21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Main.this, Most_Popular.class);
                intent.putExtra("USER", userName);
                startActivity(intent);
            }
        });
        iv_notification_h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Main.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        iv_S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FarmersProfileFragment fragment = new FarmersProfileFragment();
                Bundle args = new Bundle();
                args.putString("USER_NAME_R", userName);
                fragment.setArguments(args);


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        drawer = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        if (savedInstanceState == null) {
//            navigationView.setCheckedItem(R.id.nav_home);
//        }
        //  }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//            }
//        }
//        Intent intent1 = new Intent(MainActivity_Main.this, CropDetailsActivity.class);
//        intent1.putExtra("USER_NAME12", userName);
//
//
//        Intent intent2 = new Intent(MainActivity_Main.this, CropDetailsActivity.class);
//        intent2.putExtra("USER_NAME14", userName);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // معالجة ضغط أيقونة الهامبورجر في Toolbar
        if (item.getItemId() == android.R.id.home) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;

        // Handle Bottom Navigation Items
        switch (id) {
            case R.id.prophile:
                Bundle bundle4 = new Bundle();
                bundle4.putString("USER_NAME", userName);
                prophileFragment.setArguments(bundle4);
                selectedFragment = prophileFragment;
                break;

            case R.id.home1:
                Bundle bundle = new Bundle();
                bundle.putString("USER_NAME", userName);
                homeFragment.setArguments(bundle);
                selectedFragment = homeFragment;
                break;

            case R.id.Crops1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("USER_NAME", userName);
                cropsFragment.setArguments(bundle1);
                selectedFragment = cropsFragment;
                break;

            case R.id.Ai:
                Bundle bundle2 = new Bundle();
                bundle2.putString("USER_NAME", userName);
                agriculturalChatFragment.setArguments(bundle2);
                selectedFragment = agriculturalChatFragment;
                break;

            case R.id.inbox:
                Bundle bundle3 = new Bundle();
                bundle3.putString("USER_NAME", userName);
                inboxFragment.setArguments(bundle3);
                selectedFragment = inboxFragment;
                break;

            case R.id.nav_home:
                // Already on home
                break;

            case R.id.nav_profile:
                startActivity(new Intent(this, CropsProfileFragment.class));
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_help:
                startActivity(new Intent(this, HelpFeedback.class));
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_privacy:
                fetchPrivacyData();
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_language:
                showLanguageSelector(this);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_notifications:
                showNotificationSettings();
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_logout:
                logoutUser();
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }

        // Handle fragment changes for bottom navigation
        if (selectedFragment != null && selectedFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, selectedFragment)
                    .commit();
            activeFragment = selectedFragment;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    @Override
//    public void onBackPressed() {
//        if (!(activeFragment instanceof HomeFragment)) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.flFragment, homeFragment)
//                    .commit();
//            activeFragment = homeFragment;

    /// /            if (showCustomNav) {
    /// /                bottomNavigationView.setSelectedItemId(R.id.crops1);
    /// /            } else {
    /// /
    /// /                bottomNavigationView.setSelectedItemId(R.id.home1);
    /// /
    /// /            }
//        } else {
//            super.onBackPressed();
//        }
//    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void showNotificationSettings() {
        View bottomSheetView = LayoutInflater.from(this)
                .inflate(R.layout.notification_settings_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void fetchPrivacyData() {
        // تنفيذ جلب بيانات الخصوصية
        // يمكنك استخدام نفس الكود من SettingActivity
    }

    public void showLanguageSelector(Context context) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.language_prefrence_sheet, null);
        dialog.setContentView(view);

        LanguageManager langManager = new LanguageManager(context);

        Button btnEnglish = view.findViewById(R.id.btnEnglish);
        Button btnArabic = view.findViewById(R.id.btnArabic);

        Drawable checkIcon = ContextCompat.getDrawable(context, R.drawable.ic_check);
        int iconPadding = 16;

        btnEnglish.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnArabic.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        String currentLang = langManager.getLanguage();
        if ("ar".equals(currentLang)) {
            btnArabic.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null);
            btnArabic.setCompoundDrawablePadding(iconPadding);
        } else {
            btnEnglish.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null);
            btnEnglish.setCompoundDrawablePadding(iconPadding);
        }

        btnEnglish.setOnClickListener(v -> {
            langManager.setLanguage("en");
            refreshApp(context);
            dialog.dismiss();
        });

        btnArabic.setOnClickListener(v -> {
            langManager.setLanguage("ar");
            refreshApp(context);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void refreshApp(Context context) {
        Intent intent = new Intent(context, MainActivity_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        finish();
    }

    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تسجيل الخروج");
        builder.setMessage("هل أنت متأكد من رغبتك في تسجيل الخروج؟");

        builder.setPositiveButton("موافق", (dialog, which) -> {
            // تنفيذ تسجيل الخروج عند الضغط على موافق
            SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, ActivityMainSignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("إلغاء", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
