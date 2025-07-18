package com.example.happyharvest;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "CropProblem",
        foreignKeys = @ForeignKey(entity = Crop.class,
                parentColumns = "Crop_ID",
                childColumns = "cropId",
                onDelete = ForeignKey.CASCADE))
public class CropProblem {
    @PrimaryKey(autoGenerate = true)
    private int problemId;

    private int cropId;
    private String problemName;
    private String symptoms;
    private String solution;
    private String preventionMethods;
    private String severity;
    private String imageUrl;

    // Constructor
    public CropProblem(int problemId, int cropId, String problemName,
                       String symptoms, String solution, String preventionMethods,
                       String severity, String imageUrl) {
        this.problemId = problemId;
        this.cropId = cropId;
        this.problemName = problemName;
        this.symptoms = symptoms;
        this.solution = solution;
        this.preventionMethods = preventionMethods;
        this.severity = severity;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getPreventionMethods() {
        return preventionMethods;
    }

    public void setPreventionMethods(String preventionMethods) {
        this.preventionMethods = preventionMethods;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}