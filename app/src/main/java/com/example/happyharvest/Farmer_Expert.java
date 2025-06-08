package com.example.happyharvest;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "Farmer_Expert",
        foreignKeys = {
                @ForeignKey(
                        entity = Expert.class,
                        parentColumns = "Expert_USER_Name",
                        childColumns = "expertUserName",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Farmer.class,
                        parentColumns = "Farmer_user_name",
                        childColumns = "farmerUserName",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "expertUserName"),
                @Index(value = "farmerUserName")
        }
)
public class Farmer_Expert {
    @PrimaryKey(autoGenerate = true)
    private int id_st;
    @NonNull
    public String farmerUserName;

    @NonNull
    public String expertUserName;


    public Farmer_Expert(int id_st, @NonNull String farmerUserName, @NonNull String expertUserName) {
        this.id_st = id_st;
        this.farmerUserName = farmerUserName;
        this.expertUserName = expertUserName;
    }

    public Farmer_Expert() {
    }

    public int getId_st() {
        return id_st;
    }

    public void setId_st(int id_st) {
        this.id_st = id_st;
    }

    @NonNull
    public String getFarmerUserName() {
        return farmerUserName;
    }

    public void setFarmerUserName(@NonNull String farmerUserName) {
        this.farmerUserName = farmerUserName;
    }

    @NonNull
    public String getExpertUserName() {
        return expertUserName;
    }

    public void setExpertUserName(@NonNull String expertUserName) {
        this.expertUserName = expertUserName;
    }

    @Override
    public String toString() {
        return "Farmer_Expert{" +
                "id_st=" + id_st +
                ", farmerUserName='" + farmerUserName + '\'' +
                ", expertUserName='" + expertUserName + '\'' +
                '}';
    }
}
