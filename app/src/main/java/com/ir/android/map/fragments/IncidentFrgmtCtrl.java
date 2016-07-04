package com.ir.android.map.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ibm.android.kit.utils.GeneralUtility;
import com.ibm.android.kit.utils.LocationUtility;
import com.ir.android.R;
import com.ir.android.map.IncidentListener;
import com.ir.android.map.MapTask;

import com.ir.android.model.Incident;
import com.ir.android.service.LocationService;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class IncidentFrgmtCtrl extends Controller implements IncidentListener {


    private static final String LOC_DLG = "loc.dlg";
    private ArrayList<Incident> mIncidentsLocs;
    private double mLatitude;
    private double mLongitude;
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    public ViewModel initViewModel() {
        return new FragmentViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);

        if (!LocationUtility.isProviderEnabled(getScreen(), LocationManager.NETWORK_PROVIDER))
            showOkCancelDialog(LOC_DLG, "Warning", "GPS is disabled, would you like to enable it?");

        LocationService.start(getScreen());

        MapTask task = new MapTask(this, getContext()
        );
        task.execute();
    }

    @Override
    public void onDialogPositive(DialogInterface dialog, String tag) {
        super.onDialogPositive(dialog, tag);

        if (tag.equals(LOC_DLG))
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }


    @Override
    public void onTaskStarted(Task task) {
        super.onTaskStarted(task);
        showProgress();
    }

    @Override
    public void onTaskFinished(Task task, Result result) {
        super.onTaskFinished(task, result);
        mIncidentsLocs = (ArrayList<Incident>) result.getData();
        dismissPorgress();
        ((FragmentViewModel) viewModel).setIncidentsLocs(mIncidentsLocs);
    }


    @Override
    public void onMarkerClicked(Incident clickedIncident) {
        // TODO SHOW DIALOG BASED ON CLICKED MARKER INFO
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_dialog);

        TextView type = (TextView) dialog.findViewById(R.id.firstTitle);
        type.setText(clickedIncident.getType());


        ((ImageView) dialog.findViewById(R.id.firstIcon)).setImageResource(clickedIncident.getDrawableId());
        TextView status = (TextView) dialog.findViewById(R.id.firstInfo);
        status.setText(clickedIncident.getStatus());

        TextView security = (TextView) dialog.findViewById(R.id.secondInfo);
        security.setText(clickedIncident.getSecurityLevel());

        LinearLayout weapons = (LinearLayout) dialog.findViewById(R.id.thirdLayout);

        if (GeneralUtility.isEmptyString(clickedIncident.getWeapons())) {
            weapons.setVisibility(View.GONE);
        } else {
            weapons.setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.thirdInfo)).setText(clickedIncident.getWeapons());
        }
        dialog.show();
    }

    @Override
    public void onMarkerClicked(int position) {

        // TODO SHOW DIALOG BASED ON CLICKED MARKER INFO
        onMarkerClicked(mIncidentsLocs.get(position));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocationService.stop(getScreen());
    }
}
