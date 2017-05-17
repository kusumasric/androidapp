package com.Alertapp;

import android.content.Context;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class CurrentState {

    public float currentWeather=0;
    public String currentLocation="";
    public Date date=new Date();
    public Context context;

    public float getCurrentweather() {
        return currentWeather;
    }

    public void setCurrentweather(float currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getCurrentlocation() {
        return currentLocation;
    }

    public void setCurrentlocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public Date getDate()
    {
        return date;
    }

}
