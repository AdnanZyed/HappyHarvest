package com.example.happyharvest;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Arrays;


@Entity(tableName = "Expert_Reviews", foreignKeys = @ForeignKey(
        entity = Expert.class,
        parentColumns = "Expert_USER_Name",
        childColumns = "Expert_USER_Name",
        onDelete = ForeignKey.CASCADE
))
public class Expert_Reviews {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private byte[] image;
    private String name;
    private String s_u_name;
    private String comment;
    private String formattedDate;
    private int love;
    private String Expert_USER_Name;
    private int rating;
    private boolean isChecked;


    public Expert_Reviews(int id, byte[] image, String name, String s_u_name, String comment, String formattedDate, int love, String expert_USER_Name, int rating, boolean isChecked) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.s_u_name = s_u_name;
        this.comment = comment;
        this.formattedDate = formattedDate;
        this.love = love;
        Expert_USER_Name = expert_USER_Name;
        this.rating = rating;
        this.isChecked = isChecked;
    }

    public Expert_Reviews() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getS_u_name() {
        return s_u_name;
    }

    public void setS_u_name(String s_u_name) {
        this.s_u_name = s_u_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getExpert_USER_Name() {
        return Expert_USER_Name;
    }

    public void setExpert_USER_Name(String expert_USER_Name) {
        Expert_USER_Name = expert_USER_Name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Expert_Reviews{" +
                "id=" + id +
                ", image=" + Arrays.toString(image) +
                ", name='" + name + '\'' +
                ", s_u_name='" + s_u_name + '\'' +
                ", comment='" + comment + '\'' +
                ", formattedDate='" + formattedDate + '\'' +
                ", love=" + love +
                ", Expert_USER_Name='" + Expert_USER_Name + '\'' +
                ", rating=" + rating +
                ", isChecked=" + isChecked +
                '}';
    }
}
