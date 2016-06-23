package com.ibm.android.kit.tasks;

import com.ibm.android.kit.models.Result;

public interface ITask {

	public void onTaskStarted(Task task);

	public void onTaskFinished(Task task, Result result);

	public void onTaskProgress(Task task, int progress);

	public void onTaskCanceled(Task task);

}
