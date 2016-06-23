package com.ir.android.login;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;

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
        // TODO Start login request

    }


    @Override
    protected Result onTaskWork() {
        if (username.equalsIgnoreCase("eman"))
            return new Result(0);

        else
            return new Result(1);
    }

}
