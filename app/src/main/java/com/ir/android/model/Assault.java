package com.ir.android.model;

import com.ir.android.R;

/**
 * Created by emanhassan on 7/18/16.
 */
public class Assault extends Incident {

    private String status;
    private String severityLevel;
    private String severityLvlDesc;
    private int weaponDrawableId;
    private  String weaponDescription;

    public Assault() {
        this.setType(TYPE_ASSUALT);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null)
            status ="";
        this.status = status;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        if (severityLevel == null)
            severityLevel ="";
        this.severityLevel = severityLevel;
    }

    public String getSeverityLvlDesc() {
        return severityLvlDesc;
    }

    public void setSeverityLvlDesc(String severityLvlDesc) {
        if (severityLvlDesc == null)
            severityLvlDesc ="" ;
        this.severityLvlDesc = severityLvlDesc;
    }

    public int getWeaponDrawableId() {
        return R.mipmap.weapons_icon;
    }

    public void setWeaponDrawableId(int weaponDrawableId) {
        this.weaponDrawableId = weaponDrawableId;
    }

    public String getWeaponDescription() {
        return weaponDescription;
    }

    public void setWeaponDescription(String weaponDescription) {
        if (weaponDescription == null)
            weaponDescription = "";
        this.weaponDescription = weaponDescription;
    }
}
