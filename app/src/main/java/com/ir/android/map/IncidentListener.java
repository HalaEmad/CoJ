package com.ir.android.map;

import com.ir.android.model.Incident;

/**
 * This interface include function to be called when user clicked on one incident or marker on map screen
 * So Map controller should handle this action and return back to UI
 * Created by emanhassan on 6/23/16.
 */
public interface IncidentListener {
    void onMarkerClicked (Incident clickedIncident);
    void onMarkerClicked (int position);

}
