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

    public static final HashMap<Integer,Date> Track_rule=new HashMap<>();

    public ArrayList<Rule> rulesList;
    public RuleAdapter rulesAdapter;
    public TextView tv_weather,tv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scheduleAlarm();
        tv_weather=(TextView)findViewById(R.id.tvweather);
        tv_city=(TextView)findViewById(R.id.tvcity);
        initializeRulesView();
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

            float temperature=receiverIntent.getFloatExtra("temperature",1);
            String cityName=receiverIntent.getExtras().getString("city");
            tv_weather.setText(Float.toString(temperature)+"\u2109");
            tv_city.setText(cityName);

        }
    };

    public void initializeRulesView()
    {
        final ListView ruleListView =(ListView)findViewById(R.id.listview);
        final DataStorage dataStore =new DataStorage(this);
        rulesList = dataStore.getrules();
        if(rulesList.size()>0) {
            rulesAdapter = new RuleAdapter(getApplicationContext(), rulesList);
            ruleListView.setAdapter(rulesAdapter);
            ruleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {

                    Toast.makeText(getApplicationContext(), "Deleting rule id" + view.getTag(), Toast.LENGTH_LONG).show();
                    Rule rule=(Rule)parent.getAdapter().getItem(position);
                    dataStore.deleterule(rule);
                    rulesList.remove(position);
                    //TODO: do we need to have below two lines as well ? Can we do it smarter ?
                    rulesAdapter = new RuleAdapter(getApplicationContext(), rulesList);
                    ruleListView.setAdapter(rulesAdapter);
                    return false;
                }

            });

        }
    }

    @Override
    protected void onResume() {
        initializeRulesView();
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