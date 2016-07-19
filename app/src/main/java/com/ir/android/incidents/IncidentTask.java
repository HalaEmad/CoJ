package com.ir.android.incidents;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ir.android.R;
import com.ir.android.model.Assault;
import com.ir.android.model.Incident;
import com.ir.android.model.IncidentM;
import com.ir.android.model.Officer;
import com.ir.android.networking.IncidentRetrievingResource.IncidentRetrievingFailedException;
import com.ir.android.networking.IncidentRetrievingResource.IncidentRetrievingResource;

import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class IncidentTask extends Task {

    public IncidentTask(ITask listener, Context context) {
        super(listener, context);
    }

    @Override
    protected Result onTaskWork() {

        try {
            IncidentRetrievingResource resource = new IncidentRetrievingResource(context);
            resource.invoke();
        }catch (IncidentRetrievingFailedException e){
            e.printStackTrace();
        }

    ArrayList<Incident> incidentArrayList = new ArrayList<Incident>();

        Incident in1 = new Assault();

        in1.setType(Incident.TYPE_ASSUALT);
        in1.setDistance(200);
        in1.setLatitude(30.0444);
        in1.setLongitude(31.2357);
        ((Assault)in1).setStatus("Pending dispatch");
        ((Assault)in1).setSeverityLevel("Severity - Level 2");
        ((Assault)in1).setSeverityLvlDesc("Major Injures");

        ((Assault)in1).setWeaponDescription("Suspect with knife");


        incidentArrayList.add((Assault)in1);

        Incident in2= new Assault();

        in2.setType(Incident.TYPE_ASSUALT);
        in2.setDistance(100);
        in2.setLatitude(31.205753);
        in2.setLongitude(29.924526);
        ((Assault)in2).setStatus("Pending dispatch");
        ((Assault)in2).setSeverityLevel("Severity - Level 1");
        ((Assault)in2).setSeverityLvlDesc("Major Injures");
        ((Assault)in2).setWeaponDescription("Suspect with knife");

        incidentArrayList.add((Assault)in2);

        Incident in3 = new Officer();
        in3.setType(Incident.TYPE_OFFICER);
        in3.setLongitude(32.89942);
        in3.setLatitude(24.09082);

        ((Officer)in3).setName("Name Surname");
        ((Officer)in3).setRank("Constable");
        ((Officer)in3).setSpeciality("Speciality Crowd Control");
        ((Officer)in3).setUnit("Food Patrol");
        incidentArrayList.add((Officer)in3);

        Incident in4 = new Officer();
        in4.setType(Incident.TYPE_OFFICER);
        in4.setLatitude(29.203171);
        in4.setLongitude(25.519545);

        ((Officer)in4).setName("Name Surname");
        ((Officer)in4).setRank("W/O");
        ((Officer)in4).setSpeciality("Speciality Crowd Control");
        ((Officer)in4).setUnit("Food Patrol");
        incidentArrayList.add((Officer)in4);

        ///////////////



        ArrayList<IncidentM> incidents = new ArrayList<IncidentM>();

        IncidentM i1 = new IncidentM(30.0444, 31.2357, "Cairo");
        i1.setCategory("Criminal");
        i1.setDrawableId(R.mipmap.assault);
        i1.setSecurityLevel("Major injures");
        i1.setStatus("Pending Dispatch");
        i1.setType("Assault");
        i1.setWeapons("Suspect with knife");
        incidents.add(i1);


        IncidentM i2 = new IncidentM(31.205753, 29.924526, "Alexandria");
        i2.setCategory("Criminal");
        i2.setDrawableId(R.mipmap.foot_patrol_officer);
        i2.setSecurityLevel("Major injures");
        i2.setStatus("Name Surname");
        i2.setType("JMPD OFFICCER");


        incidents.add(i2);


        IncidentM i3 = new IncidentM(24.09082, 32.89942, "Aswan");
        i3.setCategory("Criminal");
        i3.setDrawableId(R.mipmap.hospital);
        i3.setSecurityLevel("Major injures");
        i3.setStatus("Pending Dispatch");
        i3.setType("Hospital");
        i3.setWeapons("Suspect with knife");
        incidents.add(i3);



        IncidentM i4 = new IncidentM(29.203171, 25.519545, "Siwa");
        i4.setCategory("Criminal");
        i4.setDrawableId(R.mipmap.fire_incident);
        i4.setSecurityLevel("Major injures");
        i4.setStatus("Pending Dispatch");
        i4.setType("Fire IncidentM");
        i4.setWeapons("Suspect with knife");
        incidents.add(i4);

        //TODO load locations from database or from server
        return new Result(0, incidents);
    }
}
