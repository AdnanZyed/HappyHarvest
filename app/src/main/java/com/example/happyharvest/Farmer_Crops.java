package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "Farmer_Crops",
        primaryKeys = {"Farmer_user_name", "Crop_ID"},
        foreignKeys = {
                @ForeignKey(entity = Farmer.class, parentColumns = "Farmer_user_name", childColumns = "Farmer_user_name", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Crop.class, parentColumns = "Crop_ID", childColumns = "Crop_ID", onDelete = ForeignKey.CASCADE)
        }
)

public class Farmer_Crops {
    @NonNull
    private String Farmer_user_name;
    private int Crop_ID;
    private boolean isBookmark;
    private boolean isRegister;
    private String startDate;/////////
    ///
    private int successRate;
    private boolean isAccepted;
    private String soilType;
    private String irrigationType;/////////////
    ///
    private String waterAvailability;///////////
    ///
    private double Land_area;
    private String Previous_crop;//المحصول السابق___________________________
    private double Average_humidity;
    private boolean Paid_subscription; //هل هو مشترك بالنسخة المدفوعة
    private String season;//____________________________للبيت البلاستيكي
    private double Latitude;///////////لما جيب الاي بي اي وافعل الاشعارات بصير اجيب الامطار الرياح الرطوبة الحرارة...وبعدل ع هيك في البيت والبور
    private double Longitude;


    private float averageTemperature;
    private boolean isAddCart;
    private int rating;
    private String lastFertilizerDate;//بتتعدل باستمرار
    private String lastWaterDate;//بتتعدل باستمرار
    private String greenhouseType;
    private String ventilation;
    private boolean hasLighting;
    private boolean hasHeating;
    private boolean hasCooling;
    private boolean hasCO2;
    private boolean hasAutomation;
    private String OrganicFertilizer;
    private String ChemicalFertilizer;
    private float humidity; //بتتعدل باستمرار
    private float windSpeed; //بتتعدل باستمرار
//    private Integer customWateringDays;
//    private Integer customFertilizingDays;
    private String Selecting_seeds_or_seedlings;
    private String Priority_previous_crop;
    private int wateringFrequencyDays_F;//بتتعدل باستمرار
    private int fertilizingFrequencyDays_F;//بتتعدل باستمرار

    private boolean Soil;
    private boolean Agriculture;
    private boolean Care;
    private boolean Done;
    // في Farmer_Crops.java
//    private List<FertilizationRecord> fertilizationHistory;
//
//    public void addFertilizationRecord(String date, String type, double amount) {
//        fertilizationHistory.add(new FertilizationRecord(date, type, amount));
//    }


    public Farmer_Crops(@NonNull String farmer_user_name, int crop_ID, boolean isBookmark, boolean isRegister, String startDate, int successRate, boolean isAccepted, String soilType, String irrigationType, String waterAvailability, double land_area, String previous_crop, double average_humidity, boolean paid_subscription, String season, double latitude, double longitude, float averageTemperature, boolean isAddCart, int rating, String lastFertilizerDate, String lastWaterDate, String greenhouseType, String ventilation, boolean hasLighting, boolean hasHeating, boolean hasCooling, boolean hasCO2, boolean hasAutomation, String organicFertilizer, String chemicalFertilizer, float humidity, float windSpeed, String selecting_seeds_or_seedlings, String priority_previous_crop, int wateringFrequencyDays_F, int fertilizingFrequencyDays_F, boolean soil, boolean agriculture, boolean care, boolean done) {
        Farmer_user_name = farmer_user_name;
        Crop_ID = crop_ID;
        this.isBookmark = isBookmark;
        this.isRegister = isRegister;
        this.startDate = startDate;
        this.successRate = successRate;
        this.isAccepted = isAccepted;
        this.soilType = soilType;
        this.irrigationType = irrigationType;
        this.waterAvailability = waterAvailability;
        Land_area = land_area;
        Previous_crop = previous_crop;
        Average_humidity = average_humidity;
        Paid_subscription = paid_subscription;
        this.season = season;
        Latitude = latitude;
        Longitude = longitude;
        this.averageTemperature = averageTemperature;
        this.isAddCart = isAddCart;
        this.rating = rating;
        this.lastFertilizerDate = lastFertilizerDate;
        this.lastWaterDate = lastWaterDate;
        this.greenhouseType = greenhouseType;
        this.ventilation = ventilation;
        this.hasLighting = hasLighting;
        this.hasHeating = hasHeating;
        this.hasCooling = hasCooling;
        this.hasCO2 = hasCO2;
        this.hasAutomation = hasAutomation;
        OrganicFertilizer = organicFertilizer;
        ChemicalFertilizer = chemicalFertilizer;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        Selecting_seeds_or_seedlings = selecting_seeds_or_seedlings;
        Priority_previous_crop = priority_previous_crop;
        this.wateringFrequencyDays_F = wateringFrequencyDays_F;
        this.fertilizingFrequencyDays_F = fertilizingFrequencyDays_F;
        Soil = soil;
        Agriculture = agriculture;
        Care = care;
        Done = done;
    }

