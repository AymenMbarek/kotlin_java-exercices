package com.myra.weather;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sun {
    @SerializedName("sunrise")
    private int sunrise;

    @SerializedName("sunset")
    private int sunset;

    private final SimpleDateFormat f;

    public Sun() {
        f = new SimpleDateFormat("hh:mm aa z");
    }

    public String getSunrise() {
        Date sunriseTime = new Date(sunrise * 1000L);
        return f.format(sunriseTime);
    }

    public String getSunset() {
        Date sunsetTime = new Date(sunset * 1000L);
        return f.format(sunsetTime);
    }
}
