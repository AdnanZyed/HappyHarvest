package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "Farmer")
public class Farmer {

    @PrimaryKey
    @NonNull
    private String Farmer_user_name;
    private String Farmer_Password;
    private int phone_nomber;
    private int Card_Number;
    private String S_name;
    private byte[] s_Image;
    private String bio;

    public Farmer(@NonNull String farmer_user_name, String farmer_Password, int phone_nomber, int card_Number, String s_name, byte[] s_Image, String bio) {
        Farmer_user_name = farmer_user_name;
        Farmer_Password = farmer_Password;
        this.phone_nomber = phone_nomber;
        Card_Number = card_Number;
        S_name = s_name;
        this.s_Image = s_Image;
        this.bio = bio;
    }
    public Farmer() {
    }

    @NonNull
    public String getFarmer_user_name() {
        return Farmer_user_name;
    }

    public void setFarmer_user_name(@NonNull String farmer_user_name) {
        Farmer_user_name = farmer_user_name;
    }

    public String getFarmer_Password() {
        return Farmer_Password;
    }

    public void setFarmer_Password(String farmer_Password) {
        Farmer_Password = farmer_Password;
    }

    public int getPhone_nomber() {
        return phone_nomber;
    }

    public void setPhone_nomber(int phone_nomber) {
        this.phone_nomber = phone_nomber;
    }

    public int getCard_Number() {
        return Card_Number;
    }

    public void setCard_Number(int card_Number) {
        Card_Number = card_Number;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    public byte[] getS_Image() {
        return s_Image;
    }

    public void setS_Image(byte[] s_Image) {
        this.s_Image = s_Image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "Farmer_user_name='" + Farmer_user_name + '\'' +
                ", Farmer_Password='" + Farmer_Password + '\'' +
                ", phone_nomber=" + phone_nomber +
                ", Card_Number=" + Card_Number +
                ", S_name='" + S_name + '\'' +
                ", s_Image=" + Arrays.toString(s_Image) +
                ", bio='" + bio + '\'' +
                '}';
    }
}