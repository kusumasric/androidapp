package com.example.kusumasri.backgroundservice;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
import java.lang.String;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by kusumasri on 2/12/17.
 */

public class Weather2 extends Service implements LocationListener{

    public String weatherReport;
    public Location location=new Location(" ");

    public Weather2()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Runnable r=new Runnable() {
            @Override
            public void run() {

                findlocation();
            }
        };

        Thread thread1=new Thread();
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }
    public void findlocation() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            int permission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);// .checkSelfPermission(this, );
            if (permission != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location loc) {
        Location location = loc;
        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates((float) location.getLatitude(), (float) location.getLongitude());
            // checking if max. temp. and min. temp. is available
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
                // printing the max./min. temperature
                String weatherReport = Float.toString(cwd.getMainInstance().getTemperature());
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
