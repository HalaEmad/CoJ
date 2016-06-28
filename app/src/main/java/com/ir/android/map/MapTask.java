package com.ir.android.map;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.model.Incident;

import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class MapTask extends Task {

    public MapTask(ITask listener, Context context) {
        super(listener, context);
    }

    @Override
    protected Result onTaskWork() {
        ArrayList<Incident> incidents = new ArrayList<>();
        incidents.add(new Incident(30.0444, 31.2357, "Cairo"));
        incidents.add(new Incident(31.205753, 29.924526, "Alexandria"));
        incidents.add(new Incident(24.09082, 32.89942, "Aswan"));
        incidents.add(new Incident(29.203171, 25.519545, "Siwa"));

        //TODO load locations from database or from server
        return new Result(0, incidents);
    }
}
