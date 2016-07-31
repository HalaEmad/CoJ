package com.ir.android.networking.FeatureModels.mapping;

import android.content.Context;
import android.util.Log;

import com.ir.android.networking.FeatureModels.DynamicProperty;
import com.ir.android.networking.FeatureModels.Feature;
import com.ir.android.networking.FeatureModels.Properties;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.login.UserResource;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Henawey on 7/20/16.
 */
public class DynamicPropertiesResolver extends WLResource {

    private int datasourceID;
    private ArrayList<Feature> features;
    public DynamicPropertiesResolver(Context context,int datasourceID, ArrayList<Feature> features) {
        super(context);
        this.datasourceID=datasourceID;
        this.features=features;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    @Override
    public String getAdapterName() {
        return "Datasources";
    }

    @Override
    public String getProcedureName() {
        return "dataSourceMapping";
    }

    @Override
    public void invoke() throws ResolvingFailedException {
        try {

            addParameter(datasourceID);//datasourceID
            addParameter(UserResource.getBase64(getContext()));//base64
            addParameter(UserResource.getJSessionID(getContext()));//sessionID

            WLResponse response = process();

            if(isSuccessed(response)){
                resolveFeatures(response.getResponseJSON());
            }else{
                throw new ResolvingFailedException(response.getResponseText());
            }

        }catch (Exception e){

            throw new ResolvingFailedException(e);
        }
    }
    private void resolveFeatures(JSONObject jsonObject) throws ResolvingFailedException{
        try {
            HashMap<String,String> mapping=new HashMap<>();
            JSONArray columns=jsonObject.getJSONArray("columns");
            for (int i = 0; i < columns.length(); i++) {
                JSONObject column=columns.getJSONObject(i);
                String category=column.getString("category");
                if (category.equalsIgnoreCase("MINIMAL")||category.equalsIgnoreCase("KEY")){
                      String format="";
                      if (column.has("format")) {
                            format = column.getString("format");
                       }

                    if (format.isEmpty() || (!format.equalsIgnoreCase("SHAPE") && !format.equalsIgnoreCase("TIMESTAMP"))) {
                        JSONArray targetNames=column.getJSONArray("targetNames");
                        String targetName =targetNames.getString(0);
                        String targetNewName =column.getString("i18nLabel");
                        mapping.put(targetName,targetNewName);
                    }
                }
            }

            for (Feature feature:features) {
                Properties properties=feature.getProperties();
                ArrayList<DynamicProperty> dynamicProperties=properties.getDynamicProperties();
                for (DynamicProperty dynamicProperty:dynamicProperties) {
                    String newName=mapping.get(dynamicProperty.getName());


                    if(newName!=null){
                        dynamicProperty.setName(newName);
                        Log.i("Mapping", "Name: "+dynamicProperty.getName());
                        Log.i("Mapping", "Value: "+dynamicProperty.getValue());

                        switch (dynamicProperty.getName().toLowerCase()){
                            case "call category":
                                properties.setCallCategory(dynamicProperty.getValue());
                                break;
                            case "call type":
                            case "incident type":
                                properties.setCallType(dynamicProperty.getValue());
                                break;
                            case "address":
                                properties.setAddress(dynamicProperty.getValue());
                                break;
                            case "submittedby":
                                properties.setSubmittedBy(dynamicProperty.getValue());
                                break;
                            case "submitteddatetime":
                                properties.setSubmittedDateTime(dynamicProperty.getValue());
                                break;
                            case "expirationdatetime":
                                properties.setExpirationDateTime(dynamicProperty.getValue());
                                break;
                            case "status":
                                properties.setStatus(dynamicProperty.getValue());
                                break;
                            case "severity":
                                properties.setSeverity(dynamicProperty.getValue());
                                break;
                        }

                    }
                }
            }

        }catch (Exception e){
            throw new ResolvingFailedException(e);
        }
    }
}
