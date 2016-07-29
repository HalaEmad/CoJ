package com.ir.android.incidents.popup;

import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.R;
import com.ir.android.model.Officer;

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
        type.setText(getContext().getString(R.string.officer_label));

        name.setText(officer.getName());
        distance.setText(officer.getDistance());

        unit.setText(officer.getUnit());
        speciality.setText(officer.getSpeciality());
        rank.setText(officer.getRank());
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
