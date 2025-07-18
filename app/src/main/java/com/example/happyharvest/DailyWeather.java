package com.example.happyharvest;

import java.util.Date;

public class DailyWeather {
    private Date date;
    private float temperature;
    private float humidity;

    public DailyWeather(Date date, float temperature, float humidity) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Date getDate() { return date; }
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
}