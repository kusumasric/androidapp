package com.Alertapp;

/**
 * Created by kusumasri on 4/20/17.
 */

public class Locationgps {

    float latitude;
    float longitude;
    String city;

    public String getcity() {
        return city;
    }

    public void setLocation(String city) {
        this.city=city;
    }

    public Locationgps()
    {

    }
    public Locationgps(float lon,float lat)
    {
        latitude=lat;
        longitude=lon;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
