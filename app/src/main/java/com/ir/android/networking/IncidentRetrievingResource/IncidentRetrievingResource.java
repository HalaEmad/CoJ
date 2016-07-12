package com.ir.android.networking.IncidentRetrievingResource;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.android.networking.basicimplementation.WLResource;
import com.ir.android.networking.basicimplementation.exceptions.SavingFailedException;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Henawey on 7/11/16.
 */
public class IncidentRetrievingResource extends WLResource {

    public IncidentRetrievingResource(Context context){
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

            throw new IncidentRetrievingFailedException(e);
        }
    }

}
