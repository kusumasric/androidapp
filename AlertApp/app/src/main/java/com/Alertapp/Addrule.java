package com.Alertapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.Alertapp.Home.*;

import java.util.Calendar;

/**
 * Created by kusumasri on 4/1/17.
 */

public class Addrule extends Activity implements AdapterView.OnItemSelectedListener {

    EditText et_rulename,et_ruledesc;
    String rulename,ruledesc;
    DataStorage dbhandler=new DataStorage(this);

    WeatherCondition weatherc=new WeatherCondition();
    Locationcondition locationc=new Locationcondition();
    Timecondition  timec=new Timecondition();
    int year,month,day,hours,minutes;
    EditText et_date,et_time,et_min,et_max;
    Calendar myCalendar;
    String str_date="",str_time="";
    static final int dialognum=0;
    public DatePickerDialog fromDatePickerDialog;
    public TimePickerDialog timepicker;

    int selectedposition=0;
    String mintemp,maxtemp;
    String location="";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.addrule);

         et_rulename=(EditText)findViewById(R.id.et_rulename);
         et_ruledesc=(EditText)findViewById(R.id.et_ruledesc);

        context = getApplicationContext();
        Spinner spinner_menu=(Spinner)findViewById(R.id.spinner_menu);
        ArrayAdapter<String> menuadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Menuitems));
        final ArrayAdapter<String> locationadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Locationitems));
        final ArrayAdapter<String> weatheradapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Weatheritems));
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
                    selectedposition=1;
                    lLayout.removeAllViews();
                    viewtime=inflater.inflate(R.layout.datetime,null);
                    lLayout.addView(viewtime);
                    et_date=(EditText)viewtime.findViewById(R.id.et_date);
                    et_date.setInputType(InputType.TYPE_NULL);
                    et_time=(EditText)viewtime.findViewById(R.id.et_time);
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
                            et_date.setText(str_date);
                            timec.setDate(str_date);
                        }

                    },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    timepicker=new TimePickerDialog(viewtime.getContext(),new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            str_time =hourOfDay+":"+minute;
                            et_time.setText(str_time);
                            timec.setTime(str_time);
                        }
                    },hours,minutes,false);

                    et_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fromDatePickerDialog.show();

                        }
                    });

                    et_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timepicker.show();

                        }
                    });

                    timec.rule.id=0;
                }

                if(position==2)
                {
                    selectedposition=2;
                    lLayout.removeAllViews();
                    viewlocation=inflater.inflate(R.layout.location,lLayout,false);
                    lLayout.addView(viewlocation);
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
                    locationc.rule.id=0;

                }

                if(position==3)
                {
                    selectedposition=3;
                    lLayout.removeAllViews();
                    viewweather=inflater.inflate(R.layout.weathersubmenu,null);
                    lLayout.addView(viewweather);
                    et_min=(EditText)viewweather.findViewById(R.id.et_mintemp);
                    et_max=(EditText)viewweather.findViewById(R.id.et_maxtemp);


                    weatherc.rule.id=0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void onclickadd(View view)
    {
        rulename=et_rulename.getText().toString();
        ruledesc=et_ruledesc.getText().toString();
        Rule rulenew=new Rule(rulename,ruledesc);
        dbhandler.addRule(rulenew);
      /*  Home home1=new Home();
        home1.addRulestoListview();*/


        if(selectedposition==1)
        {
            timec.date=str_date;
            timec.time=str_time;
            timec.rule=rulenew;
            dbhandler.adddatetime(timec,rulename);
        }

        if(selectedposition==2)
        {

            locationc.setLocation(location);
            locationc.rule=rulenew;
            dbhandler.addlocation(locationc,rulename);
        }

        if(selectedposition==3)
        {
            mintemp=et_min.getText().toString();
            maxtemp=et_max.getText().toString();
            int mintemp1=Integer.parseInt(mintemp);
            int maxtemp1=Integer.parseInt(maxtemp);
            weatherc.setMintemp(mintemp1);
            weatherc.setMaxtemp(maxtemp1);
            weatherc.rule=rulenew;
            dbhandler.addweather(weatherc,rulename);
        }

        super.onBackPressed();
        //finish();
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
