package com.ir.android.walkthrough;

import android.content.Intent;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.storage.PreferencesManager;

/**
 * Created by bassam on 09-07-2016.
 */
public class WalkthroughCtrl extends Controller {

    @Override
    public ViewModel initViewModel() {
        return new WalkthroughViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);

        PreferencesManager.getInstance(getScreen()).setFirstRun(false);
    }
}
