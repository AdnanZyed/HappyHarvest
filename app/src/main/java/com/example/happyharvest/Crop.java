package com.example.happyharvest;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "Crop", foreignKeys = @ForeignKey(
        entity = Expert.class,
        parentColumns = "Expert_USER_Name",
        childColumns = "Expert_USER_Name",
        onDelete = ForeignKey.CASCADE
))
public class Crop {
    @PrimaryKey(autoGenerate = true)
    private int Crop_ID;
    private String Crop_NAME;
    // private byte[] Image;
    private String Categorie;
    private String Description;
    // private byte[] profilePicture;
    private String Expert_USER_Name;
    //  private byte[] bookmarkIcon;
    private double rating;
    private String preferredSoil;
    private String allowedSoil;
    private String forbiddenSoil;//يتم الرفض
    private String preferredIrrigation;///////////////
    private String allowedIrrigation;
    private String forbiddenIrrigation;//يتم الرفض
    private double minArea;
    private String season;///////////////// //يتم الرفض
    private String preferredHumidity;
    private String allowedHumidity;//////////////
    private String forbiddenHumidity;
    private String preferredTemp;
    private String allowedTemp;//////////////
    private String forbiddenTemp;
    private int reviews;
    private int wateringFrequencyDays;//أيام تردد الري
    private int fertilizingFrequencyDays;//أيام تردد التسميد
    private String wateringInstructions;//تعليمات الري
    private String fertilizingInstructions;//تعليمات التسميد
    private String plantingMethod;//طريقة الزراعة
    private String CropProblems;//مشاكل وحلول الزرعة
    private String Pruning_and_guidance;//التقليم والتوجيه

    private String preferredAbundance;//وفرة//////////////////////
    private String allowedAbundance ;//وفرة
    private String forbiddenAbundance;//وفرة //يتم الرفض
//
//    private byte[] planting_1;
//    private byte[] planting_2;
//    private byte[] planting_3;
//
//    private byte[] fertilizing_1;
//    private byte[] fertilizing_2;
//    private byte[] fertilizing_3;
//
//    private byte[] watering_1;
//    private byte[] watering_2;
//    private byte[] watering_3;
//
//
//    private byte[] problem_1;
//    private byte[] problem_2;
//    private byte[] problem_3;
//
//    private byte[] cover;
//
//    private byte[] stage_1;
//    private byte[] stage_2;
//    private byte[] stage_3;

    private String LearnMore;//معلومات عامة
    private float OptimalTemperature;// درجة الحرارة المثالية
    private float OptimalHumidity;// درجة الحرارة المثالية
    private int LightRequirements;
    private int Number_Plant_per_dunum;//عدد الشتلات لكل دونوم
    private String organicFertilizer;//سماد عضوي
    private double organicPerPlant; // غم/شتلة
    private String chemicalFertilizer;//سماد كيماوي
    private double chemicalPerPlant; // غم/شتلة
    private String Previous_crop_preferred;
    private String Previous_crop_allowed;
    private String Previous_crop_forbidden;
    private String soil_preparation_Favorite;
    private String Preparing_irrigation_tools_F;
    private String Preparing_irrigation_tools_P;
    private String soil_preparation_allowed;
    private String Preparing_irrigation_tools_A;

    private int weight_seeds_per_dunum; //زون البذور لكل دونوم بالقرام
    private String seedSpecifications;//مواصفات البذور
    private String seedlingPreparation;//تحضير الشتلات
    private String plantingDistance;//مسافة الزراعة
    private String plantingDepth;//عمق الزراعة
    private String initialIrrigation;//الري الأولي
    private int daysToMaturity; // عدد الأيام حتى النضج
    private String high;
    private String mid;
    private String low;
    private float TemperatureTolerance;
    private float HumidityTolerance;


