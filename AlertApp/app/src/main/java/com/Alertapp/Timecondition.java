package com.Alertapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Timecondition extends Basecondition {


    public String time,date;
    public int id;
    public Date date1=new Date();
    public Date currentDate=new Date();
    public SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
    public boolean result=false;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs)  {

        try {
            date1=formatter.parse(date+"-"+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDate=cs.getDate();
        long diff =Math.abs(date1.getTime()-(currentDate).getTime());
        if(diff < 3 *60*1000)
        {
            result=true;
        }
       return result;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void Timecondition()
    {

    }
    public void setruleid(int ruleid)
    {
        rule.id=ruleid;
    }


}
