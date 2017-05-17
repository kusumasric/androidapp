package com.Alertapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class Timecondition extends Basecondition {


    public String datetime;
    public Date date1=new Date();
    public Date currentDate=new Date();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
    public boolean result=false;
    public int getId() {
        return conditionId;
    }
    public void setId(int id) {
        this.conditionId = id;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs)  {

        try {
            date1=dateFormat.parse(datetime);
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Timecondition()
    {


    }

}
