package com.ibm.android.kit.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibm.android.kit.models.ListItemViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter extends BaseAdapter {

    protected Context context;

    protected LayoutInflater layoutInflater;

    protected List<ListItemViewModel> info;

    public ListAdapter(Context context, List<ListItemViewModel> info) {

        this.context = context;
        this.info = info;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ListAdapter(Context context) {

        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AbstractViewHolder holder = null;

        if (convertView == null) {

            convertView = inflateLayout(null, position);

            // init ui elements
            holder = initView(convertView, position);

            // save holder
            convertView.setTag(holder);

        } else {

            // use saved holder if already recycled
            holder = (AbstractViewHolder) convertView.getTag();
        }

        // bind data to view holder
        bindView(convertView, holder, position);

        return convertView;
    }

    protected abstract View inflateLayout(ViewGroup parent, int position);

    protected abstract AbstractViewHolder initView(View convertView, int position);

    protected abstract void bindView(AbstractViewHolder holder, int position);

    protected void bindView(View convertView, AbstractViewHolder holder, int position) {

        // bind data to view holder
        bindView(holder, position);
    }

    public void updateData(ArrayList<ListItemViewModel> info) {

        this.info = info;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (info != null)
            return info.size();

        return 0;
    }

    @Override
    public Object getItem(int position) {

        if (info != null)
            return info.get(position);

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public boolean isEnabled(int position) {

        if (info != null)
            return info.get(position).isEnabled();

        return false;
    }
}
