package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    public int minTemp, maxTemp;


    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        float temp=cs.getCurrentweather();
        int  minval=Float.compare(temp,minTemp);
        int maxval=Float.compare(maxTemp,temp);
        if(minval>0 && maxval >0)
                return true;
        return false;
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
