package com.example.happyharvest;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponseOneCall {

    @SerializedName("daily")
    private List<Daily> daily;

    public List<Daily> getDaily() {
        return daily;
    }

    public static class Daily {
        @SerializedName("dt")
        private long dt;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("humidity")
        private float humidity;

        public long getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public float getHumidity() {
            return humidity;
        }
    }

    public static class Temp {
        @SerializedName("day")
        private float day;

        @SerializedName("min")
        private float min;

        @SerializedName("max")
        private float max;

        public float getDay() {
            return day;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }
    }
}
