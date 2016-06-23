/**
 *
 */
package com.ibm.android.kit.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ibm.android.kit.views.activities.Activity;

import java.util.Observable;
import java.util.Observer;

/**
 * @author bassam
 */
public abstract class Controller extends Fragment implements ITask, Observer, IViewListener {

    protected ViewModel viewModel;

    protected Activity activity;

    public abstract ViewModel initViewModel();

    protected Result result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void init(Intent intent) {
        viewModel = initViewModel();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getScreen() {
        return activity;
    }

    public void showProgress() {
        this.activity.showProgress();
    }

    public void dismissPorgress() {
        this.activity.dismissProgress();
    }

    public void finish() {
        activity.finish();
    }

    public void registerForChanges(Observer observer) {
        if (viewModel != null) {
            viewModel.registerForChanges(observer);
        }
    }

    public void unregisterForChanges(Observer observer) {
        if (viewModel != null) {
            viewModel.unregisterForChanges(observer);
        }
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void showOkDialog(String tag, String title, String msg) {
        activity.showOkDialog(tag, title, msg);
    }

    public void showOkCancelDialog(String tag, String title, String msg) {
        activity.showOkCancelDialog(tag, title, msg);
    }

    public void showDatePicker(String tag, int year, int month, int day, long min, long max) {
        activity.showDatePicker(tag, year, month, day, min, max);
    }

    public void showTimePicker() {
        activity.showTimePicker();
    }

    public void showToast(String text) {
        activity.showToast(text);
    }

    public void showOkErrorDialog(String msg) {
        activity.showOkDialog("", "Error", msg);
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    @Override
    public void onTaskStarted(Task task) {

    }

    @Override
    public void onTaskFinished(Task task, Result result) {

    }

    @Override
    public void onTaskProgress(Task task, int progress) {

    }

    @Override
    public void onTaskCanceled(Task task) {

    }

    public void destroy() {

    }

    public String getTitle() {
        return null;
    }

    public void onDialogPositive(DialogInterface dialog, String tag) {

    }

    public void onDialogNegative(DialogInterface dialog, String tag) {

    }

    public boolean onBack() {

        return true;
    }

    public void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth) {

    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
