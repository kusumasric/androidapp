package com.Alertapp;

import android.provider.Settings;
import android.util.Log;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Locationcondition extends Basecondition {

    String location="";
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
        Log.d(cs.currentlocation.trim() + " " + location.trim(),cs.currentlocation.trim() + " " + location.trim());
        boolean result = cs.currentlocation.trim().equalsIgnoreCase(location.trim());
        return result;
        /*if(cs.currentlocation.trim().equalsIgnoreCase(location.trim()) == true)
            return true;
        return false;*/
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

    public void setruleid(int ruleid)
    {
        rule.id=ruleid;
    }

}
