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
                    incident.setTypeName(feature.getProperties().getCallType());

                    incident.setDistance(feature.getProperties().getAddress());
                    incident.setStatus(feature.getProperties().getStatus());
                    String severityLvl = feature.getProperties().getSeverity();
                    if (!GeneralUtility.isEmptyString(severityLvl))
                        incident.setSeverityLevel(context.getString(R.string.severity_level_label) + " " + severityLvl);
                    incident.setProperties(feature.getProperties().getDynamicProperties());
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
                    incident.setTypeName(feature.getProperties().getCallType());

                    incident.setDistance(feature.getProperties().getAddress());
                    incident.setStatus(feature.getProperties().getStatus());
                    String severityLvl = feature.getProperties().getSeverity();
                    if (!GeneralUtility.isEmptyString(severityLvl))
                        incident.setSeverityLevel(context.getString(R.string.severity_level_label) + " "+ severityLvl);
                    incident.setProperties(feature.getProperties().getDynamicProperties());
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
                    incident.setTypeName(feature.getProperties().getCallType());

                    incident.setDistance(feature.getProperties().getAddress());
                    incident.setProperties(feature.getProperties().getDynamicProperties());
                    incidents.add(incident);

                }
            }

        } catch (PoliceOfficersRetrievingFailedException e) {
            e.printStackTrace();
        }



        ///////////////

        return new Result(0, incidents);
    }


}
