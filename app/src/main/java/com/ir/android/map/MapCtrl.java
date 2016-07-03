package com.ir.android.map;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;

import android.widget.Toast;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;

import com.ibm.android.kit.utils.LocationUtility;
import com.ir.android.model.Incident;
import com.ir.android.service.LocationService;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class MapCtrl extends Controller implements IncidentListener {

    private static final String LOC_DLG = "loc.dlg";
    private ArrayList<Incident> mIncidentsLocs;
    private double mLatitude;
    private double mLongitude;
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public ViewModel initViewModel() {
        return new MapViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);
//        boolean hasAccessLocPerm = PackageManager.PERMISSION_GRANTED == getScreen().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (hasAccessLocPerm)
//        {
        if (!LocationUtility.isProviderEnabled(getScreen(), LocationManager.NETWORK_PROVIDER))
            showOkCancelDialog(LOC_DLG, "Warning", "GPS is disabled, would you like to enable it?");

        LocationService.start(getScreen());
//        }
//        else {
//
//            ActivityCompat.requestPermissions(getScreen(),
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//
//        }
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

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    LocationService.start(getScreen());
//                }
//                return;
//            }
//
//
//        }
//    }

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
        ((MapViewModel) viewModel).setIncidentsLocs(mIncidentsLocs);
    }


    @Override
    public void onMarkerClicked(Incident clickedIncident) {
        // TODO SHOW DIALOG BASED ON CLICKED MARKER INFO
        showOkDialog("Info", "Marker info", clickedIncident.getInfo()
        );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        LocationService.stop(getScreen());
    }
}
