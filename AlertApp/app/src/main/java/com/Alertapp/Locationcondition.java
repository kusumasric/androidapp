package com.Alertapp;

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

        if(cs.currentlocation==location)
            return true;
        else
            return false;
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
