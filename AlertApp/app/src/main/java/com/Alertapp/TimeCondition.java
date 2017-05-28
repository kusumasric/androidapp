package com.Alertapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kusumasri on 4/24/17.
 */

public class TimeCondition extends Basecondition {

    public Date conditionDate;
    public int getId() {
        return conditionId;
    }

    public TimeCondition(Date date)
    {
        conditionDate =date;
    }

    @Override
    public boolean isConditionSatisfied(CurrentState cs)  {

        long diff = Math.abs( conditionDate.getTime()- cs.getDate().getTime() );
        if(diff < 3 *60*1000)
        {
            return true;
        }
       return false;
    }

    public String getDatetime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
        return dateFormat.format( conditionDate );
    }

 }
