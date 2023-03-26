package com.myra.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {
    @GET("weather")
    Call<Weather> getData(@Query("q") String city, @Query("appid") String apiKey);
}
