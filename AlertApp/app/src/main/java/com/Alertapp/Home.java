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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.Alertapp.Rule.*;
import static android.R.id.list;

/**
 * Created by kusumasri on 2/5/17.
 */

public class Home extends AppCompatActivity {


    public String name = "", pass = "";
    public Location location=new Location(" ");
    public TextView tvWeatherResult;
    Weather2 weatherobj1=new Weather2();
    Boolean isbind=false;
    ListView Rulelist;
    ArrayList<WeatherCondition> weatherist;
    ArrayList<Locationcondition> locationlist;
    ArrayList<Timecondition> timelist;
    ArrayList<Rule> arlist;
    public int count=0;
    public CustomAdapter adapter;
    DataStorage data =new DataStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (extras != null) {
            name = extras.getString("name");
            pass = extras.getString("pass");
        }
        String hello = "hello" + name;
        TextView text = (TextView) findViewById(R.id.hellou);
        text.setText(hello);
        Intent i=new Intent(this,Weather2.class);
        bindService(i,connectionobj,Context.BIND_AUTO_CREATE);
        addRulestoListview();

    }


    public void addRulestoListview()
    {
        Rulelist=(ListView)findViewById(R.id.listview1);
        arlist=data.getrules();
        weatherist=data.getweathercondition();
        locationlist=data.getlocationcondition();

        if(arlist.size()>0) {
            adapter = new CustomAdapter(getApplicationContext(), arlist);
            Rulelist.setAdapter(adapter);
            Rulelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {

                    Toast.makeText(getApplicationContext(), "Clicked product id" + view.getTag(), Toast.LENGTH_LONG).show();
                    //delete item from database
               //     Rule obj=(Rule)adapter.getItem(position);
                    Rule r1=(Rule)parent.getItemAtPosition(position);
                    data.deleterule(r1.getid());
                    arlist.remove(position);
                    adapter = new CustomAdapter(getApplicationContext(),arlist);
                    Rulelist.setAdapter(adapter);
                    return false;
                }

            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onResume() {
        addRulestoListview();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void gettemperature(View view)
    {
        tvWeatherResult=(TextView)findViewById(R.id.tvweather);
        tvWeatherResult.setText(weatherobj1.weatherReport);
    }

    private ServiceConnection connectionobj=new ServiceConnection()
    {
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


    public void fabClick(View view)
    {
        Intent intent = new Intent(this, Addrule.class);
        startActivityForResult(intent,1);
    }


}