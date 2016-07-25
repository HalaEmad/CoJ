package com.ir.android.incidents.list;

import android.widget.ListView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.incidents.FragmentCtrl;
import com.ir.android.incidents.FragmentViewModel;
import com.ir.android.incidents.map.IncidentMapListener;
import com.ir.android.model.Assault;
import com.ir.android.model.Incident;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class IncidentListFragment extends Fragment implements ItemListener {

    private ListView listView;

    @Override
    protected Controller createController() {
        return new FragmentCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "incident.fragment.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.incident_list_fragment;
    }

    @Override
    protected void initViews() {

        listView = (ListView) getView().findViewById(R.id.list);
        ArrayList<Incident> incidents = ((FragmentViewModel) controller.getViewModel()).getIncidentsLocs();
        ArrayList<Incident> assualtsIncidents = new ArrayList<Incident>();
        if (incidents != null) {
            for (Incident incident : incidents) {
                if (incident instanceof Assault) {
                    assualtsIncidents.add((Assault) incident);
                }
            }
        }
        listView.setAdapter(new IncidentListAdapter(getContext(), assualtsIncidents, this));

    }

    @Override
    protected void bindViews(ViewModel viewInfo) {
        ((IncidentListAdapter) listView.getAdapter()).notifyDataSetChanged();
    }


    @Override
    public void onItemClickListener(int position) {
        ((IncidentMapListener) controller).onMarkerClicked(position);
    }
}
