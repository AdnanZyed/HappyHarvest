package com.example.happyharvest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CareFragment extends Fragment {
    private EditText WateringFrequencyDays, FertilizingFrequencyDays, wateringInstructions,
            FertilizingInstructions, PlantingMethod, CropProblems, Pruning_and_guidance,
            LearnMore, Number_Plant_per_dunum, ChemicalPerPlant, OrganicPerPlant,
            Weight_seeds_per_dunum, SeedSpecifications, SeedlingPreparation, PlantingDistance,
            PlantingDepth, InitialIrrigation, DaysToMaturity, High, Mid, Low;
    private Spinner OrganicFertilizer, ChemicalFertilizer;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_care, container, false);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("crops");

        WateringFrequencyDays = view.findViewById(R.id.wateringFrequencyDays);
        FertilizingFrequencyDays = view.findViewById(R.id.fertilizingFrequencyDays);
        wateringInstructions = view.findViewById(R.id.wateringInstructions);
        FertilizingInstructions = view.findViewById(R.id.fertilizingInstructions);
        PlantingMethod = view.findViewById(R.id.plantingMethod);
        CropProblems = view.findViewById(R.id.cropProblems);
        Pruning_and_guidance = view.findViewById(R.id.pruning_and_guidance);
        LearnMore = view.findViewById(R.id.learnMore);
        Number_Plant_per_dunum = view.findViewById(R.id.number_Plant_per_dunum);
        ChemicalPerPlant = view.findViewById(R.id.chemicalPerPlant);
        OrganicPerPlant = view.findViewById(R.id.organicPerPlant);
        Weight_seeds_per_dunum = view.findViewById(R.id.weight_seeds_per_dunum);
        SeedSpecifications = view.findViewById(R.id.seedSpecifications);
        SeedlingPreparation = view.findViewById(R.id.seedlingPreparation);
        PlantingDistance = view.findViewById(R.id.plantingDistance);
        PlantingDepth = view.findViewById(R.id.plantingDepth);
        InitialIrrigation = view.findViewById(R.id.initialIrrigation);
        DaysToMaturity = view.findViewById(R.id.daysToMaturity);
        High = view.findViewById(R.id.high);
        Mid = view.findViewById(R.id.mid);
        Low = view.findViewById(R.id.low);
        OrganicFertilizer = view.findViewById(R.id.sp_organicFertilizer);
        ChemicalFertilizer = view.findViewById(R.id.chemicalFertilizer);
        return view;
    }

//    public void saveCropToFirebase() {
//        Crop crop = getCareInfo(new Crop());
//
//        String cropId = databaseReference.push().getKey();
//        if (cropId != null) {
//            databaseReference.child(cropId).setValue(crop)
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            showToast("تم حفظ المحصول بنجاح في Firebase");
//                        } else {
//                            showToast("فشل في حفظ المحصول");
//                        }
//                    });
//        }
//    }

    Crop getCareInfo(Crop crop) {
        try {
            crop.setWateringFrequencyDays(Integer.parseInt(WateringFrequencyDays.getText().toString().trim()));
            crop.setFertilizingFrequencyDays(Integer.parseInt(FertilizingFrequencyDays.getText().toString().trim()));
            crop.setWateringInstructions(wateringInstructions.getText().toString().trim());
            crop.setFertilizingInstructions(FertilizingInstructions.getText().toString().trim());
            crop.setPlantingMethod(PlantingMethod.getText().toString().trim());
            crop.setCropProblems(CropProblems.getText().toString().trim());
            crop.setPruning_and_guidance(Pruning_and_guidance.getText().toString().trim());
            crop.setLearnMore(LearnMore.getText().toString().trim());
            crop.setNumber_Plant_per_dunum(Integer.parseInt(Number_Plant_per_dunum.getText().toString().trim()));
            crop.setOrganicFertilizer(OrganicFertilizer.getSelectedItem().toString());
            crop.setOrganicPerPlant(Double.parseDouble(OrganicPerPlant.getText().toString().trim()));
            crop.setChemicalFertilizer(ChemicalFertilizer.getSelectedItem().toString());
            crop.setChemicalPerPlant(Double.parseDouble(ChemicalPerPlant.getText().toString().trim()));
            crop.setWeight_seeds_per_dunum(Integer.parseInt(Weight_seeds_per_dunum.getText().toString().trim()));
            crop.setSeedSpecifications(SeedSpecifications.getText().toString().trim());
            crop.setSeedlingPreparation(SeedlingPreparation.getText().toString().trim());
            crop.setPlantingDistance(PlantingDistance.getText().toString().trim());
            crop.setPlantingDepth(PlantingDepth.getText().toString().trim());
            crop.setInitialIrrigation(InitialIrrigation.getText().toString().trim());
            crop.setDaysToMaturity(Integer.parseInt(DaysToMaturity.getText().toString().trim()));
            crop.setHigh(High.getText().toString().trim());
            crop.setMid(Mid.getText().toString().trim());
            crop.setLow(Low.getText().toString().trim());
        } catch (Exception e) {
            showToast("خطأ في إدخال البيانات: " + e.getMessage());
        }
        return crop;
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}