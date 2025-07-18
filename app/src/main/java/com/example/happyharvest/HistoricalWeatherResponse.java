package com.example.happyharvest;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HistoricalWeatherResponse {
    @SerializedName("lat")
    private double latitude;

    @SerializedName("lon")
    private double longitude;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("data")
    private List<HistoricalWeatherData> data;

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public List<HistoricalWeatherData> getData() {
        return data;
    }

    public static class HistoricalWeatherData {
        @SerializedName("dt")
        private long timestamp;

        @SerializedName("temp")
        private double temp;

        @SerializedName("humidity")
        private double humidity;

        @SerializedName("pressure")
        private double pressure;

        @SerializedName("wind_speed")
        private double windSpeed;

        public long getTimestamp() {
            return timestamp;
        }

        public double getTemp() {
            return temp;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getPressure() {
            return pressure;
        }

        public double getWindSpeed() {
            return windSpeed;
        }
    }
}