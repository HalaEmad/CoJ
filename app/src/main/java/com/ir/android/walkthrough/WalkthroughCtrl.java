package com.ir.android.walkthrough;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;

/**
 * Created by bassam on 09-07-2016.
 */
public class WalkthroughCtrl extends Controller {

    @Override
    public ViewModel initViewModel() {
        return new WalkthroughViewModel();
    }
}
