package com.ir.android.login;

import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

/**
 * Created by Henawey on 6/26/16.
 */
public class BasicWLResponseListener implements WLResponseListener{

    private Thread syncThread;
    private WLResponse response;
    public ResponseStatus status;

    public BasicWLResponseListener(){
        super();
        status=ResponseStatus.UNKNOWN;
    }
    protected WLResponse getResponseSync() throws InterruptedException{
        syncThread =new Thread();
        syncThread.start();
        synchronized(syncThread) {
            syncThread.wait();
            return response;
        }
    }

    @Override
    public void onSuccess(WLResponse response) {
        status=ResponseStatus.SUCCESS;
        this.response=response;
        syncThread.notify();
    }
    @Override
    public void onFailure(WLFailResponse response) {
        status=ResponseStatus.FAIL;
        this.response=response;
        syncThread.notify();
    }
}

enum ResponseStatus {
    SUCCESS, FAIL , UNKNOWN
}
