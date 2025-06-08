//package com.example.happyharvest;
//
//import androidx.room.Embedded;
//import androidx.room.Relation;
//
//import java.util.List;
//public class ForecastWithDailyWeather {
//    @Embedded
//    public WeatherForecast forecast;
//
//    @Relation(
//            parentColumn = "id",
//            entityColumn = "forecastId"
//    )
//    public List<DailyWeather> dailyList;
//}
