package com.Alertapp;

import android.content.Context;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class CurrentState {
    String currentweather="";
    String currentlocation="";
    String currentdate="";
    String currenttime="";
    Context context;

    public String getCurrentweather() {
        return currentweather;
    }

    public void setCurrentweather(String currentweather) {
        this.currentweather = currentweather;
    }

    public String getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
    }

    public String getCurrentdate() {

        Calendar c = Calendar.getInstance();
        String strdate=c.get(Calendar.DAY_OF_MONTH)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR);
        this.currentdate = strdate;
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {

    }

    public String getCurrenttime() {
        Calendar c = Calendar.getInstance();
        String strtime=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
        this.currentdate = strtime ;
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {

        this.currenttime = currenttime;
    }
}
