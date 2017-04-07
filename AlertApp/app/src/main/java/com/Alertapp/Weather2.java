package com.Alertapp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by kusumasri on 2/12/17.
 */

public class Weather2 extends Service implements LocationListener{

    public String weatherReport;
    LocationManager locationManager;
    private final IBinder binderobj=new Mybinder();
    public Weather2()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        findlocation();
        return super.onStartCommand(intent,flags,startId);
    }
    public void findlocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            int permission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);// .checkSelfPermission(this, );
            if (permission != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 10, this);
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

        return binderobj;
    }

    public void onLocationChanged(Location loc) {
        Submitlocation subloc=new Submitlocation();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                subloc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loc);
            else
                subloc.execute(loc);
                weatherReport=subloc.get();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void onProviderDisabled(String provider) {}

    public void onProviderEnabled(String provider) {}

    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public class Mybinder extends Binder{
        Weather2 getData()
        {
            return Weather2.this;
        }
    }
}
