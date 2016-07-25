package com.ir.android.incidents;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.incidents.list.IncidentListFragment;
import com.ir.android.incidents.map.IncidentMapFragment;

public class IncidentScreen extends Fragment {

//    private Switch mapListSwitch;

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

        setHasOptionsMenu(true);
//        mapListSwitch = (Switch) getView().findViewById(R.id.toggleBtnSwitch);
//        mapListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    IncidentMapFragment incidentMapFragment = new IncidentMapFragment();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, incidentMapFragment).commit();
//                } else {
//                    IncidentListFragment incidentListFragment = new IncidentListFragment();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, incidentListFragment).commit();
//
//                }
//            }
//        });
//        mapListSwitch.setChecked(true);
    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_incidents, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_map_list);
        setFragment(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_map_list) {
            item.setChecked(!item.isChecked());

            setFragment(item);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(MenuItem item) {
        if (item.isChecked()) {
            item.setIcon(R.mipmap.map_selected_toggle);
            IncidentMapFragment incidentMapFragment = new IncidentMapFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, incidentMapFragment).commit();
        } else {
            item.setIcon(R.mipmap.list_selected_toggle);
            IncidentListFragment incidentListFragment = new IncidentListFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, incidentListFragment).commit();
        }
    }
}