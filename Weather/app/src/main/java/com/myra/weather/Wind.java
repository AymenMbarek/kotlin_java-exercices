package com.myra.weather;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private int deg;

    private String direction;

    public double getSpeed() {
        return speed;
    }

    public String getDirection() {
        if(deg < 22.5 || deg >= 337.5)
            direction = "North";
        else if(deg >= 22.5 && deg < 77.5)
            direction = "North-East";
        else if(deg >= 77.5 && deg < 112.5)
            direction = "East";
        else if(deg >= 112.5 && deg < 157.5)
            direction = "South-East";
        else if(deg >= 157.5 && deg < 202.5)
            direction = "South";
        else if(deg >= 202.5 && deg < 247.5)
            direction = "South-West";
        else if(deg >= 247.5 && deg < 292.5)
            direction = "West";
        else if(deg >= 292.5 && deg < 337.5)
            direction = "North-West";
        return direction;
    }
}
