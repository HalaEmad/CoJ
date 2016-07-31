package com.ir.android.incidents.popup;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.R;
import com.ir.android.model.Assault;
import com.ir.android.networking.FeatureModels.DynamicProperty;

import java.util.ArrayList;

/**
 * Created by bassam on 25-07-2016.
 */
public class AssaultFragment extends PopupBaseFragment {

    private ImageView typeIcon;
    private ImageView weaponIcon;
    private TextView type;
    private TextView status;
    private TextView distance;
    private TextView securityLvl;
    private TextView securityLvlDescription;
    private TextView weaponDescription;
    private TextView weapon;
    private Assault assault;

    @Override
    protected Controller createController() {
        return new AssaultCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "ctrl.popup.assault";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_dialog_assault;
    }

    @Override
    protected void initViews() {
        super.initViews();

        typeIcon = (ImageView) getView().findViewById(R.id.type_icon);
        type = (TextView) getView().findViewById(R.id.type);
        status = (TextView) getView().findViewById(R.id.status);
        distance = (TextView) getView().findViewById(R.id.distance);

        securityLvl = (TextView) getView().findViewById(R.id.severity_level);
        securityLvlDescription = (TextView) getView().findViewById(R.id.severity_desc);
        weaponIcon = (ImageView) getView().findViewById(R.id.weapon_icon);
        weaponDescription = (TextView) getView().findViewById(R.id.weapon_desc);
        weapon = (TextView) getView().findViewById(R.id.weapon);
    }

    public void setViewModel(Assault assault) {
        this.assault = assault;
//        if (controller == null)
//            controller = new AssaultCtrl();
//        controller.initViewModel();
//        ( (Assault)controller.getViewModel()).setSeverityLevel(assault.getSeverityLevel());
//        ( (Assault)controller.getViewModel()).setSeverityLvlDesc(assault.getSeverityLvlDesc());
//
//        ( (Assault)controller.getViewModel()).setStatus(assault.getStatus());
//        ( (Assault)controller.getViewModel()).setType(assault.getType());
//        ( (Assault)controller.getViewModel()).setWeaponDescription(assault.getWeaponDescription());
//        ( (Assault)controller.getViewModel()).setWeaponDrawableId(assault.getWeaponDrawableId());
//        ( (Assault)controller.getViewModel()).setLongitude(assault.getLongitude());
//        ( (Assault)controller.getViewModel()).setDistance(assault.getDistance());
//        ( (Assault)controller.getViewModel()).setTypeName(assault.getTypeName());
//        ( (Assault)controller.getViewModel()).setLatitude(assault.getLatitude());

    }

    @Override
    protected void bindViews(ViewModel viewModel) {

        typeIcon.setImageResource(assault.getDrawableId());
        ArrayList<DynamicProperty> properties = assault.getProperties();

//        for (int i =0; i<3; i++) {
//            DynamicProperty p = new DynamicProperty();
//            p.setName("type call"+i);
//            p.setValue(i+" the value ");
//            properties.add(p);
//        }
        LinearLayout secondSection = (LinearLayout) getView().findViewById(R.id.second_section);
        LinearLayout third = (LinearLayout) getView().findViewById(R.id.weapon_layout);

        if (properties != null) {
            int size = properties.size();
            if (size > 0) {
                Log.i("mapping_ui" , properties.get(0).getName() + ": "+ properties.get(0).getValue());
                type.setText(properties.get(0).getName() + ": "+ properties.get(0).getValue());
                type.setVisibility(View.VISIBLE);
            }

//            type.setText(assault.getTypeName());


            if (size > 1) {
                Log.i("mapping_ui" , properties.get(1).getName() + ": "+ properties.get(1).getValue());
                status.setText(properties.get(1).getName() + ": "+properties.get(1).getValue());
                status.setVisibility(View.VISIBLE);
            }


//            status.setText(assault.getStatus());

            if (size > 2) {
                Log.i("mapping_ui" , properties.get(2).getName() + ": "+ properties.get(2).getValue());
                distance.setText(properties.get(2).getName() + ": "+ properties.get(2).getValue());
                distance.setVisibility(View.VISIBLE);
            }


//            distance.setText(assault.getDistance());

            if (size > 3) {
                Log.i("mapping_ui" , properties.get(3).getName() + ": "+ properties.get(3).getValue());
                securityLvl.setText(properties.get(3).getName() + ": "+ properties.get(3).getValue());
                securityLvl.setVisibility(View.VISIBLE);
                secondSection.setVisibility(View.VISIBLE);
            }

//            securityLvl.setText(assault.getSeverityLevel());


            if (size > 4) {
                Log.i("mapping_ui" , properties.get(4).getName() + ": "+ properties.get(4).getValue());
                securityLvlDescription.setText(properties.get(4).getName() + ": "+ properties.get(4).getValue());
                securityLvlDescription.setVisibility(View.VISIBLE);
                secondSection.setVisibility(View.VISIBLE);

            }

//            securityLvlDescription.setText(assault.getSeverityLvlDesc());
            if (size > 5) {
                Log.i("mapping_ui" , properties.get(5).getName() + ": "+ properties.get(5).getValue());
                weapon.setText(properties.get(5).getName() + ": "+ properties.get(5).getValue());
                third.setVisibility(View.VISIBLE);
                weapon.setVisibility(View.VISIBLE);
            }

            if (size > 6) {
                Log.i("mapping_ui" , properties.get(6).getName() + ": "+ properties.get(6).getValue());
                weaponDescription.setText(properties.get(6).getName() + ": "+ properties.get(6).getValue());
                weaponDescription.setVisibility(View.VISIBLE);
            }
//            weaponIcon.setImageResource(assault.getWeaponDrawableId());
//            weaponDescription.setText(assault.getWeaponDescription());
        }
    }
}
