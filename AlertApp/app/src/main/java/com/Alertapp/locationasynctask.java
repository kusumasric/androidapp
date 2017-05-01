package com.Alertapp;

import android.Manifest;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kusumasri on 4/20/17.
 */

public class locationasynctask extends AsyncTask<Context,Locationgps,Locationgps> implements LocationListener {

    public LocationManager locationManager;
    public String weatherReport;
    public Locationgps loc_gps;
    public Context context;
    Submitlocation subloc=new Submitlocation();

    public float ser_latitude,ser_longitude;


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
        Intent i = new Intent("LOCATION_UPDATED");
        i.putExtra("longitude",loc_gps.getLongitude());
        i.putExtra("latitude",loc_gps.getLatitude());
        context.sendBroadcast(i);
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
        String str="longitude"+locationgps.getLongitude()+" "+"latitude"+locationgps.getLatitude();
        Toast toast = Toast.makeText(context,str,Toast.LENGTH_LONG);
        toast.show();

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
