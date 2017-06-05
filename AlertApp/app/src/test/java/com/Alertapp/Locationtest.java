package com.Alertapp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by kusumasri on 5/30/17.
 */

public class Locationtest {

    CurrentState cs=new CurrentState(50,"foster");


    @Test
    public void Locationconditionvalidation()
    {
        LocationCondition locationCondition=new LocationCondition("Foi");
        assertThat(locationCondition.isConditionSatisfied(cs),is(false));

        LocationCondition locationCondition1=new LocationCondition("foster");
        assertThat(locationCondition1.isConditionSatisfied(cs),is(true));

    }

}
