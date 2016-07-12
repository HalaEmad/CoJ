package com.ir.android.active_call;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;

/**
 * Created by bassam on 09-07-2016.
 */
public class ActiveCallScreen extends Fragment {
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
