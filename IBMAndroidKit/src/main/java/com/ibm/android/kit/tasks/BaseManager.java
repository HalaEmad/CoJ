package com.ibm.android.kit.tasks;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

public abstract class BaseManager extends Observable {

    protected Context context;

    public BaseManager(Context context) {
        this.context = context;
    }

    public void register(Observer observer) {
        addObserver(observer);
    }

    public void unregister(Observer observer) {
        deleteObserver(observer);
    }

    protected void notifyChanged() {
        setChanged();
        notifyObservers();
    }

}
