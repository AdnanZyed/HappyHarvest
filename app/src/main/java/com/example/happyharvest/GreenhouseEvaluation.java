package com.example.happyharvest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "greenhouse_evaluations")
public class GreenhouseEvaluation {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String farmerUserName;
    private int cropId;
    private String evaluationDate;
    private String location;
    private String greenhouseType;
    private String soilType;
    private String irrigationType;
    private String ventilationType;
    private boolean hasHeating;
    private boolean hasCooling;
    private boolean hasLighting;
    private boolean hasCO2;
    private boolean hasAutomation;
    private float temperature;
    private float humidity;
    private int successRate;
    private boolean isSuitable;

    // Constructor
    public GreenhouseEvaluation(String farmerUserName, int cropId, String evaluationDate,
                                String location, String greenhouseType, String soilType,
                                String irrigationType, String ventilationType, boolean hasHeating,
                                boolean hasCooling, boolean hasLighting, boolean hasCO2,
                                boolean hasAutomation, float temperature, float humidity,
                                int successRate, boolean isSuitable) {
        this.farmerUserName = farmerUserName;
        this.cropId = cropId;
        this.evaluationDate = evaluationDate;
        this.location = location;
        this.greenhouseType = greenhouseType;
        this.soilType = soilType;
        this.irrigationType = irrigationType;
        this.ventilationType = ventilationType;
        this.hasHeating = hasHeating;
        this.hasCooling = hasCooling;
        this.hasLighting = hasLighting;
        this.hasCO2 = hasCO2;
        this.hasAutomation = hasAutomation;
        this.temperature = temperature;
        this.humidity = humidity;
        this.successRate = successRate;
        this.isSuitable = isSuitable;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarmerUserName() {
        return farmerUserName;
    }

    public void setFarmerUserName(String farmerUserName) {
        this.farmerUserName = farmerUserName;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGreenhouseType() {
        return greenhouseType;
    }

    public void setGreenhouseType(String greenhouseType) {
        this.greenhouseType = greenhouseType;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getIrrigationType() {
        return irrigationType;
    }

    public void setIrrigationType(String irrigationType) {
        this.irrigationType = irrigationType;
    }

    public String getVentilationType() {
        return ventilationType;
    }

    public void setVentilationType(String ventilationType) {
        this.ventilationType = ventilationType;
    }

    public boolean isHasHeating() {
        return hasHeating;
    }

    public void setHasHeating(boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    public boolean isHasCooling() {
        return hasCooling;
    }

    public void setHasCooling(boolean hasCooling) {
        this.hasCooling = hasCooling;
    }

    public boolean isHasLighting() {
        return hasLighting;
    }

    public void setHasLighting(boolean hasLighting) {
        this.hasLighting = hasLighting;
    }

    public boolean isHasCO2() {
        return hasCO2;
    }

    public void setHasCO2(boolean hasCO2) {
        this.hasCO2 = hasCO2;
    }

    public boolean isHasAutomation() {
        return hasAutomation;
    }

    public void setHasAutomation(boolean hasAutomation) {
        this.hasAutomation = hasAutomation;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public boolean isSuitable() {
        return isSuitable;
    }

    public void setSuitable(boolean suitable) {
        isSuitable = suitable;
    }

    @Override
    public String toString() {
        return "GreenhouseEvaluation{" +
                "id=" + id +
                ", farmerUserName='" + farmerUserName + '\'' +
                ", cropId=" + cropId +
                ", evaluationDate='" + evaluationDate + '\'' +
                ", location='" + location + '\'' +
                ", greenhouseType='" + greenhouseType + '\'' +
                ", soilType='" + soilType + '\'' +
                ", irrigationType='" + irrigationType + '\'' +
                ", ventilationType='" + ventilationType + '\'' +
                ", hasHeating=" + hasHeating +
                ", hasCooling=" + hasCooling +
                ", hasLighting=" + hasLighting +
                ", hasCO2=" + hasCO2 +
                ", hasAutomation=" + hasAutomation +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", successRate=" + successRate +
                ", isSuitable=" + isSuitable +
                '}';
    }

}