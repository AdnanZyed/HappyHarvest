package com.example.happyharvest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;



public class AboutFragment extends Fragment {


    My_View_Model myViewModel;
    ImageView Message;
    TextView description;
    String FarmerUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);


        String expertUserName = getArguments().getString("TEACHER_USER_NAME1");
        String CropDescription = getArguments().getString("cropDescription");
        FarmerUser = getArguments().getString("STUDENT_USER");

        Message = view.findViewById(R.id.message_btn);
        description = view.findViewById(R.id.description);

        description.setText(CropDescription);


        Bundle bundle = new Bundle();

        myViewModel.getAllExpertByUser(expertUserName).observe(getViewLifecycleOwner(), expert -> {

            if (expert != null) {
                TextView expertNameTextView = view.findViewById(R.id.expert_name1);
                TextView educationTextView = view.findViewById(R.id.expert_magor);
                ImageView expertImageView = view.findViewById(R.id.imageView4);

                Expert expertdata = expert.get(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(expertdata.getImage_expert(), 0, expertdata.getImage_expert().length);

                expertNameTextView.setText(expertdata.getExpert_name());
                educationTextView.setText(expertdata.getEducation());
                expertImageView.setImageBitmap(bitmap);


                bundle.putString("TEACHER_NAME_TEXT_VIEW", expertdata.getExpert_name());
                bundle.putString("TEACHER_USER_NAME_TEXT_VIEW", expertdata.getExpert_USER_Name());
                bundle.putString("EDUCATION_TEXT_VIEW", expertdata.getEducation());
                bundle.putString("STUDENT_USER", FarmerUser);

                bundle.putByteArray("BITMAP", expertdata.getImage_expert());
            }
        });


        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ExpertProfileActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        return view;
    }

}