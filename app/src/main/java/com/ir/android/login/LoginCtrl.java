package com.ir.android.login;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.NavigationHelper;

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
            finish();
            NavigationHelper.showMain(getContext());
        }
    }

    @Override
    public ViewModel initViewModel() {
        return null;
    }
}
