package com.ir.android.incidents;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ibm.android.kit.utils.LocationUtility;
import com.ir.android.R;
import com.ir.android.incidents.map.IncidentMapListener;
import com.ir.android.incidents.popup.AssaultFragment;
import com.ir.android.incidents.popup.OfficerFragment;
import com.ir.android.model.Assault;
import com.ir.android.model.Incident;
import com.ir.android.model.Officer;
import com.ir.android.service.LocationService;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class FragmentCtrl extends Controller implements IncidentMapListener {


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
//            getUserInputToEnableGPS();
           showOkCancelDialog(LOC_DLG, "Warning", "GPS is disabled, would you like to enable it?");

        LocationService.start(getScreen());


        IncidentTask task = new IncidentTask(this, activity);
        task.execute();
    }

    @Override
    public void onDialogPositive(DialogInterface dialog, String tag) {
        Log.i("log", "in dialog positive");
        super.onDialogPositive(dialog, tag);

        if (tag.equals(LOC_DLG)) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }
    }



    private void getUserInputToEnableGPS() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getScreen());
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Settings.ACTION_LOCATION_SOURCE_SETTINGS == Activity Action: Show settings to allow configuration of current location sources.
                // The Settings provider contains global system-level device preferences.
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                //Every Activity is invoked by an Intent. therefore, we are going to start activity which allow to set location
                getContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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

//        Dialog dialog = new Dialog(getContext());
//
//        if (clickedIncident instanceof Assault)
//        {
//            dialog.setContentView(R.layout.popup_dialog_assault);
//            ((ImageView) dialog.findViewById(R.id.type_icon)).setImageResource(clickedIncident.getDrawableId());
//            ((TextView) dialog.findViewById(R.id.type)).setText(getContext().getString(R.string.assualt_label));
//            ((TextView) dialog.findViewById(R.id.status)).setText(((Assault) clickedIncident).getStatus());
//            ((TextView) dialog.findViewById(R.id.distance)).setText(((Assault) clickedIncident).getDistance());
//
//            ((TextView) dialog.findViewById(R.id.severity_level)).setText(((Assault) clickedIncident).getSeverityLevel());
//            ((TextView) dialog.findViewById(R.id.severity_desc)).setText(((Assault) clickedIncident).getSeverityLvlDesc());
//
//            ((ImageView) dialog.findViewById(R.id.weapon_icon)).setImageResource(((Assault) clickedIncident).getWeaponDrawableId());
//            ((TextView) dialog.findViewById(R.id.weapon_desc)).setText(((Assault) clickedIncident).getWeaponDescription());
//
//
//        }
//        else if (clickedIncident instanceof Officer){
//            dialog.setContentView(R.layout.popup_dialog_officer);
//            ((ImageView) dialog.findViewById(R.id.type_icon)).setImageResource(clickedIncident.getDrawableId());
//            ((TextView) dialog.findViewById(R.id.type)).setText(getContext().getString(R.string.officer_label));
//
//
//            ((TextView) dialog.findViewById(R.id.name)).setText(((Officer) clickedIncident).getName());
//            ((TextView) dialog.findViewById(R.id.distance)).setText(((Officer) clickedIncident).getDistance());
//
//            ((TextView) dialog.findViewById(R.id.unit)).setText(((Officer) clickedIncident).getUnit());
//            ((TextView) dialog.findViewById(R.id.speciality)).setText(((Officer) clickedIncident).getSpeciality());
//            ((TextView) dialog.findViewById(R.id.rank)).setText(((Officer) clickedIncident).getRank());
//
//        }
//        dialog.show();

        Fragment popup = null;

        if (clickedIncident instanceof Assault) {
            popup = new AssaultFragment();
            ((AssaultFragment) popup).setViewModel((Assault) clickedIncident);
        } else if (clickedIncident instanceof Officer) {
            popup = new OfficerFragment();
            ((OfficerFragment) popup).setData((Officer) clickedIncident);
        }


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_popup_container, popup)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onMarkerClicked(int position) {

        onMarkerClicked(mIncidentsLocs.get(position));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocationService.stop(getScreen());
    }

}
