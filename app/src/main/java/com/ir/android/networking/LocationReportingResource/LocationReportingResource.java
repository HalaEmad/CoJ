package com.ir.android.networking.LocationReportingResource;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.RetrivingFailedException;
import com.worklight.location.api.geo.WLCoordinate;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public void save() throws LocationReportingException {
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
            addParameter(getLtpaToken2());//ltpaToken
            WLResponse response = process();
            if(response.getStatus()!=200){
                throw new LocationReportingException(response.getResponseText());
            }
        }catch (Exception e){
            //TODO:remove stub

            try {
                InputStream inputStream = getContext().getAssets().open("Data-Injection-Response.octet-stream");

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder string = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    string.append(line).append('\n');
                }


                //read from stub
                ObjectMapper objectMapper = new ObjectMapper();
                JSONObject jsonObject=new JSONObject(string.toString());
                String array=jsonObject.getString("text");
                JSONArray jsonArray=new JSONArray(array);


                objectMapper.readerForUpdating(this).readValue(jsonArray.getString(0));

                return;
            }catch(Exception e1){
                    /*it's stub please don't handle this*/
                e1.printStackTrace();
            }
            //Stub end
            throw new LocationReportingException(e);

        }
    }

    @Override
    public void retrieve() throws RetrivingFailedException {

    }
}
