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
    private UserResource loginResource;

    public LoginTask(ITask listener, Context context, String username, String password) {
        super(listener, context);
        loginResource = new UserResource(username, password, this.context);
    }


    @Override
    protected Result onTaskWork() {
        try {
            loginResource.invoke();
            return new Result(0,loginResource);
        } catch (Exception e) {
            return new Result(1,e);
        }
    }

}
