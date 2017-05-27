package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    public int minTemp, maxTemp;
    public WeatherCondition()
    {

    }

    public WeatherCondition(int mint, int maxt)
    {
        minTemp=mint;
        maxTemp=maxt;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        float currentTemperature=cs.getCurrentweather();
        return minTemp < currentTemperature && currentTemperature < maxTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getId() {
        return conditionId;
    }

    public void setId(int id) {
        this.conditionId = id;
    }

}
