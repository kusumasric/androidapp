package com.example;




import com.Alertapp.CurrentState;
import com.Alertapp.WeatherCondition;



import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;



/**
 * Created by kusumasri on 5/30/17.
 */


public class Weathertest {

    CurrentState cs=new CurrentState(50,"foster");

    @Test
    public void weatherconditionvalidation() {
       WeatherCondition weatherCondition=new WeatherCondition(20,40);
       assertThat(weatherCondition.isConditionSatisfied(cs),is(false));

       WeatherCondition weatherCondition1=new WeatherCondition(40,60);
        assertThat(weatherCondition1.isConditionSatisfied(cs),is(true));

    }

}
