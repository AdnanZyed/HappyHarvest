package com.example.happyharvest;

import com.example.happyharvest.DailyWeather;

import java.util.List;

public class WeatherForecast {
    private List<DailyWeather> weeklyForecast;

    public WeatherForecast(List<DailyWeather> weeklyForecast) {
        this.weeklyForecast = weeklyForecast;
    }

    public List<DailyWeather> getWeeklyForecast() {
        return weeklyForecast;
    }
}