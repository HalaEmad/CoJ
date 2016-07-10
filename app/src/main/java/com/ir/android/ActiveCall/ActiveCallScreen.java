package com.ir.android.ActiveCall;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;

/**
 * Created by bassam on 09-07-2016.
 */
public class ActiveCallScreen extends Activity {
    @Override
    protected Controller createController() {
        return new ActiveCallCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.active.call";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_active_call;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }
}
