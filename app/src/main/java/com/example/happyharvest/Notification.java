package com.example.happyharvest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String message;
    private int iconResId;

    public Notification(String title, String message, int iconResId) {
        this.title = title;
        this.message = message;
        this.iconResId = iconResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int getIconResId() {
        return iconResId;
    }
}
