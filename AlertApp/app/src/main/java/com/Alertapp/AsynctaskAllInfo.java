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
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kusumasri on 4/20/17.
 */


public class AsynctaskAllInfo extends AsyncTask<Context,LocationGps,Void> implements LocationListener {

    private LocationGps locationGps;
    private Context context;
    private List<Address> listAddresses;

    @Override
    protected Void doInBackground(Context...cont) {
        CurrentState currentState;
        ArrayList< Rule > allRules;
        String cityName = null;
        float currentTemp = 0;
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        context=cont[0];

        findlocation();
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
            CurrentWeather cwd = owm.currentWeatherByCoordinates(locationGps.getLatitude(),locationGps.getLongitude());
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
                currentTemp = cwd.getMainInstance().getTemperature();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        DataStorage data=new DataStorage(context);
        currentState=new CurrentState(currentTemp,cityName);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        notification.setColor(0x008000);
        notification.setSmallIcon(R.drawable.messages);

        allRules=data.getAllRules();
        Intent[] intents = new Intent[1];
        intents[0] = new Intent(context,ActivityLogin.class);

        //check if any of rules pass. If yes display notification
        for(int i=0;i<allRules.size();i++)
        {
            try {
                Rule currentRule  =allRules.get(i);

                // If a notification is fired already in the last 30 mins dont fire it again. Tracking this via Track_rule hashmap
                boolean isNotificationFiredRecently = ( ActivityHome.Track_rule.containsKey(currentRule.getid()) &&
                        currentState.getDate().getTime() - ActivityHome.Track_rule.get(currentRule.getid()).getTime() > 30*60*1000 );

                if(!isNotificationFiredRecently && currentRule.baseconditionobj.isConditionSatisfied(currentState)) {
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(currentRule.getRulename());
                    notification.setContentText(currentRule.getRuledesc());
                    PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pendingIntent);
                    NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(currentRule.getid(), notification.build());
                    ActivityHome.Track_rule.put(currentRule.getid(), currentState.getDate());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String toastMessage="longitude"+ locationGps.getLongitude()+" "+"latitude"+ locationGps.getLatitude()+" "+"city"+ currentState.getCity() ;
        Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("LOCATION_UPDATED");
        intent.putExtra("city", currentState.getCity());
        intent.putExtra("temperature", currentState.getTemperature());

        context.sendBroadcast(intent);
        return null;
    }


    public void findlocation() {

        try {
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            if ( context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
            {
                throw new Exception();
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 10, this);
            Location loc= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            onLocationChanged(loc);

        } catch (Exception e) {
            Toast.makeText(context,"please turn on the location for the application to function properly",Toast.LENGTH_LONG)
                .show();
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location loc) {
        locationGps=new LocationGps((float)loc.getLongitude(),(float)loc.getLatitude());
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
