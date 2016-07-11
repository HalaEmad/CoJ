package com.ir.android.splash;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;

/**
 * Created by bassam on 09-07-2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected Controller createController() {
        return new SplashCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "splash.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }
}
