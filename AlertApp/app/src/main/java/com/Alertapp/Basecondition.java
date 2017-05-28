package com.Alertapp;

import java.text.ParseException;

/**
 * Created by kusumasri on 4/24/17.
 */

public abstract class Basecondition {

   public abstract boolean isConditionSatisfied(CurrentState cs);
   //Will be helpful when we have edit feature in UI
   public int conditionId;
}
