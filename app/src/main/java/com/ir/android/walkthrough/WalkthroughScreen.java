package com.ir.android.walkthrough;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;

/**
 * Created by bassam on 09-07-2016.
 */
public class WalkthroughScreen extends Activity {
    @Override
    protected Controller createController() {
        return new WalkthroughCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "walkthrough.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_walkthrough;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }
}
