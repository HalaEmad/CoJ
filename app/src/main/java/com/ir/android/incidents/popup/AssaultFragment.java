package com.ir.android.incidents.popup;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;

/**
 * Created by bassam on 25-07-2016.
 */
public class AssaultFragment extends Fragment {

    @Override
    protected Controller createController() {
        return new AssaultCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.popup.assault";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_dialog_assault;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindViews(ViewModel viewInfo) {

    }
}
