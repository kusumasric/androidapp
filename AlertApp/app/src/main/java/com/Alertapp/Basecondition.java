package com.Alertapp;

/**
 * Created by kusumasri on 4/24/17.
 */

public abstract class Basecondition {

   public abstract boolean isConditionSatisfied(CurrentState cs);
   public int conditionId;
   public Rule rule=new Rule();

}
