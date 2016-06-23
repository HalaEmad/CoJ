/**
 * 
 */
package com.ibm.android.kit.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ibm.android.kit.R;

/**
 * @author bahamada
 */
public class ProgressDialogFragment extends DialogFragment {
	public static ProgressDialogFragment newInstance() {
		ProgressDialogFragment frag = new ProgressDialogFragment();
		frag.setCancelable(false);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(getString(R.string.please_wait));
		dialog.setIndeterminate(true);

		// Disable the back button
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}

		};

		dialog.setOnKeyListener(keyListener);
		return dialog;
	}
}
