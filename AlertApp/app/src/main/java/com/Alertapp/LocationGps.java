package com.Alertapp;

/**
 * Created by kusumasri on 4/20/17.
 */

public class LocationGps {

    private float latitude;
    private float longitude;

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

    public float getLatitude() {
        return latitude;
    }

}
