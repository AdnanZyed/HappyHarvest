package com.example.happyharvest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity_Main extends AppCompatActivity

        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment = new HomeFragment();
    private boolean showCustomNav;
    private String userName;

    private ImageView iv_S1, tv_Seeall21, iv_notification_h1, menu1;
    private FarmersProfileFragment prophileFragment = new FarmersProfileFragment();
    private CropsFragment cropsFragment = new CropsFragment();
    private AgriculturalChatFragment agriculturalChatFragment = new AgriculturalChatFragment();
    private CropFragment fragment = (CropFragment) getSupportFragmentManager()
            .findFragmentById(R.id.flFragment);
    private Fragment activeFragment = homeFragment;

    private Toolbar toolbar;
    private InboxFragment inboxFragment = new InboxFragment();


    @SuppressLint("UseSupportActionBar")
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
        //   toolbar = findViewById(R.id.toolbar);
        menu1 = findViewById(R.id.menu1);
        iv_notification_h1 = findViewById(R.id.iv_notification_h1);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;


        switch (item.getItemId()) {
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
        }

        if (selectedFragment != activeFragment) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, selectedFragment)
                    .commit();

            activeFragment = selectedFragment;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!(activeFragment instanceof HomeFragment)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            activeFragment = homeFragment;
//            if (showCustomNav) {
//                bottomNavigationView.setSelectedItemId(R.id.crops1);
//            } else {
//
//                bottomNavigationView.setSelectedItemId(R.id.home1);
//
//            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}
