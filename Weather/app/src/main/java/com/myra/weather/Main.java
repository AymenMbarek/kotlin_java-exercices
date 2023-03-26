package com.myra.weather;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temp;


    @SerializedName("feels_like")
    private double feels_like;

    @SerializedName("humidity")
    private double humidity;

    public double getFeels_like() {
        return feels_like;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp() {
        return temp;
    }
}
