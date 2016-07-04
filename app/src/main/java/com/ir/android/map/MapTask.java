package com.ir.android.map;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.R;
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
        ArrayList<Incident> incidents = new ArrayList<Incident>();

        Incident i1 = new Incident(30.0444, 31.2357, "Cairo");
        i1.setCategory("Criminal");
        i1.setDrawableId(R.mipmap.assault);
        i1.setSecurityLevel("Major injures");
        i1.setStatus("Pending Dispatch");
        i1.setType("Assault");
        i1.setWeapons("Suspect with knife");
        incidents.add(i1);


        Incident i2 = new Incident(31.205753, 29.924526, "Alexandria");
        i2.setCategory("Criminal");
        i2.setDrawableId(R.mipmap.foot_patrol_officer);
        i2.setSecurityLevel("Major injures");
        i2.setStatus("Name Surname");
        i2.setType("JMPD OFFICCER");


        incidents.add(i2);


        Incident i3 = new Incident(24.09082, 32.89942, "Aswan");
        i3.setCategory("Criminal");
        i3.setDrawableId(R.mipmap.hospital);
        i3.setSecurityLevel("Major injures");
        i3.setStatus("Pending Dispatch");
        i3.setType("Hospital");
        i3.setWeapons("Suspect with knife");
        incidents.add(i3);



        Incident i4 = new Incident(29.203171, 25.519545, "Siwa");
        i4.setCategory("Criminal");
        i4.setDrawableId(R.mipmap.fire_incident);
        i4.setSecurityLevel("Major injures");
        i4.setStatus("Pending Dispatch");
        i4.setType("Fire Incident");
        i4.setWeapons("Suspect with knife");
        incidents.add(i4);


        //TODO load locations from database or from server
        return new Result(0, incidents);
    }
}
