package com.example.happyharvest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageConverter {

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        return null;
    }
}
