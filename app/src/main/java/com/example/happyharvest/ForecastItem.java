package com.example.happyharvest;

import com.google.gson.annotations.SerializedName;

public class ForecastItem {

    @SerializedName("dt_txt")
    private String dateText;

    @SerializedName("main")
    private MainWeather main;

    public String getDateText() {
        return dateText;
    }

    public MainWeather getMain() {
        return main;
    }

    public static class MainWeather {
        @SerializedName("temp")
        private float temperature;

        @SerializedName("humidity")
        private float humidity;

        public float getTemperature() {
            return temperature;
        }

        public float getHumidity() {
            return humidity;
        }
    }
}
