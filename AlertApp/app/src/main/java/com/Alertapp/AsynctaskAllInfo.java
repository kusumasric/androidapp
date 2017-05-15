package com.Alertapp;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kusumasri on 4/20/17.
 */

public class AsynctaskAllInfo extends AsyncTask<Context,LocationGps,LocationGps> implements LocationListener {

    public LocationManager locationManager;

    public LocationGps locationGps;
    public Context context;
    public String cityName="";
    public float float_latitude,float_longitude;
    public List<Address> listAddresses = null;

    //Notification parameters
    public ArrayList<Rule>  arrayallrules=new ArrayList<>();
    public DataStorage data;
    public ActivityHome home;
    public Date currentDate=new Date();
    public LocationGps loc=new LocationGps();
    public CurrentState presentState=new CurrentState();
    public Calendar c = Calendar.getInstance();
    public float temp1=0;
    public Date databaseDate;
    public NotificationCompat.Builder notification;
    private static int num = 1237;


    @Override
    protected void onPreExecute()
    {


    }

    @Override
    protected LocationGps doInBackground(Context...cont) {
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        context=cont[0];

        findlocation();
        locationGps= new LocationGps(float_longitude,float_latitude);


        Geocoder geo = new Geocoder(context);

        try {
            if (locationGps.getLatitude()!=0 && locationGps.getLongitude()!=0) {
                listAddresses = geo.getFromLocation(locationGps.getLatitude(),
                        locationGps.getLongitude(),1);
                cityName=listAddresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //code to get weather conditions

        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates(float_latitude,float_longitude);
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
        presentState.setCurrentlocation(cityName);
        presentState.setCurrentweather(temp1);

        notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notification.setColor(color);
            notification.setSmallIcon(R.drawable.messages);


        } else {
            notification.setSmallIcon(R.drawable.sjsulogo);
        }

        currentDate=presentState.getDate();
        arrayallrules=data.getrules();
        Intent[] intents = new Intent[1];
        intents[0] = new Intent(context,ActivityMain.class);

        //Rules
        for(int i=0;i<arrayallrules.size();i++)
        {
            try {
                if(arrayallrules.get(i).baseconditionobj.isConditionSatisfied(presentState))
                {
                    if(home.Track_rule.containsKey(arrayallrules.get(i).getid()))
                    {
                        databaseDate=home.Track_rule.get(arrayallrules.get(i).getid());
                        if(databaseDate.equals(currentDate))
                        {

                            if(databaseDate.getTime()-currentDate.getTime() > 30*60*1000 )
                            {
                                notification.setTicker(" kusuma");
                                notification.setWhen(System.currentTimeMillis());
                                notification.setContentTitle(arrayallrules.get(i).getRulename());
                                notification.setContentText(arrayallrules.get(i).getRuledesc());
                                PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                                notification.setContentIntent(pintent);
                                NotificationManager nm = (NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);
                                nm.notify(num++, notification.build());
                            }

                            databaseDate=currentDate;
                            home.Track_rule.put(arrayallrules.get(i).getid(),databaseDate);

                        }

                    }

                    else
                    {

                        notification.setTicker(" kusuma");
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle(arrayallrules.get(i).getRulename());
                        notification.setContentText(arrayallrules.get(i).getRuledesc());
                        PendingIntent pintent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pintent);
                        NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(num++, notification.build());
                        home.Track_rule.put(arrayallrules.get(i).getid(),currentDate);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        //End

        return locationGps;
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

            float_longitude=(float)loc.getLongitude();
            float_latitude=(float)loc.getLatitude();
            locationGps=new LocationGps(float_longitude,float_latitude);

        } catch (Exception e) {
            Toast toast = Toast.makeText(context,"please on the GPS the application stops working",Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location loc) {

      //  Intent broadcastintent=new Intent("com.example.activity_home_fab_location_selection");
        float_longitude=(float)loc.getLongitude();
        float_latitude=(float)loc.getLatitude();
        locationGps=new LocationGps(float_longitude,float_latitude);
        // Looper.myLooper().quit();
    }

    @Override
    protected void onProgressUpdate(LocationGps...loca) {


    }

    @Override
    protected void onPostExecute(LocationGps locationGps) {
        //These 3 lines are to test continous values
        String str="longitude"+ locationGps.getLongitude()+" "+"latitude"+ locationGps.getLatitude()+" "+"city"+cityName ;
        Toast toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.show();
        Intent i = new Intent("LOCATION_UPDATED");
        i.putExtra("city",cityName);
        i.putExtra("temperature",temp1);
        context.sendBroadcast(i);
        super.onPostExecute(locationGps);
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
