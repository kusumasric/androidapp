package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    public int minTemp, maxTemp;

    public WeatherCondition(int mint, int maxt)
    {
        minTemp=mint;
        maxTemp=maxt;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        float currentTemperature=cs.getTemperature();
        return minTemp < currentTemperature && currentTemperature < maxTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getId() {
        return conditionId;
    }

}
