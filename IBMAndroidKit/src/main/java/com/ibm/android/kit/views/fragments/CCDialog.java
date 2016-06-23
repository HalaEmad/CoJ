package com.ibm.android.kit.views.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CCDialog extends DialogFragment {

	public static CCDialog newInstance() {
		CCDialog d = new CCDialog();
		return d;
	}

	private EditText editText;

	public static class DialogParams {

		public String tag;
		public String title;
		public String message;
		public String positive;
		public String negative;
		public OnClickListener negativeListener;
		public OnClickListener positiveListener;
	}

	private DialogParams params;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			params = new DialogParams();
			params.tag = savedInstanceState.getString("tag");
			params.title = savedInstanceState.getString("title");
			params.message = savedInstanceState.getString("message");
			params.negative = savedInstanceState.getString("negative");
			params.positive = savedInstanceState.getString("positive");
		}

		Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(params.title);
		dialogBuilder.setMessage(params.message);
		if (editText != null) {
			dialogBuilder.setView(editText);
		}

		dialogBuilder.setPositiveButton(params.positive, params.positiveListener);

		if (params.negative != null)
			dialogBuilder.setNegativeButton(params.negative, params.negativeListener);

		return dialogBuilder.create();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		editText = new EditText(getActivity());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		outState.putString("tag", params.tag);
		outState.putString("title", params.title);
		outState.putString("message", params.message);
		outState.putString("negative", params.negative);
		outState.putString("positive", params.positive);
	}

	public void setParams(DialogParams params) {
		this.params = params;
	}

	public void showDialog(Activity activity, String tag, String title, String msg, String pos,
			OnClickListener posListener, String neg, OnClickListener negListener) {

		CCDialog dialog = new CCDialog();
		DialogParams params = new DialogParams();
		params.tag = tag;
		params.title = title;
		params.message = msg;
		params.positive = pos;
		params.positiveListener = posListener;
		if (neg != null) {
			params.negative = neg;
			params.negativeListener = negListener;
		}
		dialog.setParams(params);
		dialog.show(activity.getFragmentManager(), params.tag);
		dialog.setCancelable(false);
	}

	public void hideDialog(Activity activity, String tag) {

		CCDialog dialog = (CCDialog) activity.getFragmentManager().findFragmentByTag(tag);

		if (dialog != null && dialog.isAdded()) {
			dialog.dismiss();
		}

	}
}
