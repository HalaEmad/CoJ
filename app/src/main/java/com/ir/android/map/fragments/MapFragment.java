package com.ir.android.map.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPoint;
import com.esri.core.geometry.Point;
import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.identify.IdentifyParameters;
import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.views.fragments.Fragment;
import com.ir.android.R;
import com.ir.android.map.IncidentListener;
import com.ir.android.map.MapViewModel;
import com.ir.android.model.Incident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emanhassan on 7/4/16.
 */
public class MapFragment extends Fragment implements OnSingleTapListener {

    private MapView mapView;
    private ArrayList<Incident> incidentsLocs;

    private final IdentifyParameters params = new IdentifyParameters();
    private GraphicsLayer graphicsLayer = new GraphicsLayer();
    private final static String ATTR_INDEX = "INDEX";

    @Override
    protected Controller createController() {
        return new IncidentFrgmtCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "incident.fragment.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.incident_map_frgmt;
    }


    @Override
    protected void initViews() {
        mapView = (MapView) getView().findViewById(R.id.map);
        mapView.setOnSingleTapListener(this);

    }

    @Override
    protected void bindViews(ViewModel viewModel) {
        //Create the layer
        final Layer oneMapLayer = new ArcGISTiledMapServiceLayer("http://e1.onemap.sg/arcgis/rest/services/BASEMAP/MapServer");
        incidentsLocs =

                ((FragmentViewModel) viewModel).getIncidentsLocs();

        final Context context = getContext();
        //Know when the map layer loaded
        mapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;


            public void onStatusChanged(Object source, STATUS status) {
                if (status == STATUS.LAYER_LOADED) {
                    if (incidentsLocs != null) {

                        MultiPoint multipoint = new MultiPoint();
                        for (int i = 0; i < incidentsLocs.size(); i++) {
                            Incident incident = incidentsLocs.get(i);
//                            incident.getDrawableId()
                            BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(context,R.mipmap.your_location );
                            pinStarBlueDrawable.setBounds(5, 5, 5, 5);
                            Bitmap b = pinStarBlueDrawable.getBitmap();
                            Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 36, 36  , false);

                            PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(new BitmapDrawable(getResources(), bitmapResized));

                            Point point = GeometryEngine.project(incident.getLongitude(), incident.getLatitude(), mapView.getSpatialReference());
                            multipoint.add(point);
                            Map<String, Object> attributes = new HashMap<String, Object>();
                            attributes.put(ATTR_INDEX, i);

                            Graphic g = new Graphic(point, new SimpleMarkerSymbol(Color.RED, 10, SimpleMarkerSymbol.STYLE.CIRCLE), attributes);
//                            Graphic g = new Graphic(point, pinStarBlueSymbol);
                            Log.i("log", incident.getInfo());

                            graphicsLayer.addGraphic(g);

                            mapView.addLayer(graphicsLayer);
                        }
                        mapView.setExtent(multipoint, 100, true);

                    }
                }
            }

        });
        mapView.addLayer(oneMapLayer);

    }


    @Override
    public void onSingleTap(float x, float y) {

        Point identifyPoint = mapView.toMapPoint(x, y);

        params.setGeometry(identifyPoint);
        params.setSpatialReference(mapView.getSpatialReference());
        params.setMapHeight(mapView.getHeight());
        params.setMapWidth(mapView.getWidth());

        // add the area of extent to identify parameters
        Envelope env = new Envelope();
        mapView.getExtent().queryEnvelope(env);
        params.setMapExtent(env);

        Feature result = null;
        Layer[] layers = mapView.getLayers();
        Log.i("ir", "layers: " + layers.length);

        for (int i = 0; i < layers.length; i++) {
            Layer layer = layers[i];
            if (layer instanceof GraphicsLayer) {

                GraphicsLayer graphicsLayer = (GraphicsLayer) layer;
                int[] graphicIds = graphicsLayer.getGraphicIDs(x, y, 20);

                if (0 < graphicIds.length) {
                    result = graphicsLayer.getGraphic(graphicIds[0]);
                    break;
                }
            }
        }

        if (null != result) {
            Object id = result.getAttributeValue(ATTR_INDEX);
            if (null != id) {
                if (incidentsLocs != null && incidentsLocs.size() > ((int) id)) {
                    Incident selectedIncident = incidentsLocs.get((int) id);
                    ((IncidentListener) controller).onMarkerClicked(selectedIncident);
                }
            }

        }
    }

}
