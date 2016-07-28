package com.ibm.android.kit.views;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.controllers.IViewListener;
import com.ibm.android.kit.views.fragments.ProgressDialogFragment;

/**
 * Created by bassam on 20-07-2016.
 */
public abstract class BaseView {

    protected Controller controller;

    protected IViewListener viewListener;

    private ProgressDialogFragment mProgressDlg;
}
