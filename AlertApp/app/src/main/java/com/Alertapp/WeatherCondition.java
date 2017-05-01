package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public class WeatherCondition  extends Basecondition{


    int mintemp, maxtemp;
    int id;
    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        float temp=cs.getCurrentweather();
        int  minval=Float.compare(temp,mintemp);
        int maxval=Float.compare(maxtemp,temp);
        if(minval>0 && maxval >0)
                return true;
        return false;
    }
    public void WeatherCondition()
    {

    }

    public int getMintemp() {
        return mintemp;
    }

    public void setMintemp(int mintemp) {
        this.mintemp = mintemp;
    }

    public int getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(int maxtemp) {
        this.maxtemp = maxtemp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
