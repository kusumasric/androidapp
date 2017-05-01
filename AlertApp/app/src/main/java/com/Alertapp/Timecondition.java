package com.Alertapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Timecondition extends Basecondition {


    String time,date;
    int id;
    int currenthr=0,hrs=0,currentmin=0,min=0 ;
    Date date1=new Date();
    Date currentdate=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");


    boolean result=false;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs)  {

     /*  if(date.trim().equals(cs.getCurrentdate().trim()))
       {
           String[] timesplit=time.split(":");
           String[] currenttime=cs.getCurrenttime().split(":");
           hrs=Integer.parseInt(timesplit[0]);
           min=Integer.parseInt(timesplit[1]);
           currenthr=Integer.parseInt(currenttime[0]);
           currentmin=Integer.parseInt(currenttime[1]);
            if(hrs==currenthr && min==currentmin)
                result=true;
            else
                result=false;
       }*/
        try {
            date1=formatter.parse(date+"-"+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            currentdate=cs.getDate();

        long diff =Math.abs(date1.getTime()-(currentdate).getTime());
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
