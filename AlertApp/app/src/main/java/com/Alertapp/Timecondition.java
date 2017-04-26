package com.Alertapp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Timecondition extends Basecondition {


    String time,date;
     int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs) {
      /*  if(calendar.getTime()==cs.cal.getTime() && calendar.get(Calendar.MONTH)==cs.cal.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH)==cs.cal.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.YEAR)==cs.cal.get(Calendar.YEAR) )
            return true;
        else*/
            return false;
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


}
