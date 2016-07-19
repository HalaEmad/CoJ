package com.ir.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.ibm.android.kit.utils.LocationUtility;
import com.ir.android.networking.LocationReportingResource.LocationReportingResource;

/**
 * Created by emanhassan on 6/26/16.
 */
public class LocationService extends IntentService implements LocationListener {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name) {
        super(name);
    }

    public LocationService() {
        super("LocationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        LocationUtility.requestLocationUpdates(this, 0, LocationManager.GPS_PROVIDER, 60 * 1000, 0, this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LocationUtility.requestLocationUpdates(this, 0, LocationManager.NETWORK_PROVIDER, 60000, 0, this);
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUtility.removeLocationUpdate(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            LocationReportingResource locationReportingResource=new LocationReportingResource(getBaseContext());
            locationReportingResource.setCoordinate(location.getLatitude(),location.getLongitude());
            locationReportingResource.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("#COJLocation#", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
        // TODO send location to server
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static void start(Context context) {
        Intent locationServiceIntent = new Intent(context, LocationService.class);
        context.startService(locationServiceIntent);
    }

    public static void stop(Context context) {
        Intent locationServiceIntent = new Intent(context, LocationService.class);
        context.stopService(locationServiceIntent);
    }


}
