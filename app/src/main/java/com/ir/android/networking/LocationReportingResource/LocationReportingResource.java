package com.ir.android.networking.LocationReportingResource;

import android.content.Context;
import android.location.Location;

import com.ir.android.networking.basicimplementation.WLResource;
import com.worklight.wlclient.api.WLResponse;

import java.util.TimeZone;

/**
 * Created by Henawey on 7/11/16.
 */
public class LocationReportingResource extends WLResource {

    private Location location;
    private String userID;

    public LocationReportingResource(Context context,Location location,String userID) {
        super(context);
        this.location=location;
        this.userID=userID;
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
            addParameter("Point("+location.getLatitude()+" "+location.getLongitude()+")");//location
            addParameter("Report New Location");//name
            addParameter("");//severity
            addParameter("");//sentBy

            TimeZone timeZone=TimeZone.getDefault();
            long timezoneInMinutes = (timeZone.getRawOffset() / 1000)  / 60;
            addParameter(timezoneInMinutes);//timezone

            addParameter(userID);//id
            addParameter(12);//datasourceID
            addParameter(getLtpaToken2(getContext()));//ltpaToken
            WLResponse response = process();

            if(isSuccessed(response)==false){
                throw new LocationReportingException(response.getResponseText());
            }
        }catch (Exception e){
            throw new LocationReportingException(e);
        }
    }
}
