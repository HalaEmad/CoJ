/**
 *
 */
package com.ibm.android.kit.views.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ibm.android.kit.R;
import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.controllers.IViewListener;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.AkDialog;
import com.ibm.android.kit.views.fragments.DatePickerFragment;
import com.ibm.android.kit.views.fragments.ProgressDialogFragment;
import com.ibm.android.kit.views.fragments.TimePickerFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * @author bassam
 */
public abstract class Activity extends AppCompatActivity implements Observer, AkDialog.DialogListener, DatePickerFragment.OnDateSetListener {

    protected Controller controller;

    protected IViewListener viewListener;

    private ProgressDialogFragment mProgressDlg;

    protected abstract Controller createController();

    protected abstract String getControllerTag();

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void bindViews(ViewModel viewModel);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        FragmentManager fm = getSupportFragmentManager();
        controller = (Controller) fm.findFragmentByTag(getControllerTag());

        mProgressDlg = new ProgressDialogFragment();

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (controller == null) {

            controller = createController();
            fm.beginTransaction().add(controller, getControllerTag()).commit();
            controller.setActivity(this);
            setViewListener(controller);
            controller.init(getIntent());

        } else {

            controller.setActivity(this);
            setViewListener(controller);
        }

        String title = controller.getTitle();
        if (title != null && !title.isEmpty())
            setTitle(title);

        initViews();

        bindViews(controller.getViewModel());
    }

    protected void setViewListener(IViewListener listener) {
        this.viewListener = listener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.destroy();
    }

    public void showProgress() {

        if (mProgressDlg != null && !mProgressDlg.isAdded() && !mProgressDlg.isVisible()) {
            mProgressDlg.show(getFragmentManager(), this.getClass().getSimpleName());
        }
    }

    public void dismissProgress() {

        if (mProgressDlg != null && mProgressDlg.isAdded()) {
            mProgressDlg.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        controller.registerForChanges(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        controller.unregisterForChanges(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bindViews(controller.getViewModel());
    }

    @Override
    public void update(final Observable observable, Object data) {

        if (observable instanceof ViewModel) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    bindViews((ViewModel) observable);
                }
            });
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showOkDialog(String tag, String title, String msg) {
        AkDialog.showDialog(this, tag, title, msg, getString(R.string.ok), null);
    }

    public void showOkCancelDialog(String tag, String title, String msg) {
        AkDialog.showDialog(this, tag, title, msg, getString(R.string.ok), getString(R.string.cancel));
    }

    public void showDatePicker(String tag) {
        showDatePicker(tag, 0, 0, 0, 0, 0);
    }

    public void showDatePicker(String tag, int year, int month, int day, long min, long max) {
        DatePickerFragment newFragment = new DatePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DatePickerFragment.ARGS_KEY_TAG, tag);
        bundle.putInt(DatePickerFragment.ARGS_KEY_YEAR, year);
        bundle.putInt(DatePickerFragment.ARGS_KEY_MONTH, month);
        bundle.putInt(DatePickerFragment.ARGS_KEY_DAY, day);
        bundle.putLong(DatePickerFragment.ARGS_KEY_MIN, min);
        bundle.putLong(DatePickerFragment.ARGS_KEY_MAX, max);

        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePicker() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onDialogPositive(DialogInterface dialog, String tag) {
        controller.onDialogPositive(dialog, tag);
    }

    @Override
    public void onDialogNegative(DialogInterface dialog, String tag) {
        controller.onDialogNegative(dialog, tag);
    }

    @Override
    public void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth) {
        controller.onDateSet(tag, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onBackPressed() {

        if (controller.onBack()) {

            finish();
        }
    }

    public void setResult(Result result) {
        controller.setResult(result);
    }

    public Result getResult() {
        return controller.getResult();
    }
}
