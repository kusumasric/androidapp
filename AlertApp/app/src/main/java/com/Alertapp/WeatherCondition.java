package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    public int minTemp, maxTemp;

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        float currentTemperature=cs.getCurrentweather();
        return minTemp < currentTemperature && currentTemperature < maxTemp;
    }


    public int getMintemp() {
        return minTemp;
    }

    public void setMintemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxtemp() {
        return maxTemp;
    }

    public void setMaxtemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getId() {
        return conditionId;
    }

    public void setId(int id) {
        this.conditionId = id;
    }

}