    public Farmer_Crops() {
    }

    public boolean isSoil() {
        return Soil;
    }

    public void setSoil(boolean soil) {
        Soil = soil;
    }

    public boolean isAgriculture() {
        return Agriculture;
    }

    public void setAgriculture(boolean agriculture) {
        Agriculture = agriculture;
    }

    public boolean isCare() {
        return Care;
    }

    public void setCare(boolean care) {
        Care = care;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public int getWateringFrequencyDays_F() {
        return wateringFrequencyDays_F;
    }

    public void setWateringFrequencyDays_F(int wateringFrequencyDays_F) {
        this.wateringFrequencyDays_F = wateringFrequencyDays_F;
    }

    public int getFertilizingFrequencyDays_F() {
        return fertilizingFrequencyDays_F;
    }

    public void setFertilizingFrequencyDays_F(int fertilizingFrequencyDays_F) {
        this.fertilizingFrequencyDays_F = fertilizingFrequencyDays_F;
    }

    public String getPriority_previous_crop() {
        return Priority_previous_crop;
    }

    public void setPriority_previous_crop(String priority_previous_crop) {
        Priority_previous_crop = priority_previous_crop;
    }

    public String getSelecting_seeds_or_seedlings() {
        return Selecting_seeds_or_seedlings;
    }

    public void setSelecting_seeds_or_seedlings(String selecting_seeds_or_seedlings) {
        Selecting_seeds_or_seedlings = selecting_seeds_or_seedlings;
    }

    @NonNull
    public String getFarmer_user_name() {
        return Farmer_user_name;
    }

    public void setFarmer_user_name(@NonNull String farmer_user_name) {
        Farmer_user_name = farmer_user_name;
    }

    public int getCrop_ID() {
        return Crop_ID;
    }

    public void setCrop_ID(int crop_ID) {
        Crop_ID = crop_ID;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
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

    public String getWaterAvailability() {
        return waterAvailability;
    }

    public void setWaterAvailability(String waterAvailability) {
        this.waterAvailability = waterAvailability;
    }

    public double getLand_area() {
        return Land_area;
    }

    public void setLand_area(double land_area) {
        Land_area = land_area;
    }

    public String getPrevious_crop() {
        return Previous_crop;
    }

    public void setPrevious_crop(String previous_crop) {
        Previous_crop = previous_crop;
    }

    public double getAverage_humidity() {
        return Average_humidity;
    }

    public void setAverage_humidity(double average_humidity) {
        Average_humidity = average_humidity;
    }

    public boolean isPaid_subscription() {
        return Paid_subscription;
    }

    public void setPaid_subscription(boolean paid_subscription) {
        Paid_subscription = paid_subscription;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(float averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public boolean isAddCart() {
        return isAddCart;
    }

    public void setAddCart(boolean addCart) {
        isAddCart = addCart;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLastFertilizerDate() {
        return lastFertilizerDate;
    }

    public void setLastFertilizerDate(String lastFertilizerDate) {
        this.lastFertilizerDate = lastFertilizerDate;
    }

    public String getLastWaterDate() {
        return lastWaterDate;
    }

    public void setLastWaterDate(String lastWaterDate) {
        this.lastWaterDate = lastWaterDate;
    }

    public String getGreenhouseType() {
        return greenhouseType;
    }

    public void setGreenhouseType(String greenhouseType) {
        this.greenhouseType = greenhouseType;
    }

    public String getVentilation() {
        return ventilation;
    }

    public void setVentilation(String ventilation) {
        this.ventilation = ventilation;
    }

    public boolean isHasLighting() {
        return hasLighting;
    }

    public void setHasLighting(boolean hasLighting) {
        this.hasLighting = hasLighting;
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

    public String getOrganicFertilizer() {
        return OrganicFertilizer;
    }

    public void setOrganicFertilizer(String organicFertilizer) {
        OrganicFertilizer = organicFertilizer;
    }

    public String getChemicalFertilizer() {
        return ChemicalFertilizer;
    }

    public void setChemicalFertilizer(String chemicalFertilizer) {
        ChemicalFertilizer = chemicalFertilizer;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }


}