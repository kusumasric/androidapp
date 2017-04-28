package com.Alertapp;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by kusumasri on 2/13/17.
 */

public class Submitlocation extends AsyncTask<Locationgps,Void, String> {

    public String weatherReport="";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Locationgps...loc){

        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates(loc[0].getLatitude(),loc[0].getLongitude());
            // checking if max. temp. and min. temp. is available
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
                // printing the  temperature
                weatherReport = Float.toString(cwd.getMainInstance().getTemperature());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return weatherReport;
    }
    @Override
    protected void onPostExecute(String response) {

    }

    protected void onProgressUpdate()
    {

    }


}



