package com.example.happyharvest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ProblemsSolutionsFragment extends Fragment {

    private static final String ARG_CROP_ID = "crop_id";

    private LinearLayout problemsContainer;
    private My_View_Model myViewModel;
    private int cropId;

    public static ProblemsSolutionsFragment newInstance(int cropId) {
        ProblemsSolutionsFragment fragment = new ProblemsSolutionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CROP_ID, cropId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cropId = getArguments().getInt(ARG_CROP_ID);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problems_solutions, container, false);
        problemsContainer = view.findViewById(R.id.problems_container);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(My_View_Model.class);
        myViewModel.getAllCropsById(cropId).observe(getViewLifecycleOwner(), crops -> {
            if (crops != null && !crops.isEmpty()) {
          //      problemsContainer.removeAllViews();

                for (Crop crop : crops) {
                    View problemView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_problem_solution, problemsContainer, false);

                    TextView textProblem = problemView.findViewById(R.id.text_problem);
                    TextView textSolution = problemView.findViewById(R.id.text_solution);
                    TextView textSymptoms = problemView.findViewById(R.id.text_symptoms);

                    textProblem.setText(crop.getCropProblems());
//                    textSolution.setText(crop.getCropSolution());
//                    textSymptoms.setText("الأعراض: " + crop.getCropSymptoms());

                  //  problemsContainer.addView(problemView);
                }
            }
        });
    }
}
