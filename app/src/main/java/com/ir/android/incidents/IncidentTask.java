package com.ir.android.incidents;

import android.content.Context;

import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.tasks.ITask;
import com.ibm.android.kit.tasks.Task;
import com.ibm.android.kit.utils.GeneralUtility;
import com.ir.android.R;
import com.ir.android.model.Assault;
import com.ir.android.model.Incident;
import com.ir.android.model.Officer;
import com.ir.android.networking.FeatureModels.Feature;
import com.ir.android.networking.FeatureModels.Geometry;
import com.ir.android.networking.IncidentRetrievingResource.IncidentRetrievingFailedException;
import com.ir.android.networking.IncidentRetrievingResource.IncidentRetrievingResource;
import com.ir.android.networking.ObservationsRetrievingResource.ObservationsRetrievingFailedException;
import com.ir.android.networking.ObservationsRetrievingResource.ObservationsRetrievingResource;
import com.ir.android.networking.PoliceOfficersRetrievingResource.PoliceOfficersRetrievingFailedException;
import com.ir.android.networking.PoliceOfficersRetrievingResource.PoliceOfficersRetrievingResource;

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

        ArrayList<Incident> incidents = new ArrayList<>();

        try {
            IncidentRetrievingResource resource = new IncidentRetrievingResource(context);
            resource.invoke();
            ArrayList<Feature> features = resource.getFeatures();

            if (features != null) {
                for (Feature feature : features) {
                    Assault incident = new Assault();

                    // Setting coordinates
                    Geometry geometry = feature.getGeometry();
                    if (geometry != null) {
                        if (geometry.getCoordinates() != null && geometry.getCoordinates().size() >= 2) {
                            incident.setLongitude(geometry.getCoordinates().get(0));
                            incident.setLatitude(geometry.getCoordinates().get(1));

                        }
                    }
                    incident.setTypeName(feature.getType());

                    incident.setDistance(feature.getProperties().getAddress());
                    incident.setStatus(feature.getProperties().getStatus());
                    String severityLvl = feature.getProperties().getSeverity();
                    if (!GeneralUtility.isEmptyString(severityLvl))
                        incident.setSeverityLevel(context.getString(R.string.severity_level_label) + " " + severityLvl);
                    incidents.add(incident);

                }
            }

        } catch (IncidentRetrievingFailedException e) {
            e.printStackTrace();
        }


        try {
            ObservationsRetrievingResource obsResourse = new ObservationsRetrievingResource(context);
            obsResourse.invoke();
            ArrayList<Feature> features = obsResourse.getFeatures();

            if (features != null) {
                for (Feature feature : features) {
                    Assault incident = new Assault();

                    // Setting coordinates
                    Geometry geometry = feature.getGeometry();
                    if (geometry != null) {
                        if (geometry.getCoordinates() != null && geometry.getCoordinates().size() >= 2) {
                            incident.setLongitude(geometry.getCoordinates().get(0));
                            incident.setLatitude(geometry.getCoordinates().get(1));

                        }
                    }
                    incident.setTypeName(feature.getType());

                    incident.setDistance(feature.getProperties().getAddress());
                    incident.setStatus(feature.getProperties().getStatus());
                    String severityLvl = feature.getProperties().getSeverity();
                    if (!GeneralUtility.isEmptyString(severityLvl))
                        incident.setSeverityLevel(context.getString(R.string.severity_level_label) + " "+ severityLvl);

                    incidents.add(incident);

                }
            }

        } catch (ObservationsRetrievingFailedException e) {
            e.printStackTrace();
        }


        try {
            PoliceOfficersRetrievingResource policeResourse = new PoliceOfficersRetrievingResource(context);
            policeResourse.invoke();
            ArrayList<Feature> features = policeResourse.getFeatures();

            if (features != null) {
                for (Feature feature : features) {
                    Officer incident = new Officer();

                    // Setting coordinates
                    Geometry geometry = feature.getGeometry();
                    if (geometry != null) {
                        if (geometry.getCoordinates() != null && geometry.getCoordinates().size() >= 2) {
                            incident.setLongitude(geometry.getCoordinates().get(0));
                            incident.setLatitude(geometry.getCoordinates().get(1));

                        }
                    }
                    incident.setTypeName(feature.getType());

                    incident.setDistance(feature.getProperties().getAddress());

                    incidents.add(incident);

                }
            }

        } catch (PoliceOfficersRetrievingFailedException e) {
            e.printStackTrace();
        }


        ArrayList<Incident> incidentArrayList = new ArrayList<Incident>();

        Incident in1 = new Assault();

        in1.setType(Incident.TYPE_ASSUALT);
        in1.setDistance("200");
        in1.setLatitude(-26.206852);
        in1.setLongitude(28.0442333);
        ((Assault) in1).setStatus("Pending dispatch");
        ((Assault) in1).setSeverityLevel("Severity - Level 2");
        ((Assault) in1).setSeverityLvlDesc("Major Injures");

        ((Assault) in1).setWeaponDescription("Suspect with knife");


        incidentArrayList.add((Assault) in1);

        Incident in2 = new Assault();


        in2.setType(Incident.TYPE_ASSUALT);
        in2.setDistance("100");
        in2.setLatitude(-26.20671);
        in2.setLongitude(28.0442333);
        ((Assault) in2).setStatus("Pending dispatch");
        ((Assault) in2).setSeverityLevel("Severity - Level 1");
        ((Assault) in2).setSeverityLvlDesc("Major Injures");
        ((Assault) in2).setWeaponDescription("Suspect with knife");

        incidentArrayList.add((Assault) in2);

        Incident in3 = new Officer();
        in3.setType(Incident.TYPE_OFFICER);
        in3.setDistance("100");
        in3.setLatitude(-26.206142);
        in3.setLongitude(28.0414706);

        ((Officer) in3).setName("Name Surname");
        ((Officer) in3).setRank("Constable");
        ((Officer) in3).setSpeciality("Speciality Crowd Control");
        ((Officer) in3).setUnit("Food Patrol");
        incidentArrayList.add((Officer) in3);


        Incident in4 = new Officer();
        in4.setType(Incident.TYPE_OFFICER);
        in3.setDistance("100");
        in4.setLatitude(-26.2051699);
        in4.setLongitude(28.0451453);

        ((Officer) in4).setName("Name Surname");
        ((Officer) in4).setRank("W/O");
        ((Officer) in4).setSpeciality("Speciality Crowd Control");
        ((Officer) in4).setUnit("Food Patrol");
        incidentArrayList.add((Officer) in4);

        ///////////////

        return new Result(0, incidents);
    }


}
