package com.Alertapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kusumasri on 4/28/17.
 */

public class NotificationsAsynctask extends AsyncTask< CurrentState,Void,Void> {

    ArrayList<Locationcondition> array_location=new ArrayList<>();
    ArrayList<WeatherCondition> array_weather=new ArrayList<>();
    ArrayList<Timecondition>  array_datetime=new ArrayList<>();
    DataStorage data;

    DateandTime dt=new DateandTime();
    String[] currenttime;
    String[] notificationtime;

    Locationgps loc=new Locationgps();

    CurrentState cs=new CurrentState();
    Calendar c = Calendar.getInstance();
    String strtime,strdate;
    String temperature;

    NotificationCompat.Builder notification;
    private static int num = 1237;


    @Override
    protected Void doInBackground(CurrentState... params) {


        data=new DataStorage(params[0].context);

        notification = new NotificationCompat.Builder(params[0].context);
        notification.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notification.setColor(color);
            notification.setSmallIcon(R.drawable.images);


        } else {
            notification.setSmallIcon(R.drawable.sjsulogo);
        }

        dt.date=params[0].currentdate;
        dt.time=params[0].currenttime;
        array_weather=data.getweathercondition();
        array_location=data.getlocationcondition();
        array_datetime=data.gettimecondition();
        HashMap<Integer,DateandTime>  Track_rule=new HashMap<>();

        for(int i=0;i<array_location.size();i++)
        {
            if(array_location.get(i).isConditionSatisfied(params[0]))
            {
                if(Track_rule.containsKey(array_location.get(i).rule.getid()))
                {
                    DateandTime dt1=new DateandTime();
                    dt1=Track_rule.get(array_location.get(i));
                    if(dt1.date.equals(dt.date))
                    {
                        currenttime=dt.time.split(":");
                        notificationtime=dt1.time.split(":");
                        if(Integer.parseInt(currenttime[0])-Integer.parseInt(notificationtime[0])>3)
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(array_location.get(i).rule.getRulename());
                            notification.setContentText(array_location.get(i).rule.getRuledesc());
                            Intent[] intents = new Intent[1];

                            PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)params[0].context. getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(num++, notification.build());


                        }
                        dt1.time=dt.time;
                        Track_rule.put(array_location.get(i).rule.getid(),dt1);

                    }

                }

                else
                {

                    notification.setTicker(" kusuma");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(array_location.get(i).rule.getRulename());
                    notification.setContentText(array_location.get(i).rule.getRuledesc());
                    Intent[] intents = new Intent[1];
                    PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)params[0].context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    Track_rule.put(array_location.get(i).rule.getid(),dt);
                }

            }

        }






        return null;
    }
}
