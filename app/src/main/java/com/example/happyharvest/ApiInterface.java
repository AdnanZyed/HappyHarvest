package com.example.happyharvest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("chat/completions")
    Call<ChatResponse> getChatResponse(
            @Header("Authorization") String authHeader,
            @Body ChatRequest request
    );
}