package com.example.happyharvest;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Arrays;
@Entity(tableName = "Expert")
public class Expert {
    @PrimaryKey
    @NonNull
    private String Expert_USER_Name;
    private String Expert_name;
    private String Education;
    private String Expert_PASSWORD;
    private byte[] Image_expert;
    public String location; // ممكن نخزنها كنص أو نحط Latitude و Longitude منفصلين
    public String soilType;
    public String irrigationType;
    public double area;

    public Expert(@NonNull String expert_USER_Name, String expert_name, String education, String expert_PASSWORD, byte[] image_expert, String location, String soilType, String irrigationType, double area) {
        Expert_USER_Name = expert_USER_Name;
        Expert_name = expert_name;
        Education = education;
        Expert_PASSWORD = expert_PASSWORD;
        Image_expert = image_expert;
        this.location = location;
        this.soilType = soilType;
        this.irrigationType = irrigationType;
        this.area = area;
    }

    public Expert() {
    }

    @NonNull
    public String getExpert_USER_Name() {
        return Expert_USER_Name;
    }

    public void setExpert_USER_Name(@NonNull String expert_USER_Name) {
        Expert_USER_Name = expert_USER_Name;
    }

    public String getExpert_name() {
        return Expert_name;
    }

    public void setExpert_name(String expert_name) {
        Expert_name = expert_name;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getExpert_PASSWORD() {
        return Expert_PASSWORD;
    }

    public void setExpert_PASSWORD(String expert_PASSWORD) {
        Expert_PASSWORD = expert_PASSWORD;
    }

    public byte[] getImage_expert() {
        return Image_expert;
    }

    public void setImage_expert(byte[] image_expert) {
        Image_expert = image_expert;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "Expert_USER_Name='" + Expert_USER_Name + '\'' +
                ", Expert_name='" + Expert_name + '\'' +
                ", Education='" + Education + '\'' +
                ", Expert_PASSWORD='" + Expert_PASSWORD + '\'' +
                ", Image_expert=" + Arrays.toString(Image_expert) +
                ", location='" + location + '\'' +
                ", soilType='" + soilType + '\'' +
                ", irrigationType='" + irrigationType + '\'' +
                ", area=" + area +
                '}';
    }
}

