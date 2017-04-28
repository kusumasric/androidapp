package com.Alertapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by kusumasri on 4/27/17.
 */

public class GenerateNotificationService extends Service {


    public Context context;




    CurrentState currentstate=new CurrentState();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


     //   registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED"));


        currentstate.currentlocation=intent.getStringExtra("location");
        currentstate.currentdate=intent.getStringExtra("date");
        currentstate.currenttime=intent.getStringExtra("time");
        currentstate.currentweather = intent.getStringExtra("temperature");
        currentstate.context=getApplicationContext();

        NotificationsAsynctask notificationtask =new NotificationsAsynctask();

        try {
            notificationtask.execute(currentstate);
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

        return START_STICKY;

    }









    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
