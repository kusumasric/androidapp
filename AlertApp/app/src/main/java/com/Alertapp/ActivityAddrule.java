package com.Alertapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by kusumasri on 4/1/17.
 */

public class ActivityAddrule extends Activity implements AdapterView.OnItemSelectedListener {

    //TODO: specify modifiers
    public EditText et_RuleName,et_RuleDesc,et_Date,et_Time,et_MinimumTemp,et_MaximumTemp;;
    public String RuleName,RuleDesc;
    public DataStorage dbhandler=new DataStorage(this);
    public WeatherCondition weatherc=new WeatherCondition();
    public Locationcondition locationc=new Locationcondition();
    public Timecondition  timec=new Timecondition();
    int year,month,day,hours,minutes;
  //  Date date,time;
  //  public SimpleDateFormat formatterdate = new SimpleDateFormat("dd-MM-yyyy");
  //  public SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
    String str_date="",str_time="";
    public DatePickerDialog fromDatePickerDialog;
    public TimePickerDialog timePicker;
    public int selectedPosition=0;
    public String minTemp,maxTemp;
    public String location="";
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fab_addrule);
        et_RuleName=(EditText)findViewById(R.id.et_rulename);

        context = getApplicationContext();
        Spinner spinner_menu=(Spinner)findViewById(R.id.spinner_menu);
        ArrayAdapter<String> menuadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Menuitems));
        final ArrayAdapter<String> locationadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Locationitems));
        menuadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_menu.setAdapter(menuadapter);
        spinner_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            LayoutInflater inflater =getLayoutInflater();
            View viewtime,viewlocation,viewweather;
            LinearLayout lLayout = (LinearLayout)findViewById(R.id.layoutinflate);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)                {

                    lLayout.removeAllViews();
                }
                if(position==1)
                {
                    selectedPosition=1;
                    lLayout.removeAllViews();
                    viewtime=inflater.inflate(R.layout.activity_home_fab_datetime_selection,null);
                    lLayout.addView(viewtime);
                    et_Date=(EditText)viewtime.findViewById(R.id.et_Date);
                    et_Date.setInputType(InputType.TYPE_NULL);
                    et_Time=(EditText)viewtime.findViewById(R.id.et_Time);
                    et_RuleDesc=(EditText)viewtime.findViewById(R.id.et_ruledesc);
                    final Calendar c=Calendar.getInstance();
                    c.setTimeInMillis(System.currentTimeMillis() - 1000);
                    year=c.get(Calendar.YEAR);
                    month=c.get(Calendar.MONTH);
                    day=c.get(Calendar.DAY_OF_MONTH);
                    hours=c.get(Calendar.HOUR_OF_DAY);
                    minutes=c.get(Calendar.MINUTE);

                    fromDatePickerDialog = new DatePickerDialog(viewtime.getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            str_date=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;

                            et_Date.setText(str_date);
                            timec.setDate(str_date);
                        }

                    },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    timePicker=new TimePickerDialog(viewtime.getContext(),new TimePickerDialog.OnTimeSetListener(){

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            str_time =hourOfDay+":"+minute;
                            et_Time.setText(str_time);
                            timec.setTime(str_time);
                        }
                    },hours,minutes,false);

                    et_Date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fromDatePickerDialog.show();

                        }
                    });

                    et_Time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timePicker.show();

                        }
                    });

                }

                if(position==2)
                {
                    selectedPosition=2;
                    lLayout.removeAllViews();
                    viewlocation=inflater.inflate(R.layout.activity_home_fab_location_selection,lLayout,false);
                    lLayout.addView(viewlocation);
                    et_RuleDesc=(EditText)viewlocation.findViewById(R.id.et_ruledesc);
                    Spinner spinner_location=(Spinner)viewlocation.findViewById(R.id.locationspinner);
                    locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_location.setAdapter(locationadapter);
                    spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==1)
                            {
                                location="Foster City";
                            }
                            if(position==2)
                            {
                                location="SF";
                            }
                            if(position==3)
                            {
                                location="Santa Clara";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                                  }

                if(position==3)
                {
                    selectedPosition=3;
                    lLayout.removeAllViews();
                    viewweather=inflater.inflate(R.layout.activity_home_fab_weather,null);
                    lLayout.addView(viewweather);
                    et_RuleDesc=(EditText)viewweather.findViewById(R.id.et_ruledesc);
                    et_MinimumTemp=(EditText)viewweather.findViewById(R.id.et_mintemp);
                    et_MaximumTemp=(EditText)viewweather.findViewById(R.id.et_maxtemp);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void onclickadd(View view)
    {
        RuleName=et_RuleName.getText().toString();
        RuleDesc=et_RuleDesc.getText().toString();
        Rule rulenew;
        if(selectedPosition==1)
        {
            timec.date=str_date;
            timec.time=str_time;
            rulenew=new Rule(RuleName,RuleDesc,timec);
            dbhandler.adddatetime(timec,rulenew);
        }

        if(selectedPosition==2)
        {
            locationc.setLocation(location);
            rulenew=new Rule(RuleName,RuleDesc,locationc);
            dbhandler.addlocation(locationc,rulenew);
        }

        if(selectedPosition==3)
        {
            minTemp=et_MinimumTemp.getText().toString();
            maxTemp=et_MaximumTemp.getText().toString();
            maxTemp=et_MaximumTemp.getText().toString();
            int mintemp1=Integer.parseInt(minTemp);
            int maxtemp1=Integer.parseInt(maxTemp);
            weatherc.setMintemp(mintemp1);
            weatherc.setMaxtemp(maxtemp1);
            rulenew=new Rule(RuleName,RuleDesc,weatherc);
            dbhandler.addweather(weatherc,rulenew);
        }

        super.onBackPressed();
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

        super.onResume();
    }

    @Override
    protected void onPause() {

        finishActivity(1);
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

    public void onclickcancel(View view)
    {
            finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
