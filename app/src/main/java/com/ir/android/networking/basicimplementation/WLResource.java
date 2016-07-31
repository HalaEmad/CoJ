package com.ir.android.networking.basicimplementation;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ir.android.networking.basicimplementation.exceptions.ProcessingFailedException;
import com.ir.android.networking.basicimplementation.exceptions.WLUnresponsiveHostException;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLRequestOptions;
import com.worklight.wlclient.api.WLResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public abstract class WLResource implements Resource {

    @JsonIgnore
    private ArrayList<Object> parameters;

    @JsonIgnore
    private Context context;

    @JsonIgnore
    private WLClient client;

    public WLResource(Context context) {
        super();
        this.context = context;

        try {
            client = WLClient.getInstance();
        } catch (RuntimeException e) {
            client = WLClient.createInstance(this.context);
        }

        parameters = new ArrayList<>();
    }

    public abstract String getAdapterName();

    public abstract String getProcedureName();

    public Context getContext() {
        return context;
    }

    public void addParameter(Object object) {
        parameters.add(object);
    }


    protected WLResponse process() throws ProcessingFailedException {

        try {

            connectToServer();

            WLResponse response = invokeAdapter();

            return response;

        } catch (Exception e) {
            throw new ProcessingFailedException(e);
        }
    }

    protected boolean isSuccessed(WLResponse response) throws JSONException {
        JSONObject responseJson = response.getResponseJSON();
        if (response.getStatus() != 200
                || (responseJson.has("isSuccessful") && responseJson.getBoolean("isSuccessful") == false)
                || (responseJson.has("statusCode") && (responseJson.getInt("statusCode") != 200))) {
            return false;
        } else {
            return true;
        }
    }

    private WLResponse invokeAdapter() throws InterruptedException {
        BasicWLResponseListener responseListener = new BasicWLResponseListener();

        String adapterName = getAdapterName();
        String procedureName = getProcedureName();
        WLProcedureInvocationData invocationData =
                new WLProcedureInvocationData(adapterName, procedureName);

        if (!parameters.isEmpty()) {
            //Parameters
            invocationData.setParameters(parameters.toArray());
        }
        WLRequestOptions options = new WLRequestOptions();
        options.setTimeout(30000);

        client.invokeProcedure(invocationData, responseListener, options);

        return responseListener.getResponseSync();
    }

    private void connectToServer() throws InterruptedException, ProcessingFailedException {

        BasicWLResponseListener responseListener = new BasicWLResponseListener();
        client.connect(responseListener);
        WLResponse response = responseListener.getResponseSync();
        if (response.getStatus() != 200) {
            if (response instanceof WLFailResponse) {

                WLFailResponse failResponse = (WLFailResponse) response;

                switch (failResponse.getErrorCode()) {
                    case UNRESPONSIVE_HOST:
                        throw new WLUnresponsiveHostException(failResponse.getErrorMsg());
                    default:
                        throw new ProcessingFailedException(failResponse.getErrorMsg());
                }

            } else {
                throw new ProcessingFailedException(response.getResponseText());
            }
        }
    }

}
