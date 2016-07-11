package com.ir.android.splash;

import android.content.Intent;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.NavigationHelper;

/**
 * Created by bassam on 09-07-2016.
 */
public class SplashCtrl extends Controller {

    @Override
    public ViewModel initViewModel() {
        return null;
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);

        TaskSplashWait task = new TaskSplashWait(this, activity);
        task.execute();
    }

    @Override
    public boolean onBack() {

        // cancel back
        return false;
    }

    @Override
    public void onTaskFinished(Task task, Result result) {
        super.onTaskFinished(task, result);

        finish();
        NavigationHelper.showHome(getContext());
    }
}
