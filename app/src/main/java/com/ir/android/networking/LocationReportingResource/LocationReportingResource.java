package com.ir.android.networking.LocationReportingResource;

import android.content.Context;
import android.location.Location;

import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.login.UserResource;
import com.worklight.wlclient.api.WLResponse;

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
        return "LocationControlAdapter";
    }

    @Override
    public String getProcedureName() {
        return "updateOfficerLocation";
    }

    @Override
    public void invoke() throws LocationReportingException {
        try {
            addParameter(UserResource.getCurrentUserID(getContext()));//uid
            addParameter(location.getLatitude());//latitude
            addParameter(location.getLongitude());//longitude
            addParameter(UserResource.getBase64(getContext()));//base64
            addParameter(UserResource.getJSessionID(getContext()));//sessionID

            WLResponse response = process();

            if(isSuccessed(response)==false){
                throw new LocationReportingException(response.getResponseText());
            }
        }catch (Exception e){
            throw new LocationReportingException(e);
        }
    }
}
