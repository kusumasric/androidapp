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


public class ActivityAddrule extends Activity  {

    private EditText et_RuleName,et_RuleDesc,et_MinimumTemp,et_MaximumTemp;
    private String RuleName,RuleDesc;
    private DataStorage dbhandler=new DataStorage(this);
    private WeatherCondition weatherc=new WeatherCondition();
    private Locationcondition locationc=new Locationcondition();
    private Timecondition timeCondition = new Timecondition();
    public Rule newRule;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
    public String location="";
    public Context context;
    Spinner spinner_conditionmenu;
    Calendar selectedDateTime =Calendar.getInstance();
    DatePicker datepicker;
    TimePicker timepicker;

    //TODO: handle adding rules differently like we discussed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fab_addrule);
        et_RuleName=(EditText)findViewById(R.id.et_rulename);
        context = getApplicationContext();
        spinner_conditionmenu=(Spinner)findViewById(R.id.spinner_menu);
        ArrayAdapter<String> menuadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Conditionmenu));
        final ArrayAdapter<String> locationadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Locationitems));
        menuadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_conditionmenu.setAdapter(menuadapter);
        spinner_conditionmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            LayoutInflater inflater =getLayoutInflater();
            View viewTime, viewLocation, viewWeather;
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutinflate);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                linearLayout.removeAllViews();

                if(position==1)
                {
                    viewTime =inflater.inflate(R.layout.activity_home_fab_datetime_selection,null);
                    linearLayout.addView(viewTime);
                    et_RuleDesc=(EditText) viewTime.findViewById(R.id.et_ruledesc);
                    datepicker=(DatePicker) viewTime.findViewById(R.id.datepicker);
                    timepicker=(TimePicker) viewTime.findViewById(R.id.timepicker);
                    datepicker.setMinDate(System.currentTimeMillis() - 1000);

                }

                if(position==2) {
                    viewLocation = inflater.inflate(R.layout.activity_home_fab_location_selection, linearLayout, false);
                    linearLayout.addView(viewLocation);
                    et_RuleDesc = (EditText) viewLocation.findViewById(R.id.et_ruledesc);
                    final Spinner spinner_location = (Spinner) viewLocation.findViewById(R.id.locationspinner);
                    locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_location.setAdapter(locationadapter);
                    spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Handling only few cities for now. Need to figure out a generic way to do this
                            location = spinner_location.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if(position==3)
                {
                    viewWeather =inflater.inflate(R.layout.activity_home_fab_weather,null);
                    linearLayout.addView(viewWeather);
                    et_RuleDesc=(EditText) viewWeather.findViewById(R.id.et_ruledesc);
                    et_MinimumTemp=(EditText) viewWeather.findViewById(R.id.et_mintemp);
                    et_MaximumTemp=(EditText) viewWeather.findViewById(R.id.et_maxtemp);
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
      //  Rule newRule;
        if(spinner_conditionmenu.getSelectedItemPosition()==1)
        {
            selectedDateTime.set(datepicker.getYear(),datepicker.getMonth(),datepicker.getDayOfMonth(),timepicker.getHour(),timepicker.getMinute());
            Date date = selectedDateTime.getTime();
            timeCondition.datetime=dateFormat.format(date);
            newRule = new Rule(RuleName, RuleDesc, timeCondition);

          /*  newRule=new Rule(RuleName,RuleDesc, timeCondition);
            dbhandler.adddatetime(timeCondition,newRule);*/
        }

        if(spinner_conditionmenu.getSelectedItemPosition()==2)
        {
            locationc.setLocation(location);
            newRule.setBaseconditionobj(locationc);
            /*newRule=new Rule(RuleName,RuleDesc,locationc);
            dbhandler.addlocation(locationc,newRule);*/
        }

        if(spinner_conditionmenu.getSelectedItemPosition()==3)
        {
            int minTemp=Integer.parseInt(et_MinimumTemp.getText().toString());
            int maxTemp=Integer.parseInt(et_MaximumTemp.getText().toString());
            weatherc.setMintemp(minTemp);
            weatherc.setMaxtemp(maxTemp);
            newRule.setBaseconditionobj(weatherc);
           /* newRule=new Rule(RuleName,RuleDesc,weatherc);
            dbhandler.addweather(weatherc,newRule);*/
        }
        dbhandler.addRule(newRule);
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
