package com.ir.android.networking.IncidentRetrievingResource;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.SavingFailedException;
import com.worklight.wlclient.api.WLResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */

public class IncidentRetrievingResource extends WLResource {

    private String type;
    private int id;
    private long lastUpdateDate;
    private ArrayList<Feature> features;
    private boolean moreDataAvailable;
    private ArrayList<String> messages;

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public IncidentRetrievingResource(Context context) {
        super(context);
    }

    @Override
    public String getAdapterName() {
        return "IoCIntegrationAdapter";
    }

    @Override
    public String getProcedureName() {
        return "getEvents";
    }

    @Override
    public void save() throws SavingFailedException {

    }

    @Override
    public void retrieve() throws IncidentRetrievingFailedException {
        try {

            //TODO: missing boundaries

            addParameter(1);//datasourceID
            addParameter("??");//boundaries
            addParameter(getLtpaToken2());//ltpaToken

            WLResponse response = process();

            if(response.getStatus()!=200){
                throw new IncidentRetrievingFailedException(response.getResponseText());
            }
        }catch (Exception e){
            //TODO:remove stub

            try {
                InputStream inputStream = getContext().getAssets().open("Incidents-Response.octet-stream");

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder string = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    string.append(line).append('\n');
                }

                //read from stub
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.readerForUpdating(this).readValue(string.toString());

                return;
            }catch(Exception e1){
                    /*it's stub please don't handle this*/
                e1.printStackTrace();
            }
            //Stub end

            throw new IncidentRetrievingFailedException(e);
        }
    }

}
