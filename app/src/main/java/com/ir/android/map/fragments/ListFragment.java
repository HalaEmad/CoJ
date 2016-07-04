package com.ir.android.map.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.map.IncidentListener;
import com.ir.android.model.Incident;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class ListFragment extends Fragment implements  ItemListener
{

    private ListView listView;

    @Override
    protected Controller createController() {
        return new IncidentFrgmtCtrl();
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
        ArrayList<Incident> incidents =  ((FragmentViewModel)controller.getViewModel()).getIncidentsLocs();
        listView.setAdapter(new IncidentAdapter(getContext(), incidents,this));

    }

    @Override
    protected void bindViews(ViewModel viewInfo) {
        ((IncidentAdapter)listView.getAdapter()).notifyDataSetChanged();
    }



    @Override
    public void onItemClickListener(int position) {
        ((IncidentListener)controller).onMarkerClicked(position);
    }
}
