//package com.example.happyharvest;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Transaction;
//
//import java.util.List;
//
//@Dao
//public interface WeatherDao {
//
//    @Insert
//    long insertForecast(WeatherForecast forecast);
//
//    @Insert
//    void insertDailyWeather(List<DailyWeather> dailyList);
//
//    @Transaction
//    @Query("SELECT * FROM weather_forecast WHERE id = :forecastId")
//    ForecastWithDailyWeather getForecastWithDays(int forecastId);
//}
