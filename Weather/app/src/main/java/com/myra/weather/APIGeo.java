package com.myra.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIGeo {
    @GET("weather")
    Call<com.myra.weather.Weather> getData(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey);
}