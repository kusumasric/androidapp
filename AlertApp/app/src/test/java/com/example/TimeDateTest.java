package com.example;

import com.Alertapp.CurrentState;
import com.Alertapp.LocationCondition;
import com.Alertapp.TimeCondition;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by kusumasri on 5/30/17.
 */

public class TimeDateTest {

    CurrentState cs=new CurrentState(50,"foster");


    @Test
    public void Timeconditionvalidation()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(2013,12,23,12,45);
        Date date=new Date();
        Date date1=cal.getTime();
        TimeCondition timeCondition=new TimeCondition(date);
        TimeCondition timeCondition1=new TimeCondition(date1);
        assertThat(timeCondition.isConditionSatisfied(cs),is(true));
        assertThat(timeCondition1.isConditionSatisfied(cs),is(false));

    }

}
