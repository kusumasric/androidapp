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

public class ActivityHome extends AppCompatActivity {


    // TODO: dont have to initialize to blank . Its blank by default in java
    //Result- done
    public String name;
    public static final HashMap<Integer,Date> Track_rule=new HashMap<>();
    //TODO:use full names - here weatherlistview
    //Result-deleted it
    public ListView Rulelist;
    //TODO: name these as weatherConditions
   //Result-done
    public ArrayList<Rule> listOfRules;
    public CustomAdapter adapter;
    //TODO: write formatting rules somewhere and the whole codebase should be consistent
    //Result-done
    public String cityName="";
    public float temperature;
    public DataStorage data =new DataStorage(this);
    public  TextView tv_weather,tv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // TODO: extras should never be null so dont add the if condition
        //Result -no extras
        scheduleAlarm();
        // TODO: textview variable should be prefixed with "tv_".
        // TODO: no need to create another variable hello here. Use it directly in setText
        //Result-done above
        tv_weather=(TextView)findViewById(R.id.tvweather);
        tv_city=(TextView)findViewById(R.id.tvcity);
        addRulestoListview();
        registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED"));

    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), ReceiverStartService.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, ReceiverStartService.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1*60*1000, pIntent);
    }

    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        // TODO: name the intent variable better
        //Result-done
        @Override
        public void onReceive(Context context, Intent receiverIntent) {

            temperature=receiverIntent.getFloatExtra("temperature",1);
            cityName=receiverIntent.getExtras().getString("city");
            tv_weather.setText(Float.toString(temperature)+"\u2109");
            tv_city.setText(cityName);

        }
    };

    public void addRulestoListview()
    {
        Rulelist=(ListView)findViewById(R.id.listview);
        listOfRules=data.getrules();
        // TODO: rename arrlist to something better
        //Result-done
        if(listOfRules.size()>0) {
            adapter = new CustomAdapter(getApplicationContext(), listOfRules);
            Rulelist.setAdapter(adapter);
            Rulelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {

                    Toast.makeText(getApplicationContext(), "Clicked product id" + view.getTag(), Toast.LENGTH_LONG).show();
                    Rule rule=(Rule)parent.getAdapter().getItem(position);
                    data.deleterule(rule);
                    listOfRules.remove(position);
                    // TODO: Why create adaptor again and also why setAdapter again ?
                    adapter = new CustomAdapter(getApplicationContext(),listOfRules);
                    Rulelist.setAdapter(adapter);
                    return false;
                }

            });

        }
    }

    // TODO: Remove default implementations
    //Result-done

    @Override
    protected void onResume() {
        addRulestoListview();
        super.onResume();
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

    public void fabClick(View view)
    {
        Intent intent = new Intent(this, ActivityAddrule.class);
        startActivityForResult(intent,1);
    }

}