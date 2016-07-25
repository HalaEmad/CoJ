package com.ir.android.incidents.popup;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.model.Assault;

/**
 * Created by bassam on 25-07-2016.
 */
public class AssaultCtrl extends Controller {
    @Override
    public ViewModel initViewModel() {
        return new Assault();
    }

}
