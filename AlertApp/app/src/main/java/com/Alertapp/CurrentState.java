package com.Alertapp;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class CurrentState {
    float currentweather=0;
    String currentlocation="";
    Date d=new Date();
    Context context;

    public float getCurrentweather() {
        return currentweather;
    }

    public void setCurrentweather(float currentweather) {
        this.currentweather = currentweather;
    }

    public String getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
    }
    public Date getDate()
    {
        return d;
    }

}
