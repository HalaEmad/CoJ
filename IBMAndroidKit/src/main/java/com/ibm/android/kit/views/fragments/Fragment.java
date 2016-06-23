/**
 * 
 */
package com.ibm.android.kit.views.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.controllers.IViewListener;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;

import java.util.Observable;
import java.util.Observer;

/**
 * @author bassam
 *
 */
public abstract class Fragment extends android.support.v4.app.Fragment implements Observer {

	protected Controller controller;

	protected IViewListener viewListener;

	protected abstract Controller getController();

	protected abstract String getControllerTag();

	protected abstract int getLayoutId();

	protected abstract void initViews();

	protected abstract void bindViews(ViewModel viewInfo);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getFragmentManager();
		controller = (Controller) fm.findFragmentByTag(getControllerTag());

		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (controller == null) {

			controller = getController();
			fm.beginTransaction().add(controller, getControllerTag()).commit();
			controller.setActivity((Activity) getActivity());
			setViewListener(controller);
			controller.init(getActivity().getIntent());

		} else {

			controller.setActivity((Activity) getActivity());
			setViewListener(controller);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getLayoutId(), container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();

		bindViews(controller.getViewModel());
	}

	protected void setViewListener(IViewListener listener) {
		this.viewListener = listener;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		controller.destroy();
	}

	@Override
	public void onStart() {
		super.onStart();
		controller.registerForChanges(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		controller.unregisterForChanges(this);
	}

	@Override
	public void update(final Observable observable, Object data) {

		if (observable instanceof ViewModel) {

			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {

					bindViews((ViewModel) observable);
				}
			});
		}
	}

}
