package com.Alertapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.Alertapp.Rule.*;
import static android.R.id.list;

/**
 * Created by kusumasri on 2/5/17.
 */

public class Home extends AppCompatActivity {


    public String name = "", pass = "";
    public Location location=new Location(" ");
    public TextView tvWeatherResult;
    public static final HashMap<Integer,Date> Track_rule=new HashMap<>();
    Boolean isbind=false;
    ListView Rulelist;
    ArrayList<WeatherCondition> weatherist;
    ArrayList<Locationcondition> locationlist;
    ArrayList<Timecondition> timelist;
    ArrayList<Rule> arlist;
    public int count=0;
    public CustomAdapter adapter;
    String cityName="";
    float temperature;
    DataStorage data =new DataStorage(this);
    Locationgps loc=new Locationgps();
    TextView tv_weather,tv_city;
    CurrentState cs=new CurrentState();


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
        tv_city=(TextView)findViewById(R.id.tvcity);

        addRulestoListview();

        registerReceiver(uiUpdated, new IntentFilter("LOCATION_UPDATED"));
    }


    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

           /*  loc.longitude=Float.parseFloat(""+intent.getExtras().getFloat("longitude"));
             loc.latitude =Float.parseFloat(""+intent.getExtras().getFloat("latitude"));
             Geocoder gcd = new Geocoder(getBaseContext(),Locale.getDefault());
             List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

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
            }*/

            temperature=intent.getFloatExtra("temperature",1);
            cityName=intent.getExtras().getString("city");
            tv_weather.setText(Float.toString(temperature));
            tv_city.setText(cityName);

            cs.setCurrentlocation(cityName);
            cs.setCurrentweather(temperature);

            notificationintent =new Intent(context,GenerateNotificationService.class);
            notificationintent.putExtra("location",cityName);
            notificationintent.putExtra("temperature",temperature);
        //    startService(notificationintent);


        }
    };






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