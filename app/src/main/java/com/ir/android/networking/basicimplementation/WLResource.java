package com.ir.android.networking.basicimplementation;

import android.content.Context;

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

    private ArrayList<Object> parameters;


    public WLResource(){
        super();
        parameters=new ArrayList<>();
    }

    protected WLResponse process(Context context) throws ProcessingFailedException {
        try {

            BasicWLResponseListener responseListener=new BasicWLResponseListener();

            WLClient client=WLClient.createInstance(context);

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

            client.invokeProcedure(invocationData,responseListener , options);

            WLResponse response = responseListener.getResponseSync();

            return response;

        } catch (Exception e) {
            throw new ProcessingFailedException(e);
        }
    }

    public abstract String getAdapterName();

    public abstract String getProcedureName();

    public void addParameter(Object object){
        parameters.add(object);
    }
}
