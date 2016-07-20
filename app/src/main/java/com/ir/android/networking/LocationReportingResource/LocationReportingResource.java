package com.ir.android.networking.LocationReportingResource;

import android.content.Context;

import com.ir.android.networking.basicimplementation.WLResource;
import com.worklight.location.api.geo.WLCoordinate;
import com.worklight.wlclient.api.WLResponse;

import java.util.TimeZone;

/**
 * Created by Henawey on 7/11/16.
 */
public class LocationReportingResource extends WLResource {

    private WLCoordinate wlCoordinate;

    public void setCoordinate(double latidute,double longitude) {
        this.wlCoordinate = new WLCoordinate(latidute,longitude);
    }

    public WLCoordinate getCoordinate() {
        return wlCoordinate;
    }

    public LocationReportingResource(Context context) {
        super(context);
    }

    @Override
    public String getAdapterName() {
        return "IoCIntegrationAdapter";
    }

    @Override
    public String getProcedureName() {
        return "reportEvent";
    }

    @Override
    public void invoke() throws LocationReportingException {
        try {
            addParameter(System.currentTimeMillis());//startDate
            addParameter(System.currentTimeMillis());//endDate
            addParameter(System.currentTimeMillis());//lastUpdate
            addParameter("Point("+getCoordinate().getLatitude()+" "+getCoordinate().getLongitude()+")");//location
            addParameter("Report New Location");//name
            addParameter("");//severity
            addParameter("");//sentBy

            TimeZone timeZone=TimeZone.getDefault();
            long timezoneInMinutes = (timeZone.getRawOffset() / 1000)  / 60;
            addParameter(timezoneInMinutes);//timezone

            addParameter(1);//id
            addParameter(1);//datasourceID
            addParameter(getLtpaToken2(getContext()));//ltpaToken
            WLResponse response = process();
            if(response.getStatus()!=200){
                throw new LocationReportingException(response.getResponseText());
            }
        }catch (Exception e){
            throw new LocationReportingException(e);
        }
    }
}
