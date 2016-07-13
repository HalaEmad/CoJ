package com.ir.android.networking.IncidentRetrievingResource;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/12/16.
 */
public class Geometry {
    private String type;
    private ArrayList<Double> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
