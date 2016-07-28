package com.ibm.android.kit.views.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.android.kit.R;
//import com.ir.android.R;

public class AkDialog extends DialogFragment {

	public static class DialogParams {

		public String tag;
		public String title;
		public String message;
		public String positive;
		public String negative;
	}

	public interface DialogListener {
		void onDialogPositive(DialogInterface dialog, String tag);

		void onDialogNegative(DialogInterface dialog, String tag);
	}

	private DialogParams params;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof DialogListener)) {
			throw new ClassCastException(activity.toString() + " must implement DialogListener");
		}
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

		final Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//		dialogBuilder.setTitle(params.title);
//		dialogBuilder.setMessage(params.message);
//		dialogBuilder.setPositiveButton(params.positive, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				((DialogListener) getActivity()).onDialogPositive(dialog, params.tag);
//			}
//		});
//
//		if (params.negative != null) {
//			dialogBuilder.setNegativeButton(params.negative, new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					((DialogListener) getActivity()).onDialogNegative(dialog, params.tag);
//				}
//			});
//		}
//		dialogBuilder.setView(R.layout.error_dialog);
		LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
//		builder.setTitle("Sifiso");
		View v = inflater.inflate(R.layout.error_dialog, null);

		TextView DialogTitle = (TextView) v.findViewById(R.id.dialogTitle);
		TextView Message = (TextView) v.findViewById(R.id.error_message);
		Button OkayButton = (Button) v.findViewById(R.id.okay_button);

		DialogTitle.setText(params.title);
		Message.setText(params.message);
		OkayButton.setText(params.positive);

		OkayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		dialogBuilder.setView(v);
//		builder.setIcon()
//		builder.setPositiveButton(android.R.string.ok, null);
//		builder.setNegativeButton(android.R.string.cancel, null);

		return dialogBuilder.create();
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

	public static void showDialog(Activity activity, String tag, String title, String msg, String pos, String neg) {

		AkDialog dialog = new AkDialog();
		DialogParams params = new DialogParams();
		params.tag = tag;
		params.title = title;
		params.message = msg;
		params.negative = neg;
		params.positive = pos;

		dialog.setParams(params);
		dialog.show(activity.getFragmentManager(), params.tag);
		dialog.setCancelable(false);
	}

	public static void hideDialog(Activity activity, String tag) {

		AkDialog dialog = (AkDialog) activity.getFragmentManager().findFragmentByTag(tag);

		if (dialog != null && dialog.isAdded()) {
			dialog.dismiss();
		}

	}
}
