package com.ir.android.ActiveCall;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;

/**
 * Created by bassam on 09-07-2016.
 */
public class ActiveCallCtrl extends Controller {
    @Override
    public ViewModel initViewModel() {
        return new ActiveCallViewModel();
    }
}
