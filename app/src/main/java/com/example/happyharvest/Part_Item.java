package com.example.happyharvest;

public class Part_Item {
    private int imageResId;
    private String text;

    public Part_Item(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

}
