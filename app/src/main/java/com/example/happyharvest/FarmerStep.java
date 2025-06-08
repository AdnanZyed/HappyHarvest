package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "FarmerStep",
        primaryKeys = {"farmerUserName", "stepId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Farmer.class,
                        parentColumns = "Farmer_user_name",
                        childColumns = "farmerUserName",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CropStep.class,
                        parentColumns = "s_id",
                        childColumns = "stepId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class FarmerStep {
    @NonNull
    private String farmerUserName;
    private int stepId;
    private boolean completed;

    public FarmerStep(@NonNull String farmerUserName, int stepId, boolean completed) {
        this.farmerUserName = farmerUserName;
        this.stepId = stepId;
        this.completed = completed;
    }


    @NonNull
    public String getFarmerUserName() {
        return farmerUserName;
    }

    public void setFarmerUserName(@NonNull String farmerUserName) {
        this.farmerUserName = farmerUserName;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
