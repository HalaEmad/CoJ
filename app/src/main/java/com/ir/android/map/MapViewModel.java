package com.ir.android.map;

import com.ibm.android.kit.models.ViewModel;
import com.ir.android.model.Incident;

import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class MapViewModel extends ViewModel {
    private ArrayList<Incident> mIncidentsLocs;
    private double mLatitude;
    private double mLongitude;



    public ArrayList<Incident> getIncidentsLocs() {
        return mIncidentsLocs;
    }

    public void setIncidentsLocs(ArrayList<Incident> incidentsLocs) {
        this.mIncidentsLocs = new ArrayList<Incident>(incidentsLocs);
        setModelChanged();
    }


    public void setMyLocation(double lat, double lng) {
        this.mLatitude= lat;
        this.mLongitude = lng;
        setModelChanged();
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
