package com.ibm.android.kit.models;

import java.util.Observable;
import java.util.Observer;

public abstract class ViewModel extends Observable {

    protected void setModelChanged() {
        setChanged();
        notifyObservers();
    }

    public void registerForChanges(Observer observer) {
        addObserver(observer);
    }

    public void unregisterForChanges(Observer observer) {
        deleteObserver(observer);
    }
}