    public Crop(int crop_ID, String crop_NAME, String categorie, String description, String expert_USER_Name, double rating, String preferredSoil, String allowedSoil, String forbiddenSoil, String preferredIrrigation, String allowedIrrigation, String forbiddenIrrigation, double minArea, String season, String preferredHumidity, String allowedHumidity, String forbiddenHumidity, String preferredTemp, String allowedTemp, String forbiddenTemp, int reviews, int wateringFrequencyDays, int fertilizingFrequencyDays, String wateringInstructions, String fertilizingInstructions, String plantingMethod, String cropProblems, String pruning_and_guidance, String preferredAbundance, String allowedAbundance, String forbiddenAbundance, String learnMore, float optimalTemperature, float optimalHumidity, int lightRequirements, int number_Plant_per_dunum, String organicFertilizer, double organicPerPlant, String chemicalFertilizer, double chemicalPerPlant, String previous_crop_preferred, String previous_crop_allowed, String previous_crop_forbidden, String soil_preparation_Favorite, String preparing_irrigation_tools_F, String preparing_irrigation_tools_P, String soil_preparation_allowed, String preparing_irrigation_tools_A, int weight_seeds_per_dunum, String seedSpecifications, String seedlingPreparation, String plantingDistance, String plantingDepth, String initialIrrigation, int daysToMaturity, String high, String mid, String low, float temperatureTolerance, float humidityTolerance) {
        Crop_ID = crop_ID;
        Crop_NAME = crop_NAME;
        Categorie = categorie;
        Description = description;
        Expert_USER_Name = expert_USER_Name;
        this.rating = rating;
        this.preferredSoil = preferredSoil;
        this.allowedSoil = allowedSoil;
        this.forbiddenSoil = forbiddenSoil;
        this.preferredIrrigation = preferredIrrigation;
        this.allowedIrrigation = allowedIrrigation;
        this.forbiddenIrrigation = forbiddenIrrigation;
        this.minArea = minArea;
        this.season = season;
        this.preferredHumidity = preferredHumidity;
        this.allowedHumidity = allowedHumidity;
        this.forbiddenHumidity = forbiddenHumidity;
        this.preferredTemp = preferredTemp;
        this.allowedTemp = allowedTemp;
        this.forbiddenTemp = forbiddenTemp;
        this.reviews = reviews;
        this.wateringFrequencyDays = wateringFrequencyDays;
        this.fertilizingFrequencyDays = fertilizingFrequencyDays;
        this.wateringInstructions = wateringInstructions;
        this.fertilizingInstructions = fertilizingInstructions;
        this.plantingMethod = plantingMethod;
        CropProblems = cropProblems;
        Pruning_and_guidance = pruning_and_guidance;
        this.preferredAbundance = preferredAbundance;
        this.allowedAbundance = allowedAbundance;
        this.forbiddenAbundance = forbiddenAbundance;
        LearnMore = learnMore;
        OptimalTemperature = optimalTemperature;
        OptimalHumidity = optimalHumidity;
        LightRequirements = lightRequirements;
        Number_Plant_per_dunum = number_Plant_per_dunum;
        this.organicFertilizer = organicFertilizer;
        this.organicPerPlant = organicPerPlant;
        this.chemicalFertilizer = chemicalFertilizer;
        this.chemicalPerPlant = chemicalPerPlant;
        Previous_crop_preferred = previous_crop_preferred;
        Previous_crop_allowed = previous_crop_allowed;
        Previous_crop_forbidden = previous_crop_forbidden;
        this.soil_preparation_Favorite = soil_preparation_Favorite;
        Preparing_irrigation_tools_F = preparing_irrigation_tools_F;
        Preparing_irrigation_tools_P = preparing_irrigation_tools_P;
        this.soil_preparation_allowed = soil_preparation_allowed;
        Preparing_irrigation_tools_A = preparing_irrigation_tools_A;
        this.weight_seeds_per_dunum = weight_seeds_per_dunum;
        this.seedSpecifications = seedSpecifications;
        this.seedlingPreparation = seedlingPreparation;
        this.plantingDistance = plantingDistance;
        this.plantingDepth = plantingDepth;
        this.initialIrrigation = initialIrrigation;
        this.daysToMaturity = daysToMaturity;
        this.high = high;
        this.mid = mid;
        this.low = low;
        TemperatureTolerance = temperatureTolerance;
        HumidityTolerance = humidityTolerance;
    }

    public Crop() {
    }

    public String getPreparing_irrigation_tools_P() {
        return Preparing_irrigation_tools_P;
    }

    public void setPreparing_irrigation_tools_P(String preparing_irrigation_tools_P) {
        Preparing_irrigation_tools_P = preparing_irrigation_tools_P;
    }

    public int getCrop_ID() {
        return Crop_ID;
    }

    public void setCrop_ID(int crop_ID) {
        Crop_ID = crop_ID;
    }

    public String getCrop_NAME() {
        return Crop_NAME;
    }

