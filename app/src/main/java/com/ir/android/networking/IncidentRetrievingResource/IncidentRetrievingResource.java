package com.ir.android.networking.IncidentRetrievingResource;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.FeatureModels.Feature;
import com.ir.android.networking.FeatureModels.mapping.DynamicPropertiesResolver;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.login.UserResource;
import com.worklight.wlclient.api.WLResponse;

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
        return "Datasources";
    }

    @Override
    public String getProcedureName() {
        return "getEvents";
    }

    @Override
    public void invoke() throws IncidentRetrievingFailedException {
        try {

            addParameter(10);//datasourceID
            addParameter(UserResource.getBase64(getContext()));//base64
            addParameter(UserResource.getJSessionID(getContext()));//sessionID

            WLResponse response = process();

            if(isSuccessed(response)){
                ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                objectMapper.readerForUpdating(this).readValue(response.getResponseJSON().toString());

                DynamicPropertiesResolver dynamicPropertiesResolver=new DynamicPropertiesResolver(getContext(),10,getFeatures());
                dynamicPropertiesResolver.invoke();
            }else{
                throw new IncidentRetrievingFailedException(response.getResponseText());
            }

        }catch (Exception e){

            throw new IncidentRetrievingFailedException(e);
        }
    }

}
