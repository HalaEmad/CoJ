package com.ir.android.login;

import android.content.Intent;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.map.IncidentActivity;

/**
 * Created by emanhassan on 6/12/16.
 */
public class LoginCtrl extends Controller {

    private LoginTask task;

    public void startLogin(String username, String password) {

        task = new LoginTask(this, getContext(), username, password);
        task.execute();

    }


    @Override
    public void onTaskStarted(Task task) {
        super.onTaskStarted(task);
        showProgress();
    }

    @Override
    public void onTaskFinished(Task task, Result result) {
        super.onTaskFinished(task, result);
        dismissPorgress();
        if (result.getError() != 0) {
            showOkErrorDialog("Login Failed");
        } else {
            Intent intent = new Intent(getContext(), IncidentActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public ViewModel initViewModel() {
        return null;
    }
}
