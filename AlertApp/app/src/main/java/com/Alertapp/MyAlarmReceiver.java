package com.Alertapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kusumasri on 4/22/17.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationintent ;

        Intent i = new Intent(context, Locationservice.class);
        context.startService(i);
      //  notificationintent =new Intent(context,GenerateNotificationService.class);
      //  context.startService(notificationintent);

    }
}
