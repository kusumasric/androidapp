package com.example.kusumasri.backgroundservice;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by kusumasri on 2/6/17.
 */

public class Weather extends IntentService implements LocationListener {
    public String weatherReport="";
    public Location location=new Location(" ");
    public Weather()
    {
        super("Weather");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {// Let it continue running until it is stopped.
        /*try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            int permission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);// .checkSelfPermission(this, );
            if (permission != PackageManager.PERMISSION_GRANTED) {
                return ;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void onLocationChanged(Location loc) {
        location=loc;
        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates((float) location.getLatitude(), (float) location.getLongitude());
            // checking if max. temp. and min. temp. is available
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
                // printing the max./min. temperature
                weatherReport = Float.toString(cwd.getMainInstance().getTemperature());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    public void onProviderDisabled(String provider) {}

    public void onProviderEnabled(String provider) {}

    public void onStatusChanged(String provider, int status, Bundle extras) {}

}
