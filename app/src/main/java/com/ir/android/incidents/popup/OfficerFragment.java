package com.ir.android.incidents.popup;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.R;
import com.ir.android.model.Officer;
import com.ir.android.networking.FeatureModels.DynamicProperty;

import java.util.ArrayList;

/**
 * Created by bassam on 25-07-2016.
 */
public class OfficerFragment extends PopupBaseFragment {

    private ImageView icon;
    private TextView type;
    private TextView name;
    private TextView distance;
    private TextView unit;
    private TextView rank;
    private TextView speciality;
    private Officer officer;

    @Override
    protected Controller createController() {
        return new OfficerCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.popup.officer";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_dialog_officer;
    }

    @Override
    protected void initViews() {
        super.initViews();

        icon = (ImageView) getView().findViewById(R.id.type_icon);
        type = (TextView) getView().findViewById(R.id.type);

        name = (TextView) getView().findViewById(R.id.name);
        distance = (TextView) getView().findViewById(R.id.distance);

        unit = (TextView) getView().findViewById(R.id.unit);
        speciality = (TextView) getView().findViewById(R.id.speciality);
        rank = (TextView) getView().findViewById(R.id.rank);

    }

    @Override
    protected void bindViews(ViewModel viewInfo) {
        icon.setImageResource(officer.getDrawableId());


        ArrayList<DynamicProperty> properties = officer.getProperties();
//        for (int i =0; i<3; i++) {
//            DynamicProperty p = new DynamicProperty();
//            p.setName("type call"+i);
//            p.setValue(i+" the value ");
//            properties.add(p);
//        }
        if (properties != null) {
            int size = properties.size();
            if (size > 0) {
                Log.i("mapping_ui" , properties.get(0).getName() + ": "+ properties.get(0).getValue());
                type.setText(properties.get(0).getName() + ": "+ properties.get(0).getValue());
                type.setVisibility(View.VISIBLE);
            }

            if (size > 1) {
                Log.i("mapping_ui" , properties.get(1).getName() + ": "+ properties.get(1).getValue());
                rank.setText(properties.get(1).getName() + ": "+ properties.get(1).getValue());
                rank.setVisibility(View.VISIBLE);
            }

            if (size > 2) {
                Log.i("mapping_ui" , properties.get(2).getName() + ": "+ properties.get(2).getValue());
                distance.setText(properties.get(2).getName() + ": "+ properties.get(2).getValue());
                rank.setVisibility(View.VISIBLE);
            }


//            distance.setText(assault.getDistance());

            if (size > 3) {
                Log.i("mapping_ui" , properties.get(3).getName() + ": "+ properties.get(3).getValue());
                name.setText(properties.get(3).getName() + ": "+ properties.get(3).getValue());
                name.setVisibility(View.VISIBLE);
            }

//            securityLvl.setText(assault.getSeverityLevel());


            if (size > 4) {
                Log.i("mapping_ui" , properties.get(4).getName() + ": "+ properties.get(4).getValue());
                unit.setText(properties.get(4).getName() + ": "+ properties.get(4).getValue());
                unit.setVisibility(View.VISIBLE);
            }

//            securityLvlDescription.setText(assault.getSeverityLvlDesc());
            if (size > 5) {
                Log.i("mapping_ui" , properties.get(5).getName() + ": "+ properties.get(5).getValue());
                speciality.setText( properties.get(5).getName() + ": "+ properties.get(5).getValue());
                speciality.setVisibility(View.VISIBLE);
            }


//            weaponIcon.setImageResource(assault.getWeaponDrawableId());
//            weaponDescription.setText(assault.getWeaponDescription());
        }


//        type.setText(getContext().getString(R.string.officer_label));
//
//        name.setText(officer.getName());
//        distance.setText(officer.getDistance());
//
//        unit.setText(officer.getUnit());
//        speciality.setText(officer.getSpeciality());
//        rank.setText(officer.getRank());
    }

    public void setData(Officer officer) {
        this.officer = officer;
//        if (controller == null)
//            controller = new OfficerCtrl();
//        controller.initViewModel();
//
//        ( (Officer)controller.getViewModel()).setRank(officer.getRank());
//        ( (Officer)controller.getViewModel()).setSpeciality(officer.getSpeciality());
//        ( (Officer)controller.getViewModel()).setType(officer.getType());
//        ( (Officer)controller.getViewModel()).setUnit(officer.getUnit());
//        ( (Officer)controller.getViewModel()).setName(officer.getName());
//        ( (Officer)controller.getViewModel()).setLongitude(officer.getLongitude());
//        ( (Officer)controller.getViewModel()).setDistance(officer.getDistance());
//        ( (Officer)controller.getViewModel()).setTypeName(officer.getTypeName());
//        ( (Officer)controller.getViewModel()).setLatitude(officer.getLatitude());
    }
}
