package com.myra.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Weather {
    @SerializedName("main")
    private com.myra.weather.Main main;

    @SerializedName("weather")
    private ArrayList<Details> details;

    @SerializedName("wind")
    private com.myra.weather.Wind wind;

    @SerializedName("sys")
    private com.myra.weather.Sun sun;

    @SerializedName("cod")
    private int err_cod;

    public int getErr_cod() {
        return err_cod;
    }

    public com.myra.weather.Main getMain() {
        return main;
    }

    public Details getDetails() {
        return details.get(0);
    }

    public com.myra.weather.Wind getWind() {
        return wind;
    }

    public com.myra.weather.Sun getSun() {
        return sun;
    }
}
