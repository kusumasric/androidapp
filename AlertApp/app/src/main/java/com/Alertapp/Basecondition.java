package com.Alertapp;

import java.text.ParseException;

/**
 * Created by kusumasri on 4/24/17.
 */

public abstract class Basecondition {

   public abstract boolean isConditionSatisfied(CurrentState cs) throws ParseException;
   //TODO: Add a comment
   public int conditionId;

   public  Basecondition()
   {

   }


}
