package com.ir.android.incidents.popup;

import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ir.android.R;
import com.ir.android.model.Assault;

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
        type.setText(assault.getTypeName());
//        type.setText(getContext().getString(R.string.assualt_label));

        status.setText(assault.getStatus());
        distance.setText(assault.getDistance());

        securityLvl.setText(assault.getSeverityLevel());
        securityLvlDescription.setText(assault.getSeverityLvlDesc());

        weaponIcon.setImageResource(assault.getWeaponDrawableId());
        weaponDescription.setText(assault.getWeaponDescription());

    }
}
