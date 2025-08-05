package com.example.happyharvest;

public class Crops {
    private String name;
    private int iconRes;
    private int type;

    public Crops(String name, int iconRes, int type) {
        this.name = name;
        this.iconRes = iconRes;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

