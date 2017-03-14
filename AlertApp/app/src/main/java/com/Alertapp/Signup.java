package com.Alertapp;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.kusumasri.Alertapp.R;

/**
 * Created by kusumasri on 2/5/17.
 */

public class Signup extends AppCompatActivity {


    public String name = "", pass = "";
    public Location location=new Location(" ");
    public TextView tvWeatherResult;
    Weather2 weatherobj1=new Weather2();
    Boolean isbind=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (extras != null) {
            name = extras.getString("name");
            pass = extras.getString("pass");
        }

        String hello = "hello" + name;
        TextView text = (TextView) findViewById(R.id.hellou);
        text.setText(hello);
        Intent i=new Intent(this,Weather2.class);
        bindService(i,connectionobj,Context.BIND_AUTO_CREATE);
    }
    public void gettemperature(View view)
    {
        tvWeatherResult=(TextView)findViewById(R.id.tvweather);
        tvWeatherResult.setText(weatherobj1.weatherReport);
    }

    private ServiceConnection connectionobj=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Weather2.Mybinder bind=(Weather2.Mybinder)service;
            weatherobj1=bind.getData();
            isbind=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isbind=false;
        }
    };

}