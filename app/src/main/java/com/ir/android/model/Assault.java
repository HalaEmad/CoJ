package com.ir.android.model;

/**
 * Created by emanhassan on 7/18/16.
 */
public class Assault extends Incident{

    private String status;
    private String severityLevel;
    private String severityLvlDesc;
    private int weaponDrawableId;
    private  String weaponDescription;

    // TODO REMOVE
    public Assault(double latitude, double longitude, String info) {
        super(latitude, longitude, info);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getSeverityLvlDesc() {
        return severityLvlDesc;
    }

    public void setSeverityLvlDesc(String severityLvlDesc) {
        this.severityLvlDesc = severityLvlDesc;
    }

    public int getWeaponDrawableId() {
        return weaponDrawableId;
    }

    public void setWeaponDrawableId(int weaponDrawableId) {
        this.weaponDrawableId = weaponDrawableId;
    }

    public String getWeaponDescription() {
        return weaponDescription;
    }

    public void setWeaponDescription(String weaponDescription) {
        this.weaponDescription = weaponDescription;
    }
}
