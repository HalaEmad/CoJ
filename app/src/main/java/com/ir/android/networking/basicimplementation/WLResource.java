package com.ir.android.networking.basicimplementation;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ir.android.networking.basicimplementation.exceptions.ProcessingFailedException;
import com.ir.android.networking.basicimplementation.exceptions.WLUnresponsiveHostException;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLRequestOptions;
import com.worklight.wlclient.api.WLResponse;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Henawey on 7/11/16.
 */
public abstract class WLResource implements Resource {

    private final static String SHARED_PREFERNCES_NAME = "WLResource";
    private final static String LTPA_TOKEN2_SHARED_PREFERNCES_NAME = "ltpaToken2";
    private final static String JSESSION_ID_SHARED_PREFERNCES_NAME = "JSessionID";

    @JsonIgnore
    private ArrayList<Object> parameters;

    @JsonIgnore
    private Context context;

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

    public abstract String getAdapterName();

    public abstract String getProcedureName();

    public Context getContext() {
        return context;
    }

    public void addParameter(Object object) {
        parameters.add(object);
    }

    protected static String getLtpaToken2(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String ltpaToken2 = prefs.getString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, null);

        return ltpaToken2;
    }

    protected static String getJSessionID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE);
        String jsessionID = prefs.getString(JSESSION_ID_SHARED_PREFERNCES_NAME, null);

        return jsessionID;
    }

    protected  static void clearCache(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, context.MODE_PRIVATE).edit();
        editor.remove(LTPA_TOKEN2_SHARED_PREFERNCES_NAME);
        editor.remove(JSESSION_ID_SHARED_PREFERNCES_NAME);
        editor.apply();
    }

    protected WLResponse process() throws ProcessingFailedException {

        try {

            connectToServer();

            WLResponse response = invokeAdapter();

            saveJSessionID(response);

            saveLTPAToken2(response);

            return response;

        } catch (Exception e) {
            throw new ProcessingFailedException(e);
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
        WLResponse response=responseListener.getResponseSync();
        if(response.getStatus()!=200){
            if(response instanceof WLFailResponse){

                WLFailResponse failResponse=(WLFailResponse) response;

                switch (failResponse.getErrorCode()){
                    case UNRESPONSIVE_HOST:
                        throw new WLUnresponsiveHostException(failResponse.getErrorMsg());
                    default:
                        throw new ProcessingFailedException(failResponse.getErrorMsg());
                }

            }else {
                throw new ProcessingFailedException(response.getResponseText());
            }
        }
    }

    private void saveJSessionID(WLResponse response) throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject= response.getResponseJSON();

        JSONObject responseHeaders=jsonObject.getJSONObject("responseHeaders");

        if(responseHeaders.has("com.ibm.ioc.sessionid")) {
            String jsessionID = responseHeaders.getString("com.ibm.ioc.sessionid");

            if (jsessionID != null && !jsessionID.isEmpty()) {

                editor.putString(JSESSION_ID_SHARED_PREFERNCES_NAME, jsessionID);
                editor.commit();
            }
        }
    }
    private void saveLTPAToken2(WLResponse response) throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE).edit();

        Header cookie = response.getHeader("Set-Cookie");
        if (cookie != null) {
            String cookieValue = cookie.getValue();
            int startIndex = cookieValue.indexOf("LtpaToken2=");
            int endIndex = cookieValue.indexOf(";", startIndex);
            String ltpaToken2 = cookieValue.substring(startIndex, endIndex);

            if (ltpaToken2!=null && !ltpaToken2.isEmpty()) {
                editor.putString(LTPA_TOKEN2_SHARED_PREFERNCES_NAME, ltpaToken2);
                editor.commit();
            }
        }

    }

}
