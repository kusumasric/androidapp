package com.Alertapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;


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






        currentstate.currentlocation=intent.getStringExtra("location");
        currentstate.currentweather = intent.getFloatExtra("temperature",0);
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
