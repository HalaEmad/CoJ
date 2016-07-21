package com.ir.android.model;

/**
 * Created by emanhassan on 7/18/16.
 */
public class Officer extends Incident {
    private String rank;
    private String speciality;
    private String name;
    private String unit;

    public Officer() {
        this.setType(TYPE_OFFICER);
    }
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        if (rank == null)
            rank = "";
        this.rank = rank;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        if (speciality == null)
            speciality = "";
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            name = "";
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        if(unit == null)
            unit ="";
        this.unit = unit;
    }
}
