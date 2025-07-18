package com.example.happyharvest;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class WeatherManager {

    private static WeatherManager instance;
    private WeatherResponse weatherResponse;
    private  double LAT ;
    private  double LON ;
    private final String API_KEY = "9e269c7c20355e9e8bba48b0ad2cd52c";

    private WeatherManager() {
    }

    public static synchronized WeatherManager getInstance() {
        if (instance == null) {
            instance = new WeatherManager();
        }
        return instance;
    }

    public interface WeatherCallback {
        void onWeatherLoaded(WeatherResponse response);
        void onFailure(Throwable t);
    }

    public void fetchWeather(Context context, double LAT,double LON,WeatherCallback callback) {
        this.LAT=LAT;
        this.LON=LON;

        if (weatherResponse != null) {
            callback.onWeatherLoaded(weatherResponse);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi service = retrofit.create(WeatherApi.class);
        Call<WeatherResponse> call = service.getCurrentWeather(LAT, LON, API_KEY, "metric", "ar");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherResponse = response.body();
                    callback.onWeatherLoaded(weatherResponse);
                } else {
                    callback.onFailure(new Throwable("Empty response"));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public WeatherResponse getStoredWeather() {
        return weatherResponse;
    }
}
