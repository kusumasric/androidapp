package com.Alertapp;

import android.util.Log;

/**
 * Created by kusumasri on 4/24/17.
 */

public class LocationCondition extends Basecondition {

    private String location;

    public LocationCondition(String loc)
    {
        location=loc;
    }

    public int getId() {
        return conditionId;
    }

    public void setId(int id) {
        this.conditionId = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        boolean result = cs.city.trim().equalsIgnoreCase(location.trim());
        return result;
    }

    public String getLocation() {
        return location;
    }


}
