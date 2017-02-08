package com.example.kusumasri.sample1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Created by kusumasri on 2/5/17.
 */

public class Signup extends AppCompatActivity implements LocationListener {

    public static int count = 0;
    public String name = "", pass = "";
    public Location location=new Location(" ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        count++;
        String filen = extras.getString("Filename");
        FileOutputStream outputStream;

        if (extras != null) {
            name = extras.getString("name");
            pass = extras.getString("pass");
        }
        String str = count + " " + name + " " + pass;
        try {
            outputStream = openFileOutput(filen, Context.MODE_PRIVATE);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hello = "hello" + name;
        TextView text = (TextView) findViewById(R.id.hellou);
        text.setText(hello);

        try {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            int permission =this.checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION);// .checkSelfPermission(this, );
            if ( permission != PackageManager.PERMISSION_GRANTED ) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onLocationChanged(Location loc) {
        location=loc;
        Weather weatherCapture=new Weather();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                weatherCapture.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, location);
            else
                weatherCapture.execute(location);
            weatherCapture.get();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        TextView tvWeatherResult=(TextView)findViewById(R.id.tvweather);
        tvWeatherResult.setText(weatherCapture.weatherReport);
    }


    public void onProviderDisabled(String provider) {}

    public void onProviderEnabled(String provider) {}

    public void onStatusChanged(String provider, int status, Bundle extras) {}


}