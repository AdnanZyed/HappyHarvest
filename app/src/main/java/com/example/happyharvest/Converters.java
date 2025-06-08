package com.example.happyharvest;

import androidx.room.TypeConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class Converters {

    @TypeConverter
    public static String fromUrl(URL url) {
        return url != null ? url.toString() : null;
    }

    @TypeConverter
    public static URL toUrl(String urlString) {
        try {
            return urlString != null ? new URL(urlString) : null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
