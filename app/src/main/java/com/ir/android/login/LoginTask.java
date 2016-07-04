package com.ir.android.login;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.worklight.utils.Base64;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLRequestOptions;

/**
 * Created by emanhassan on 6/12/16.
 */
public class LoginTask extends Task {
    private String username;
    private String password;
    private LoginResponseListener loginResponseListener;

    public LoginTask(ITask listener, Context context, String username, String password) {

        super(listener, context);
        this.username = username;
        this.password = password;

    }


    @Override
    protected Result onTaskWork() {

        try {
            String authorizationInput=
                    Base64.encode((this.username+":"+this.password).getBytes(),"UTF-8");
            //TODO call WL Adapter

            loginResponseListener=new LoginResponseListener();
            WLClient client=WLClient.createInstance(context);

            String adapterName = "Authentication";
            String procedureName = "authenticateUser";
            WLProcedureInvocationData invocationData =
                    new WLProcedureInvocationData(adapterName, procedureName);

            //Parameters
            Object[] parameters = new Object[] {authorizationInput};
            invocationData.setParameters(parameters);
            WLRequestOptions options = new WLRequestOptions();
            options.setTimeout(30000);

            client.invokeProcedure(invocationData,loginResponseListener , options);

            /*WLResponse response=*/loginResponseListener.getLoginResponse();
            if (loginResponseListener.status==ResponseStatus.FAIL){
                return new Result(1);
            }else{
                return new Result(0);
            }
        } catch (Exception e) {
            return new Result(0);
            //TODO: Please make reuslt 0 once "java.lang.RuntimeException: WLConfig(): Can't load wlclient.properties file" solved
//            return new Result(1);//java.lang.RuntimeException: WLConfig(): Can't load wlclient.properties file
        }

//        if (username.equalsIgnoreCase("eman"))
//            return new Result(0);
//
//        else
//            return new Result(1);
    }

}
