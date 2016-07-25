package com.ir.android.incidents.popup;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;

/**
 * Created by bassam on 25-07-2016.
 */
public class OfficerFragment extends Fragment {
    @Override
    protected Controller createController() {
        return new OfficerCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.popup.officer";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_dialog_officer;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindViews(ViewModel viewInfo) {

    }
}
