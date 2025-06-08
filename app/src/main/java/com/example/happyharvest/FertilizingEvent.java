package com.example.happyharvest;

public class FertilizingEvent {
    private String date;
    private String cropName;
    private FertilizerType type;
    private String fertilizerName;
    private double amount;

    public FertilizingEvent() {}

    public FertilizingEvent(String date, String cropName, FertilizerType type, String fertilizerName, double amount) {
        this.date = date;
        this.cropName = cropName;
        this.type = type;
        this.fertilizerName = fertilizerName;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public FertilizerType getType() {
        return type;
    }

    public void setType(FertilizerType type) {
        this.type = type;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return (date + cropName + type.toString()).hashCode();
    }
}