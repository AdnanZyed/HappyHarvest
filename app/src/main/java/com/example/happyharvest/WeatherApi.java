package com.example.happyharvest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * واجهة للوصول إلى OpenWeatherMap API
 * API Docs: https://openweathermap.org/api
 */
public interface WeatherApi {

    @GET("data/3.0/onecall")
    Call<WeatherResponseOneCall> getSevenDayForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("exclude") String exclude,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String apiKey
    );
    /**
     * جلب بيانات الطقس الحالي
     * يستخدم نفس الكلاس WeatherResponse لسهولة التعامل
     */
    @GET("data/2.5/weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String language

    );

    /**
     * جلب بيانات الطقس التاريخية للفترة المحددة (onecall/timemachine)
     */
    @GET("data/2.5/onecall/timemachine")
    Call<HistoricalWeatherResponse> getHistoricalWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("dt") long timestamp,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String language

    );
//    @GET("forecast")
//    Call<WeatherResponse> getWeeklyForecast(
//            @Query("q") String location,
//            @Query("units") String units,
//            @Query("appid") String apiKey
//    );

//    @GET("weather")
//    Call<WeatherResponse> getWeather(
//            @Query("lat") double lat,
//            @Query("lon") double lon,
//            @Query("appid") String apiKey,
//            @Query("units") String units,
//            @Query("lang") String lang
//
//    );

}
