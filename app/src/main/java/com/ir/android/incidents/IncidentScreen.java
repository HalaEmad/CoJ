package com.ir.android.incidents;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.incidents.list.IncidentListFragment;
import com.ir.android.incidents.map.IncidentMapFragment;
import com.ir.android.main.MainScreen;

public class IncidentScreen extends Fragment {

    private boolean isLoaded;
    private Switch mapListSwitch;
    private TextView actionBarTitle;

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

        // ste custom action
        ActionBar actionBar = ((MainScreen) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.incident_action_bar);

        // set action bar title
        actionBarTitle = (TextView) getActivity().findViewById(R.id.action_bar_title);
        actionBarTitle.setText(getResources().getString(R.string.incident_title));

        if (!isLoaded) {
            // set action bar map/list switch button
            mapListSwitch = (Switch) getActivity().findViewById(R.id.action_bar_map_list_switch);
            setFragment(mapListSwitch.isChecked());
            mapListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setFragment(isChecked);
                }
            });
            isLoaded = true;
        }

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
        Log.i("MAPS", "onPrepareOptionsMenu");
        super.onPrepareOptionsMenu(menu);
        if (!isLoaded) {
            MenuItem item = menu.findItem(R.id.action_map_list);
            setFragment(item);
        }
        isLoaded = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("MAPS", "in optionsitem selected");
        if (item.getItemId() == R.id.action_map_list) {
            item.setChecked(!item.isChecked());

            setFragment(item);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(MenuItem item) {

        clearFragmentBackStack();

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

    private void setFragment(boolean mapChecked) {

        clearFragmentBackStack();

        if (mapChecked) {
            IncidentMapFragment incidentMapFragment = new IncidentMapFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, incidentMapFragment).commit();
        } else {
            IncidentListFragment incidentListFragment = new IncidentListFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, incidentListFragment).commit();
        }
    }

    private boolean clearFragmentBackStack() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }

        return false;
    }
}