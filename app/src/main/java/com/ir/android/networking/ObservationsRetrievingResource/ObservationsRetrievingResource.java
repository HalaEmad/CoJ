package com.ir.android.networking.ObservationsRetrievingResource;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.FeatureModels.Feature;
import com.ir.android.networking.FeatureModels.mapping.DynamicPropertiesResolver;
import com.ir.android.networking.basicimplementation.WLResource;
import com.worklight.wlclient.api.WLResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */

public class ObservationsRetrievingResource extends WLResource {

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

    public ObservationsRetrievingResource(Context context) {
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
    public void invoke() throws ObservationsRetrievingFailedException {
        try {

            addParameter(11);//datasourceID
            addParameter("");//boundaries
            addParameter(getLtpaToken2(getContext()));//ltpaToken

            WLResponse response = process();

            if(isSuccessed(response)){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.readerForUpdating(this).readValue(response.getResponseJSON().toString());

                DynamicPropertiesResolver dynamicPropertiesResolver=new DynamicPropertiesResolver(getContext(),11,getFeatures());
                dynamicPropertiesResolver.invoke();
            }else{
                throw new ObservationsRetrievingFailedException(response.getResponseText());
            }

        }catch (Exception e){

            try {
                InputStream inputStream = getContext().getAssets().open("Observations-Response.octet-stream");

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder string = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    string.append(line).append('\n');
                }

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.readerForUpdating(this).readValue(string.toString());

                DynamicPropertiesResolver dynamicPropertiesResolver=new DynamicPropertiesResolver(getContext(),11,getFeatures());
                dynamicPropertiesResolver.invoke();

                return;
            }catch(Exception e1){
                    /*it's stub please don't handle this*/
                e1.printStackTrace();
            }
            //Stub end

            throw new ObservationsRetrievingFailedException(e);
        }
    }

}
