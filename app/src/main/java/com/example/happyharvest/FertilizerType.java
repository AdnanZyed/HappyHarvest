package com.example.happyharvest;

public enum FertilizerType {
    ORGANIC("سماد عضوي"),
    CHEMICAL("سماد كيماوي");

    private String arabicName;

    FertilizerType(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicName() {
        return arabicName;
    }

    @Override
    public String toString() {
        return arabicName;
    }
}