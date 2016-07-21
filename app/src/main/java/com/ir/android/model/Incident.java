package com.ir.android.model;

import com.ir.android.R;

/**
 * Created by emanhassan on 7/19/16.
 */
public class Incident {
    public final static int TYPE_ASSUALT = 1;
    public final static int TYPE_OFFICER = 2;

    private int type;
    private String typeName;
    private double latitude;
    private  double longitude;
    private String distance;
    private int drawableId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        if (distance == null)
            distance = "";
        this.distance = distance;
    }

    public int getDrawableId() {
        if (type == TYPE_OFFICER){
            return R.mipmap.foot_patrol_officer;
        }else
            return R.mipmap.assault_icon;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
