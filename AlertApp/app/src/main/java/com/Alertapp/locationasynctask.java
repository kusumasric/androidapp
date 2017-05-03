package com.Alertapp;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kusumasri on 4/20/17.
 */

public class locationasynctask extends AsyncTask<Context,Locationgps,Locationgps> implements LocationListener {

    public LocationManager locationManager;
    public String weatherReport;
    public Locationgps loc_gps;
    public Context context;
    String cityName="";

    Submitlocation subloc=new Submitlocation();

    public float ser_latitude,ser_longitude;
    public List<Address> listAddresses = null;
    Intent notificationintent ;

    //Notification parameters
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
    float temp1=0;

    Date dt1;


    NotificationCompat.Builder notification;
    private static int num = 1237;


    @Override
    protected void onPreExecute()
    {


    }

    @Override
    protected Locationgps doInBackground(Context...cont) {
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        context=cont[0];

        findlocation();
        loc_gps= new Locationgps(ser_longitude,ser_latitude);


        Geocoder geo = new Geocoder(context);

        try {
            if (loc_gps.getLatitude()!=0 && loc_gps.getLongitude()!=0) {
                listAddresses = geo.getFromLocation(loc_gps.getLatitude(),
                        loc_gps.getLongitude(),1);
                cityName=listAddresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //code to get weather conditions

        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates(ser_latitude,ser_longitude);
            // checking if max. temp. and min. temp. is available
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
                // printing the  temperature
                temp1 = cwd.getMainInstance().getTemperature();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }



        //Notification code

        data=new DataStorage(context);
        cs.setCurrentlocation(cityName);
        cs.setCurrentweather(temp1);

        notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notification.setColor(color);
            notification.setSmallIcon(R.drawable.images);


        } else {
            notification.setSmallIcon(R.drawable.sjsulogo);
        }

        dt=cs.getDate();

        array_weather=data.getweathercondition();
        array_location=data.getlocationcondition();
        array_datetime=data.gettimecondition();
        Intent[] intents = new Intent[1];
        intents[0] = new Intent(context,MainActivity.class);


        for(int i=0;i<array_location.size();i++)
        {
            if(array_location.get(i).isConditionSatisfied(cs))
            {
                if(home.Track_rule.containsKey(array_location.get(i).rule.getid()))
                {
                    dt1=home.Track_rule.get(array_location.get(i).rule.getid());
                    if(dt1.equals(dt))
                    {

                        if(dt1.getTime()-dt.getTime() > 30*60*1000 )
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(array_location.get(i).rule.getRulename());
                            notification.setContentText(array_location.get(i).rule.getRuledesc());
                            PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);
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
                    PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(array_location.get(i).rule.getid(),dt);
                }

            }

        }

        for(Timecondition timeCondition: array_datetime)
        {
            if(timeCondition.isConditionSatisfied(cs))
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
                            PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);
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
                    PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(timeCondition.rule.getid(),dt);
                }

            }

        }


        //for weather conditions
        for(WeatherCondition weatherCondition: array_weather)
        {
            if(weatherCondition.isConditionSatisfied(cs))
            {
                if(home.Track_rule.containsKey(weatherCondition.rule.getid()))
                {
                    dt1=home.Track_rule.get(weatherCondition.rule.getid());
                    if(dt1.equals(dt))
                    {

                        if(dt.getTime()-dt1.getTime()> 30 *60*1000)
                        {
                            notification.setTicker(" kusuma");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(weatherCondition.rule.getRulename());
                            notification.setContentText(weatherCondition.rule.getRuledesc());
                            PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pintent);
                            NotificationManager nm = (NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);
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
                    PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pintent);
                    NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(num++, notification.build());
                    home.Track_rule.put(weatherCondition.rule.getid(),dt);
                }

            }

        }







        //end
        return loc_gps;
    }


    public void findlocation() {

        try {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            int permission = 0;// .checkSelfPermission(this, );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permission = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permission != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 10, this);
            Location loc= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            ser_longitude=(float)loc.getLongitude();
            ser_latitude=(float)loc.getLatitude();
            loc_gps=new Locationgps(ser_longitude,ser_latitude);





        } catch (Exception e) {
            Toast toast = Toast.makeText(context,"please on the GPS the application stops working",Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location loc) {

        Intent broadcastintent=new Intent("com.example.location");
        ser_longitude=(float)loc.getLongitude();
        ser_latitude=(float)loc.getLatitude();
        loc_gps=new Locationgps(ser_longitude,ser_latitude);


        // Looper.myLooper().quit();



    }
    @Override
    protected void onProgressUpdate(Locationgps...loca) {


    }

    @Override
    protected void onPostExecute(Locationgps locationgps) {
        //These 3 lines are to test continous values
        String str="longitude"+locationgps.getLongitude()+" "+"latitude"+locationgps.getLatitude()+" "+"city"+cityName ;
        Toast toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.show();
        Intent i = new Intent("LOCATION_UPDATED");
        i.putExtra("city",cityName);
        i.putExtra("temperature",temp1);
        context.sendBroadcast(i);
        super.onPostExecute(locationgps);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
