package com.Alertapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kusumasri on 4/28/17.
 */

public class NotificationsAsynctask extends AsyncTask<CurrentState,Void,Void> {

    ArrayList<Locationcondition> array_location=new ArrayList<>();
    ArrayList<WeatherCondition> array_weather=new ArrayList<>();
    ArrayList<Timecondition>  array_datetime=new ArrayList<>();
    DataStorage data;

    Home home;
    Date dt=new Date();
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

        dt=params[0].getDate();

        array_weather=data.getweathercondition();
        array_location=data.getlocationcondition();
        array_datetime=data.gettimecondition();
        Intent[] intents = new Intent[1];
        intents[0] = new Intent(params[0].context,MainActivity.class);

        Date dt1=new Date();

        for(int i=0;i<array_location.size();i++)
        {
            if(array_location.get(i).isConditionSatisfied(params[0]))
            {
                if(home.Track_rule.containsKey(array_location.get(i).rule.getid()))
                {
                    dt1=home.Track_rule.get(array_location.get(i));
                    if(dt1.equals(dt))
                    {

                        if(dt1.getTime()-dt.getTime() > 30*60*1000 )
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(array_location.get(i).rule.getRulename());
                            notification.setContentText(array_location.get(i).rule.getRuledesc());
                            PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)params[0].context. getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(num++, notification.build());


                        }
                        dt1=dt;
                        home.Track_rule.put(array_location.get(i).rule.getid(),dt1);

                    }

                }

                else
                {

                    notification.setTicker(" kusuma");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(array_location.get(i).rule.getRulename());
                    notification.setContentText(array_location.get(i).rule.getRuledesc());
                    PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)params[0].context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(array_location.get(i).rule.getid(),dt);
                }

            }

        }

        for(Timecondition timeCondition: array_datetime)
        {
            if(timeCondition.isConditionSatisfied(params[0]))
            {
                if(home.Track_rule.containsKey(timeCondition.rule.getid()))
                {
                    dt1=home.Track_rule.get(timeCondition);
                    if(dt1.equals(dt))
                    {

                        if(dt.getTime()-dt1.getTime()> 30 *60*1000)
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(timeCondition.rule.getRulename());
                            notification.setContentText(timeCondition.rule.getRuledesc());
                            PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)params[0].context. getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(num++, notification.build());


                        }
                        dt1=dt;
                        home.Track_rule.put(timeCondition.rule.getid(),dt1);

                    }

                }

                else
                {

                    notification.setTicker(" kusuma");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(timeCondition.rule.getRulename());
                    notification.setContentText(timeCondition.rule.getRuledesc());
                    PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)params[0].context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(timeCondition.rule.getid(),dt);
                }

            }

        }


        //for weather conditions
        for(WeatherCondition weatherCondition: array_weather)
        {
            if(weatherCondition.isConditionSatisfied(params[0]))
            {
                if(home.Track_rule.containsKey(weatherCondition.rule.getid()))
                {
                    dt1=home.Track_rule.get(weatherCondition);
                    if(dt1.equals(dt))
                    {

                        if(dt.getTime()-dt1.getTime()> 30 *60*1000)
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(weatherCondition.rule.getRulename());
                            notification.setContentText(weatherCondition.rule.getRuledesc());
                            PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)params[0].context. getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(num++, notification.build());


                        }
                        dt1=dt;
                        home.Track_rule.put(weatherCondition.rule.getid(),dt1);

                    }

                }

                else
                {

                    notification.setTicker(" kusuma");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(weatherCondition.rule.getRulename());
                    notification.setContentText(weatherCondition.rule.getRuledesc());
                    PendingIntent pintent = PendingIntent.getActivities(params[0].context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)params[0].context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(weatherCondition.rule.getid(),dt);
                }

            }

        }



        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {


        super.onProgressUpdate(values);
    }


}
