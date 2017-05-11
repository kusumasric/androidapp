package com.Alertapp;

/**
 * Created by kusumasri on 4/20/17.
 */

public class LocationGps {

    public float latitude;
    public float longitude;

    public LocationGps()
    {

    }
    public LocationGps(float lon, float lat)
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
