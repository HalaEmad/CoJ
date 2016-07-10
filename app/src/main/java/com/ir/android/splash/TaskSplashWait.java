/**
 *
 */
package com.ir.android.splash;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;

/**
 * Created by bassam on 09-07-2016.
 */
public class TaskSplashWait extends Task {

    private static final int SPLASH_WAIT_SEC = 4;

    public TaskSplashWait(ITask listener, Context context) {
        super(listener, context);
    }

    @Override
    protected Result onTaskWork() {

        try {
            Thread.sleep(SPLASH_WAIT_SEC * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
