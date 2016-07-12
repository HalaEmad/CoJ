package com.ir.android.networking.basicimplementation;

import android.content.Context;
import android.content.SharedPreferences;

import com.ir.android.networking.basicimplementation.exceptions.ProcessingFailedException;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLRequestOptions;
import com.worklight.wlclient.api.WLResponse;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public abstract class WLResource implements Resource {

    private final static String SHARED_PREFERNCES_NAME = "WLResource";
    private final static String LTPA_TOKEN2_SHARED_PREFERNCES_NAME = "ltpaToken2";
    private ArrayList<Object> parameters;
    private Context context;

    private String ltpaToken2;

    public WLResource(Context context) {
        super();
        this.context = context;
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

            final WLClient client = WLClient.createInstance(context);

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

            WLResponse response = responseListener.getResponseSync();

            // Bassam++ commented because it gives Header class not found error
//            Header cookie = response.getHeader("Set-Cookie");
//            if (cookie != null) {
//                String cookieValue = cookie.getValue();
//                int startIndex = cookieValue.indexOf("LtpaToken2=");
//                int endIndex = cookieValue.indexOf(";", startIndex);
//                ltpaToken2 = cookieValue.substring(startIndex, endIndex);
//
//                SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();
//                editor.putString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, ltpaToken2);
//                editor.commit();
//            }
            // Bassam--

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
