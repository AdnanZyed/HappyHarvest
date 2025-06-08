package com.example.happyharvest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    private Main main;
    private List<Weather> weather;
    @SerializedName("list")
    public List<ForecastItem> forecastList;

    public List<ForecastItem> getForecastList() {
        return forecastList;
    }
    private Wind wind; // ← أضفنا هذا

    public String name;
    public Wind getWind() { // ← Getter الجديد
        return wind;
    }
    public void setWind(Wind wind) { // ← Setter الجديد
        this.wind = wind;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static class Main {
        private float temp;
        private int humidity;

        public float getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class Weather {
        private String main;
        private String description; // ← أضفنا هذا
        private String icon;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description; // ← Getter الجديد
        }

        public String getIcon() {
            return icon;
        }
    }
    // ← أضفنا هذا الكلاس الجديد لبيانات الرياح
    public static class Wind {
        private float speed; // السرعة بالمتر/ثانية
        private float deg;   // الاتجاه بالدرجات (0-360)

        public float getSpeed() {
            return speed;
        }

        public float getDeg() {
            return deg;
        }
    }

}

