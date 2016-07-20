package com.ir.android.networking.FeatureModels.mapping;

import android.content.Context;

import com.ir.android.networking.FeatureModels.DynamicProperty;
import com.ir.android.networking.FeatureModels.Feature;
import com.ir.android.networking.basicimplementation.WLResource;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Henawey on 7/20/16.
 */
public class DynamicPropertiesResolver extends WLResource {

    private ArrayList<Feature> features;
    public DynamicPropertiesResolver(Context context, ArrayList<Feature> features) {
        super(context);
        this.features=features;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
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
    public void invoke() throws ResolvingFailedException {
        try {

            addParameter(1);//datasourceID
            addParameter("");//boundaries
            addParameter(getLtpaToken2(getContext()));//ltpaToken

            WLResponse response = process();

            if(response.getStatus()!=200){
                throw new ResolvingFailedException(response.getResponseText());
            }else{

                resolveFeatures(response.getResponseText());
            }

        }catch (Exception e){

            try {
                InputStream inputStream = getContext().getAssets().open("DataSources-List-and-Attributes-Mapping.octet-stream");

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder string = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    string.append(line).append('\n');
                }

                //read from stub
                resolveFeatures(string.toString());

                return;
            }catch(Exception e1){
                    /*it's stub please don't handle this*/
                e1.printStackTrace();
            }
            //Stub end

            throw new ResolvingFailedException(e);
        }
    }
    private void resolveFeatures(String data) throws ResolvingFailedException{
        try {
            HashMap<String,String> mapping=new HashMap<>();
            JSONObject jsonObject = new JSONObject(data);
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
                ArrayList<DynamicProperty> dynamicProperties=feature.getProperties().getDynamicProperties();
                for (DynamicProperty dynamicProperty:dynamicProperties) {
                    String newName=mapping.get(dynamicProperty.getName());
                    if(newName!=null){
                        dynamicProperty.setName(newName);
                    }
                }
            }

        }catch (Exception e){
            throw new ResolvingFailedException(e);
        }
    }
}
