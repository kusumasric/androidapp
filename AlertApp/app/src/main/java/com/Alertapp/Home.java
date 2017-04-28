package com.Alertapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.Alertapp.Rule.*;
import static android.R.id.list;

/**
 * Created by kusumasri on 2/5/17.
 */

public class Home extends AppCompatActivity {


    public String name = "", pass = "";
    public Location location=new Location(" ");
    public TextView tvWeatherResult;

    Boolean isbind=false;
    ListView Rulelist;
    ArrayList<WeatherCondition> weatherist;
    ArrayList<Locationcondition> locationlist;
    ArrayList<Timecondition> timelist;
    ArrayList<Rule> arlist;
    public int count=0;
    public CustomAdapter adapter;
    String temperature="";
    DataStorage data =new DataStorage(this);
    Locationgps loc=new Locationgps();
    TextView tv_weather,tv_location;
    CurrentState cs=new CurrentState();
    Calendar c = Calendar.getInstance();
    String strtime,strdate;
    Intent notificationintent ;

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
        tv_weather=(TextView)findViewById(R.id.tvweather);
        tv_location=(TextView)findViewById(R.id.tvlocation);

        addRulestoListview();
        scheduleAlarm();
        strtime=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
        strdate=c.get(Calendar.DAY_OF_MONTH)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR);
        registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED"));

    }


    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

             loc.longitude=Float.parseFloat(""+intent.getExtras().getFloat("longitude"));
             loc.latitude =Float.parseFloat(""+intent.getExtras().getFloat("latitude"));
             loc.city=""+intent.getExtras().get("city");
             Submitlocation subloc=new Submitlocation();
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    subloc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loc);
                else
                    subloc.execute(loc);
                temperature=subloc.get();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            tv_weather.setText(temperature);
            tv_location.setText(loc.city);
            cs.setCurrentlocation(loc.city);
            cs.setCurrentweather(temperature);
            cs.setCurrentdate(strdate);
            cs.setCurrenttime(strtime);
            notificationintent =new Intent(context,GenerateNotificationService.class);
            notificationintent.putExtra("location",loc.city);
            notificationintent.putExtra("date",strdate);
            notificationintent.putExtra("time",strtime);
            notificationintent.putExtra("temperature",temperature);
            startService(notificationintent);

        }
    };



    public void scheduleAlarm() {

        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);

        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1*60*1000, pIntent);

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



    public void fabClick(View view)
    {
        Intent intent = new Intent(this, Addrule.class);
        startActivityForResult(intent,1);
    }


}