//package com.example.happyharvest;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import com.example.happyharvest.ui.main.StepFragment2;
//
//public class StepFragment extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_step);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, StepFragment2.newInstance())
//                .commitNow();
//        }
//    }
//}