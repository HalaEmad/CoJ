package com.ibm.android.kit.views;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.controllers.IViewListener;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.ProgressDialogFragment;

/**
 * Created by bassam on 20-07-2016.
 */
public interface IView {

    public abstract Controller createController();

    public abstract String getControllerTag();

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void bindViews(ViewModel viewModel);
}
