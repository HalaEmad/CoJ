package com.ir.android.incidents.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.views.adapters.AbstractViewHolder;
import com.ibm.android.kit.views.adapters.ListAdapter;
import com.ir.android.R;
import com.ir.android.incidents.list.ItemListener;
import com.ir.android.model.Incident;

import java.util.ArrayList;

/**
 * Created by emanhassan on 7/4/16.
 */
public class IncidentListAdapter extends com.ibm.android.kit.views.adapters.ListAdapter {
    private ArrayList<Incident> incidents;
    private ItemListener listener ;
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
        View v = layoutInflater.inflate(R.layout.incident_list_item,  null);
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
        holder.incidentType = (TextView) convertView.findViewById(R.id.firstTitle);
        holder.incidentStatus = (TextView) convertView.findViewById(R.id.firstInfo);
        holder.incidentLoc = (TextView) convertView.findViewById(R.id.firstLoc);
        holder.severityIcon = (ImageView) convertView.findViewById(R.id.severity_level);
        holder.incidentTypeImage = (ImageView) convertView.findViewById(R.id.incident_type);
        return holder;
    }

    @Override
    protected void bindView(AbstractViewHolder holder, int position) {
        Incident incident = incidents.get(position);
        ((IncidentViewHolder)holder).incidentType.setText(incident.getType());
        ((IncidentViewHolder)holder).incidentStatus.setText(incident.getStatus());

        ((IncidentViewHolder)holder).incidentLoc.setText(incident.getDistance());

        ((IncidentViewHolder)holder).incidentTypeImage.setImageResource(incident.getDrawableId());
        //TODO
        ((IncidentViewHolder)holder).severityIcon.setImageResource(R.mipmap.severity_icon);


    }

    private static class IncidentViewHolder extends AbstractViewHolder {
        public ImageView incidentTypeImage;
        public TextView incidentType;
        public TextView incidentStatus;
        public TextView incidentLoc;
        public ImageView severityIcon;


    }
}
