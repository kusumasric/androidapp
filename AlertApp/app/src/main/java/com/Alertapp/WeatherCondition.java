package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    String weather="";
    int id;
    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        if(weather==cs.currentweather)
            return true;
        else
            return false;
    }
    public void WeatherCondition()
    {

    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public  void setid(int id)
    {
        id=id;
    }

    public void setruleid(int ruleid)
    {
        rule.id=ruleid;
    }



}
