package com.Alertapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kusumasri on 4/1/17.
 */

// TODO: remove this onitemselected listener
public class ActivityAddrule extends Activity  {

    //TODO: specify modifiers
    public EditText et_RuleName,et_RuleDesc,et_MinimumTemp,et_MaximumTemp,et_Datetime;
    public String RuleName,RuleDesc;
    public DataStorage dbhandler=new DataStorage(this);
    public WeatherCondition weatherc=new WeatherCondition();
    public Locationcondition locationc=new Locationcondition();
    public Timecondition  timec=new Timecondition();
    //TODO: remove unused/commented code
    //-Reply-have done it
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
    Date date=new Date();
    //TODO: see if you can use date instead of string
    // use http://stackoverflow.com/questions/2592499/casting-and-getting-values-from-date-picker-and-time-picker-in-android/14590203#14590203

    public String location="";
    public Context context;
    Spinner spinner_conditionmenu;
    Calendar selectedDateTime =Calendar.getInstance();
    DatePicker datepicker;
    TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fab_addrule);
        et_RuleName=(EditText)findViewById(R.id.et_rulename);
        context = getApplicationContext();
        //TODO: make this spinner class variable and get rid of position use the spinner's getSelectedPosition in onclickadd
        //-Reply have changed it
        //TODO: Menuitems - rename it to something better
        //Reply- Have changed name
        spinner_conditionmenu=(Spinner)findViewById(R.id.spinner_menu);
        ArrayAdapter<String> menuadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Conditionmenu));
        final ArrayAdapter<String> locationadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Locationitems));
        menuadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_conditionmenu.setAdapter(menuadapter);
        spinner_conditionmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            LayoutInflater inflater =getLayoutInflater();
            //TODO: move the below variables inside the selector
            //Reply -its not working
            View viewtime,viewlocation,viewweather;
            LinearLayout lLayout = (LinearLayout)findViewById(R.id.layoutinflate);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0) {

                    lLayout.removeAllViews();
                }
                if(position==1)
                {
                    lLayout.removeAllViews();
                    viewtime=inflater.inflate(R.layout.activity_home_fab_datetime_selection,null);
                    lLayout.addView(viewtime);
                    et_RuleDesc=(EditText)viewtime.findViewById(R.id.et_ruledesc);
                    datepicker=(DatePicker)viewtime.findViewById(R.id.datepicker);
                    timepicker=(TimePicker)viewtime.findViewById(R.id.timepicker);
                    datepicker.setMinDate(System.currentTimeMillis() - 1000);
                    //TODO: Rename the variable calender to something better
                    //reply- has done it

                }

                if(position==2) {

                    lLayout.removeAllViews();
                    viewlocation = inflater.inflate(R.layout.activity_home_fab_location_selection, lLayout, false);
                    lLayout.addView(viewlocation);
                    et_RuleDesc = (EditText) viewlocation.findViewById(R.id.et_ruledesc);
                    Spinner spinner_location = (Spinner) viewlocation.findViewById(R.id.locationspinner);
                    locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_location.setAdapter(locationadapter);
                    spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 1) {
                                location = "Foster City";
                            }
                            if (position == 2) {
                                location = "SF";
                            }
                            if (position == 3) {
                                location = "Santa Clara";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if(position==3)
                {
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
        if(spinner_conditionmenu.getSelectedItemPosition()==1)
        {
            selectedDateTime.set(datepicker.getYear(),datepicker.getMonth(),datepicker.getDayOfMonth(),timepicker.getHour(),timepicker.getMinute());
            date= selectedDateTime.getTime();
            timec.datetime=dateFormat.format(date);
            rulenew=new Rule(RuleName,RuleDesc,timec);
            dbhandler.adddatetime(timec,rulenew);
        }

        if(spinner_conditionmenu.getSelectedItemPosition()==2)
        {
            locationc.setLocation(location);
            rulenew=new Rule(RuleName,RuleDesc,locationc);
            dbhandler.addlocation(locationc,rulenew);
        }

        if(spinner_conditionmenu.getSelectedItemPosition()==3)
        {
            int minTemp=Integer.parseInt(et_MinimumTemp.getText().toString());
            int maxTemp=Integer.parseInt(et_MaximumTemp.getText().toString());
            weatherc.setMintemp(minTemp);
            weatherc.setMaxtemp(maxTemp);
            rulenew=new Rule(RuleName,RuleDesc,weatherc);
            dbhandler.addweather(weatherc,rulenew);
        }

        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        finishActivity(1);
        super.onPause();
    }

    public void onclickcancel(View view)
    {
        finish();
    }

}
