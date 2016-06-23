package com.ir.android.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;
import com.ir.android.model.Incident;

import java.util.ArrayList;
import java.util.List;

public class MapsScreen extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Marker> incidentMarkers;

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        bindViews(controller.getViewModel());
    }

    @Override
    protected Controller getController() {
        return new MapCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "map.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    @Override
    protected void initViews() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void bindViews(ViewModel viewModel) {

        ArrayList<Incident> incidentsLocs =

                ((MapViewModel) viewModel).getIncidentsLocs();
        if (mMap != null && incidentsLocs != null) {
            incidentMarkers = new ArrayList<Marker>
                    ();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Incident incident : incidentsLocs) {
                LatLng latLng = new LatLng(incident.getLatitude(), incident.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));

                builder.include(latLng);

                marker.setTitle(incident.getInfo());
                incidentMarkers.add(marker);
            }

            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 40));
            // Used for finding current location with button
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }
}
