package com.Alertapp;

import android.util.Log;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Locationcondition extends Basecondition {

    public String location="";


    public int getId() {

        return conditionId;
    }

    public void setId(int id) {

        this.conditionId = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        Log.d(cs.currentLocation.trim() + " " + location.trim(),cs.currentLocation.trim() + " " + location.trim());
        boolean result = cs.currentLocation.trim().equalsIgnoreCase(location.trim());
        return result;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void Locationcondition()
    {

    }

}
