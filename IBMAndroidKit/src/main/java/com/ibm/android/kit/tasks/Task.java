package com.ibm.android.kit.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ibm.android.kit.models.IResult;
import com.ibm.android.kit.models.Result;

public abstract class Task extends AsyncTask<Void, Integer, Result> implements IResult {

	protected ITask listener;
	protected Context context;

	protected abstract Result onTaskWork();

	public Task(ITask listener, Context context) {

		this.listener = listener;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

		if (listener != null)
			listener.onTaskStarted(this);
	}

	@Override
	protected Result doInBackground(Void... params) {

		return onTaskWork();
	}

	@Override
	protected void onPostExecute(Result result) {

		super.onPostExecute(result);

		if (listener != null)
			listener.onTaskFinished(this, result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {

		if (listener != null)
			listener.onTaskProgress(this, values[0]);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		if (listener != null)
			listener.onTaskCanceled(this);
	}

	public void updateView(int progress) {

		publishProgress(progress);
	}

	public Result executeSync() {

		return onTaskWork();
	}

}