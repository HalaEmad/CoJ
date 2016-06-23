package com.ibm.android.kit.views.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by bassam on 26-02-2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String ARGS_KEY_TAG = "args.key.tag";
    public static final String ARGS_KEY_YEAR = "args.key.year";
    public static final String ARGS_KEY_MONTH = "args.key.month";
    public static final String ARGS_KEY_DAY = "args.key.day";
    public static final String ARGS_KEY_MIN = "args.key.min";
    public static final String ARGS_KEY_MAX = "args.key.max";

    public static interface OnDateSetListener {
        public void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof OnDateSetListener)) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = 0;
        int month = 0;
        int day = 0;
        long max = 0;
        long min = 0;

        Bundle args = getArguments();
        if (args != null) {
            year = (int) args.get(ARGS_KEY_YEAR);
            month = (int) args.get(ARGS_KEY_MONTH);
            day = (int) args.get(ARGS_KEY_DAY);
            min = (long) args.get(ARGS_KEY_MIN);
            max = (long) args.get(ARGS_KEY_MAX);
        }

        if (year == 0 && month == 0 && day == 0) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        if (min != 0)
            datePickerDialog.getDatePicker().setMinDate(min);

        if (max != 0)
            datePickerDialog.getDatePicker().setMinDate(max);

        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String tag = null;

        Bundle args = getArguments();
        if (args != null) {
            tag = (String) args.get(ARGS_KEY_TAG);
        }

        ((OnDateSetListener) getActivity()).onDateSet(tag, year, monthOfYear, dayOfMonth);
    }
}

