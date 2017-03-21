package com.Alertapp;

import android.location.Location;
import android.os.AsyncTask;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kusumasri on 2/6/17.
 */

public class Weather extends AsyncTask<Location,Void,String > {
    public String weatherReport="";
   @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Location...loc){

        try {
            OpenWeatherMap owm = new OpenWeatherMap("c9191af0478be48aff2225833c22d6c7");
            CurrentWeather cwd = owm.currentWeatherByCoordinates((float) loc[0].getLatitude(), (float) loc[0].getLongitude());
            // checking if max. temp. and min. temp. is available
            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {

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

}
