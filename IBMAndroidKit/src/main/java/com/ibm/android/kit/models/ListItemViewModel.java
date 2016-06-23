package com.ibm.android.kit.models;

public abstract class ListItemViewModel extends ViewModel implements Comparable<ListItemViewModel> {

    protected long idL;

    protected String idStr;

    protected boolean enabled = true;

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String id) {
        this.idStr = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getIdL() {
        return idL;
    }

    public void setIdL(long idL) {
        this.idL = idL;
    }
}
