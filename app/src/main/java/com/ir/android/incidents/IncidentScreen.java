package com.ir.android.incidents;

import android.widget.CompoundButton;
import android.widget.Switch;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.incidents.list.IncidentListFragment;
import com.ir.android.incidents.map.IncidentMapFragment;

public class IncidentScreen extends Fragment {

    private Switch mapListSwitch;

    @Override
    protected Controller createController() {
        return new IncidentCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "map.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.incident_activity;
    }


    @Override
    protected void initViews() {


        mapListSwitch = (Switch) getView().findViewById(R.id.toggleBtnSwitch);
        mapListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IncidentMapFragment incidentMapFragment = new IncidentMapFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, incidentMapFragment).commit();
                } else {
                    IncidentListFragment incidentListFragment = new IncidentListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, incidentListFragment).commit();

                }
            }
        });
        mapListSwitch.setChecked(true);
    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }


}