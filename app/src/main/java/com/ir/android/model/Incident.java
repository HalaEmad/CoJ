package com.ir.android.model;

import com.google.android.gms.maps.model.LatLng;
import com.ir.android.R;

/**
 * Created by emanhassan on 6/10/16.
 */
public class Incident {

    private double latitude;
    private double longitude;
    private String info;
    private int drawableId;


    public Incident(double latitude, double longitude, String info) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        // TODO remove this constant id
        this.drawableId
                = R.drawable.marker;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}

