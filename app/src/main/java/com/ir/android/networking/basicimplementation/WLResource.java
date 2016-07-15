package com.ir.android.networking.basicimplementation;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ir.android.networking.basicimplementation.exceptions.ProcessingFailedException;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLRequestOptions;
import com.worklight.wlclient.api.WLResponse;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public abstract class WLResource implements Resource {

    private final static String SHARED_PREFERNCES_NAME = "WLResource";
    private final static String LTPA_TOKEN2_SHARED_PREFERNCES_NAME = "ltpaToken2";

    @JsonIgnore
    private ArrayList<Object> parameters;

    @JsonIgnore
    private Context context;

    @JsonIgnore
    private String ltpaToken2;

    @JsonIgnore
    private WLClient client;

    public WLResource(Context context)  {
        super();
        this.context = context;

        try {
            client = WLClient.getInstance();
        }catch (RuntimeException e){
            client=WLClient.createInstance(this.context);
        }

        parameters = new ArrayList<>();
    }

    protected String getLtpaToken2() {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String ltpaToken2 = prefs.getString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, null);

        return ltpaToken2;
    }

    protected WLResponse process() throws ProcessingFailedException {
        try {

            BasicWLResponseListener responseListener = new BasicWLResponseListener();
            client.connect(responseListener);
            WLResponse response=responseListener.getResponseSync();
            if(response.getStatus()!=200){
                throw new ProcessingFailedException(response.getResponseText());
            }

            responseListener = new BasicWLResponseListener();

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

            response = responseListener.getResponseSync();

            Header cookie = response.getHeader("Set-Cookie");
            if (cookie != null) {
                String cookieValue = cookie.getValue();
                int startIndex = cookieValue.indexOf("LtpaToken2=");
                int endIndex = cookieValue.indexOf(";", startIndex);
                ltpaToken2 = cookieValue.substring(startIndex, endIndex);

                SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();
                editor.putString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, ltpaToken2);
                editor.commit();
            }

            return response;

        } catch (Exception e) {
            throw new ProcessingFailedException(e);
        }
    }

    public abstract String getAdapterName();

    public abstract String getProcedureName();

    public Context getContext() {
        return context;
    }

    public void addParameter(Object object) {
        parameters.add(object);
    }

}