    public void setCrop_NAME(String crop_NAME) {
        Crop_NAME = crop_NAME;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExpert_USER_Name() {
        return Expert_USER_Name;
    }

    public void setExpert_USER_Name(String expert_USER_Name) {
        Expert_USER_Name = expert_USER_Name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPreferredSoil() {
        return preferredSoil;
    }

    public void setPreferredSoil(String preferredSoil) {
        this.preferredSoil = preferredSoil;
    }

    public String getAllowedSoil() {
        return allowedSoil;
    }

    public void setAllowedSoil(String allowedSoil) {
        this.allowedSoil = allowedSoil;
    }

    public String getForbiddenSoil() {
        return forbiddenSoil;
    }

    public void setForbiddenSoil(String forbiddenSoil) {
        this.forbiddenSoil = forbiddenSoil;
    }

    public String getPreferredIrrigation() {
        return preferredIrrigation;
    }

    public void setPreferredIrrigation(String preferredIrrigation) {
        this.preferredIrrigation = preferredIrrigation;
    }

    public String getAllowedIrrigation() {
        return allowedIrrigation;
    }

    public void setAllowedIrrigation(String allowedIrrigation) {
        this.allowedIrrigation = allowedIrrigation;
    }

    public String getForbiddenIrrigation() {
        return forbiddenIrrigation;
    }

    public void setForbiddenIrrigation(String forbiddenIrrigation) {
        this.forbiddenIrrigation = forbiddenIrrigation;
    }

    public double getMinArea() {
        return minArea;
    }

    public void setMinArea(double minArea) {
        this.minArea = minArea;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPreferredHumidity() {
        return preferredHumidity;
    }

    public void setPreferredHumidity(String preferredHumidity) {
        this.preferredHumidity = preferredHumidity;
    }

    public String getAllowedHumidity() {
        return allowedHumidity;
    }

    public void setAllowedHumidity(String allowedHumidity) {
        this.allowedHumidity = allowedHumidity;
    }

    public String getForbiddenHumidity() {
        return forbiddenHumidity;
    }

    public void setForbiddenHumidity(String forbiddenHumidity) {
        this.forbiddenHumidity = forbiddenHumidity;
    }

    public String getPreferredTemp() {
        return preferredTemp;
    }

    public void setPreferredTemp(String preferredTemp) {
        this.preferredTemp = preferredTemp;
    }

    public String getAllowedTemp() {
        return allowedTemp;
    }

    public void setAllowedTemp(String allowedTemp) {
        this.allowedTemp = allowedTemp;
    }

    public String getForbiddenTemp() {
        return forbiddenTemp;
    }

    public void setForbiddenTemp(String forbiddenTemp) {
        this.forbiddenTemp = forbiddenTemp;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public int getWateringFrequencyDays() {
        return wateringFrequencyDays;
    }

    public void setWateringFrequencyDays(int wateringFrequencyDays) {
        this.wateringFrequencyDays = wateringFrequencyDays;
    }

    public int getFertilizingFrequencyDays() {
        return fertilizingFrequencyDays;
    }

    public void setFertilizingFrequencyDays(int fertilizingFrequencyDays) {
        this.fertilizingFrequencyDays = fertilizingFrequencyDays;
    }

    public String getWateringInstructions() {
        return wateringInstructions;
    }

    public void setWateringInstructions(String wateringInstructions) {
        this.wateringInstructions = wateringInstructions;
    }

    public String getFertilizingInstructions() {
        return fertilizingInstructions;
    }

    public void setFertilizingInstructions(String fertilizingInstructions) {
        this.fertilizingInstructions = fertilizingInstructions;
    }

    public String getPlantingMethod() {
        return plantingMethod;
    }

    public void setPlantingMethod(String plantingMethod) {
        this.plantingMethod = plantingMethod;
    }

    public String getCropProblems() {
        return CropProblems;
    }

    public void setCropProblems(String cropProblems) {
        CropProblems = cropProblems;
    }

    public String getPruning_and_guidance() {
        return Pruning_and_guidance;
    }

    public void setPruning_and_guidance(String pruning_and_guidance) {
        Pruning_and_guidance = pruning_and_guidance;
    }

    public String getPreferredAbundance() {
        return preferredAbundance;
    }

    public void setPreferredAbundance(String preferredAbundance) {
        this.preferredAbundance = preferredAbundance;
    }

    public String getAllowedAbundance() {
        return allowedAbundance;
    }

    public void setAllowedAbundance(String allowedAbundance) {
        this.allowedAbundance = allowedAbundance;
    }

    public String getForbiddenAbundance() {
        return forbiddenAbundance;
    }

    public void setForbiddenAbundance(String forbiddenAbundance) {
        this.forbiddenAbundance = forbiddenAbundance;
    }

    public String getLearnMore() {
        return LearnMore;
    }

    public void setLearnMore(String learnMore) {
        LearnMore = learnMore;
    }

    public float getOptimalTemperature() {
        return OptimalTemperature;
    }

    public void setOptimalTemperature(float optimalTemperature) {
        OptimalTemperature = optimalTemperature;
    }

    public float getOptimalHumidity() {
        return OptimalHumidity;
    }

    public void setOptimalHumidity(float optimalHumidity) {
        OptimalHumidity = optimalHumidity;
    }

    public int getLightRequirements() {
        return LightRequirements;
    }

    public void setLightRequirements(int lightRequirements) {
        LightRequirements = lightRequirements;
    }

    public int getNumber_Plant_per_dunum() {
        return Number_Plant_per_dunum;
    }

    public void setNumber_Plant_per_dunum(int number_Plant_per_dunum) {
        Number_Plant_per_dunum = number_Plant_per_dunum;
    }

    public String getOrganicFertilizer() {
        return organicFertilizer;
    }

    public void setOrganicFertilizer(String organicFertilizer) {
        this.organicFertilizer = organicFertilizer;
    }

    public double getOrganicPerPlant() {
        return organicPerPlant;
    }

    public void setOrganicPerPlant(double organicPerPlant) {
        this.organicPerPlant = organicPerPlant;
    }

    public String getChemicalFertilizer() {
        return chemicalFertilizer;
    }

    public void setChemicalFertilizer(String chemicalFertilizer) {
        this.chemicalFertilizer = chemicalFertilizer;
    }

    public double getChemicalPerPlant() {
        return chemicalPerPlant;
    }

    public void setChemicalPerPlant(double chemicalPerPlant) {
        this.chemicalPerPlant = chemicalPerPlant;
    }

    public String getPrevious_crop_preferred() {
        return Previous_crop_preferred;
    }

    public void setPrevious_crop_preferred(String previous_crop_preferred) {
        Previous_crop_preferred = previous_crop_preferred;
    }

    public String getPrevious_crop_allowed() {
        return Previous_crop_allowed;
    }

    public void setPrevious_crop_allowed(String previous_crop_allowed) {
        Previous_crop_allowed = previous_crop_allowed;
    }

    public String getPrevious_crop_forbidden() {
        return Previous_crop_forbidden;
    }

    public void setPrevious_crop_forbidden(String previous_crop_forbidden) {
        Previous_crop_forbidden = previous_crop_forbidden;
    }

    public String getSoil_preparation_Favorite() {
        return soil_preparation_Favorite;
    }

    public void setSoil_preparation_Favorite(String soil_preparation_Favorite) {
        this.soil_preparation_Favorite = soil_preparation_Favorite;
    }

    public String getPreparing_irrigation_tools_F() {
        return Preparing_irrigation_tools_F;
    }

    public void setPreparing_irrigation_tools_F(String preparing_irrigation_tools_F) {
        Preparing_irrigation_tools_F = preparing_irrigation_tools_F;
    }

    public String getSoil_preparation_allowed() {
        return soil_preparation_allowed;
    }

    public void setSoil_preparation_allowed(String soil_preparation_allowed) {
        this.soil_preparation_allowed = soil_preparation_allowed;
    }

    public String getPreparing_irrigation_tools_A() {
        return Preparing_irrigation_tools_A;
    }

    public void setPreparing_irrigation_tools_A(String preparing_irrigation_tools_A) {
        Preparing_irrigation_tools_A = preparing_irrigation_tools_A;
    }

    public int getWeight_seeds_per_dunum() {
        return weight_seeds_per_dunum;
    }

    public void setWeight_seeds_per_dunum(int weight_seeds_per_dunum) {
        this.weight_seeds_per_dunum = weight_seeds_per_dunum;
    }

    public String getSeedSpecifications() {
        return seedSpecifications;
    }

    public void setSeedSpecifications(String seedSpecifications) {
        this.seedSpecifications = seedSpecifications;
    }

    public String getSeedlingPreparation() {
        return seedlingPreparation;
    }

    public void setSeedlingPreparation(String seedlingPreparation) {
        this.seedlingPreparation = seedlingPreparation;
    }

    public String getPlantingDistance() {
        return plantingDistance;
    }

    public void setPlantingDistance(String plantingDistance) {
        this.plantingDistance = plantingDistance;
    }

    public String getPlantingDepth() {
        return plantingDepth;
    }

    public void setPlantingDepth(String plantingDepth) {
        this.plantingDepth = plantingDepth;
    }

    public String getInitialIrrigation() {
        return initialIrrigation;
    }

    public void setInitialIrrigation(String initialIrrigation) {
        this.initialIrrigation = initialIrrigation;
    }

    public int getDaysToMaturity() {
        return daysToMaturity;
    }

    public void setDaysToMaturity(int daysToMaturity) {
        this.daysToMaturity = daysToMaturity;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public float getTemperatureTolerance() {
        return TemperatureTolerance;
    }

    public void setTemperatureTolerance(float temperatureTolerance) {
        TemperatureTolerance = temperatureTolerance;
    }

    public float getHumidityTolerance() {
        return HumidityTolerance;
    }

    public void setHumidityTolerance(float humidityTolerance) {
        HumidityTolerance = humidityTolerance;
    }


}