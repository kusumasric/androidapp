package com.Alertapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kusumasri on 2/5/17.
 */

public class HomePage extends AppCompatActivity {


    public String name = "";
    public static final HashMap<Integer,Date> Track_rule=new HashMap<>();
    //TODO:use full names - here weatherlistview
    public ListView Rulelist,wlist,tlist,llist;
    //TODO: name these as weatherConditions
    public ArrayList<WeatherCondition> weatherlist;
    public ArrayList<Locationcondition> locationlist;
    public ArrayList<Timecondition> timelist;
    public ArrayList<Rule> arlist;
    public CustomAdapter adapter;
    //TODO: variableFormat should be consistent - Use camel case everywhere
    //TODO: write formatting rules somewhere and the whole codebase should be consistent
    public CustomAdapterWeather weather_adapter;
    public CustomAdapterTime time_adapter;
    public CustomAdapterLocation location_adapter;
    public String cityName="";
    public float temperature;
    public DataStorage data =new DataStorage(this);
    public  TextView tv_weather,tv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (extras != null) {
            name = extras.getString("name");
        }
        scheduleAlarm();
        String hello = "hello" + name;
        TextView text = (TextView) findViewById(R.id.hellou);
        text.setText(hello);
        tv_weather=(TextView)findViewById(R.id.tvweather);
        tv_city=(TextView)findViewById(R.id.tvcity);
        addRulestoListview();
        registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED"));
    }



    public void scheduleAlarm() {
        //TODO:Put this after signup/signin is successful
        Intent intent = new Intent(getApplicationContext(), ReceiverStartService.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, ReceiverStartService.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1*60*1000, pIntent);
    }


    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            temperature=intent.getFloatExtra("temperature",1);
            cityName=intent.getExtras().getString("city");
            tv_weather.setText(Float.toString(temperature));
            tv_city.setText(cityName);

        }
    };


    public void addRulestoListview()
    {
        Rulelist=(ListView)findViewById(R.id.listview);
        arlist=data.getrules();


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

        try {
            unregisterReceiver(uiUpdated);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void fabClick(View view)
    {
        Intent intent = new Intent(this, Addrule.class);
        startActivityForResult(intent,1);
    }

}