package com.example.happyharvest;

public class Step {
    String title;
    int iconResId;
    boolean isCurrent;

    public Step(String title, int iconResId, boolean isCurrent) {
        this.title = title;
        this.iconResId = iconResId;
        this.isCurrent = isCurrent;
    }
}
