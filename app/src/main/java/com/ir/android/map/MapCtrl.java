package com.ir.android.map;

import android.content.Intent;
import android.widget.Toast;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.model.Incident;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class MapCtrl extends Controller implements IncidentListener {

    private ArrayList<Incident> mIncidentsLocs;
    private double mLatitude;
    private double mLongitude;

    @Override
    public ViewModel initViewModel() {
        return new MapViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);
        MapTask task = new MapTask(this, getContext()
        );
        task.execute();
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
        ((MapViewModel) viewModel).setIncidentsLocs(mIncidentsLocs);
    }


    @Override
    public void onMarkerClicked(Incident clickedIncident) {
        // TODO SHOW DIALOG BASED ON CLICKED MARKER INFO
        showOkDialog("Info","Marker info", clickedIncident.getInfo()
        );
//        showToast("test test");
    }
}
