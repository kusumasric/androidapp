package com.Alertapp;

import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class CurrentState {

    public float temperature;
    public String city;
    public Date date=new Date();

    public CurrentState(float temperature, String city) {
        this.temperature = temperature;
        this.city = city;
    }
    public float getTemperature() {
        return temperature;
    }

    public String getCity() {
        return city;
    }

    public Date getDate()
    {
        return date;
    }

}
