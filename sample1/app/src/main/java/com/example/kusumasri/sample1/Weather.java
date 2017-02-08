package com.example.kusumasri.sample1;

import android.location.Location;
import android.os.AsyncTask;

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

            URL url = new URL("http://api.openweathermap.org/data/2.5/Weather?lat="+String.valueOf(loc[0].getLatitude())+"&lon="+String.valueOf(loc[0].getLongitude())+"&appid=c9191af0478be48aff2225833c22d6c7");
            // URL url=new URL("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=c9191af0478be48aff2225833c22d6c7");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            try {
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                weatherReport=stringBuilder.toString();
            }
            catch (Exception excep)
            {
                excep.printStackTrace();
            }
            finally{
                urlConnection.disconnect();
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
