package com.ir.android.incidents.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.views.adapters.AbstractViewHolder;
import com.ir.android.R;
import com.ir.android.model.Assault;
import com.ir.android.model.Incident;
import com.ir.android.networking.FeatureModels.DynamicProperty;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class IncidentListAdapter extends com.ibm.android.kit.views.adapters.ListAdapter {
    private ArrayList<Incident> incidents;
    private ItemListener listener;

    public IncidentListAdapter(Context context, ArrayList<Incident> incidents, ItemListener listener) {
        super(context);
        this.incidents = incidents;
        this.listener = listener;

    }

    @Override
    public int getCount() {
        return incidents.size();
    }

    @Override
    protected View inflateLayout(ViewGroup parent, final int position) {
        View v = layoutInflater.inflate(R.layout.incident_list_item, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position);
            }
        });
        return v;
    }

    @Override
    protected AbstractViewHolder initView(View convertView, int position) {
        IncidentViewHolder holder = new IncidentViewHolder();
        holder.incidentType = (TextView) convertView.findViewById(R.id.type);
        holder.incidentStatus = (TextView) convertView.findViewById(R.id.status);
        holder.incidentLoc = (TextView) convertView.findViewById(R.id.distance);
        holder.severityIcon = (ImageView) convertView.findViewById(R.id.severity_level);
        holder.incidentTypeImage = (ImageView) convertView.findViewById(R.id.incident_type);
        return holder;
    }

    @Override
    protected void bindView(AbstractViewHolder holder, int position) {
        Assault incident = (Assault) incidents.get(position);


        ArrayList<DynamicProperty> properties = incident.getProperties();

        if (properties != null) {
            int size = properties.size();
            if (size > 0) {

                ((IncidentViewHolder) holder).incidentType.setText(properties.get(0).getName() + ": "+ properties.get(0).getValue());
                if (size > 1) {
                    ((IncidentViewHolder) holder).incidentStatus.setText(properties.get(1).getName() + ": "+ properties.get(1).getValue());
                    if (size > 2) {
                        ((IncidentViewHolder) holder).incidentLoc.setText(properties.get(2).getName() + ": "+ properties.get(2).getValue());
                    }

                }

            }
        }

//        ((IncidentViewHolder)holder).incidentType.setText(incident.getTypeName());
//        ((IncidentViewHolder)holder).incidentStatus.setText(incident.getStatus());
//
//        ((IncidentViewHolder)holder).incidentLoc.setText(incident.getDistance());

        ((IncidentViewHolder) holder).incidentTypeImage.setImageResource(incident.getDrawableId());

        ((IncidentViewHolder) holder).severityIcon.setImageResource(R.mipmap.severity_icon);


    }

    private static class IncidentViewHolder extends AbstractViewHolder {
        public ImageView incidentTypeImage;
        public TextView incidentType;
        public TextView incidentStatus;
        public TextView incidentLoc;
        public ImageView severityIcon;


    }
}
