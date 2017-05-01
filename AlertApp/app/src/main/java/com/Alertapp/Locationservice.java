package com.Alertapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kusumasri on 4/20/17.
 */

public class Locationservice extends Service {
    public Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationasynctask locationtask =new locationasynctask();
        context=getApplicationContext();
        try {
            locationtask.execute(context);
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
