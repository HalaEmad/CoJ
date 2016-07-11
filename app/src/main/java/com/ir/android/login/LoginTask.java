package com.ir.android.login;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.networking.login.UserResource;

/**
 * Created by emanhassan on 6/12/16.
 */
public class LoginTask extends Task {
    private String username;
    private String password;

    public LoginTask(ITask listener, Context context, String username, String password) {
        super(listener, context);
        this.username = username;
        this.password = password;
    }


    @Override
    protected Result onTaskWork() {

        try {
            UserResource loginResource=new UserResource(username,password);
            loginResource.retrieve(context);
//            if (loginResponseListener.status== ResponseStatus.FAIL){
                return new Result(1,loginResource);
//            }else{
//                return new Result(0);
//            }
        } catch (Exception e) {
            return new Result(0,e);
            //TODO: Please make reuslt 0 once "java.lang.RuntimeException: WLConfig(): Can't load wlclient.properties file" solved
//            return new Result(1);//java.lang.RuntimeException: WLConfig(): Can't load wlclient.properties file
        }

    }

}
