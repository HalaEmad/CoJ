package com.ir.android.main;

import android.support.v4.app.Fragment;

import com.ibm.android.kit.models.ViewModel;

/**
 * Created by bassam on 12-07-2016.
 */
public class MainViewModel extends ViewModel {

    private int selected = -1;
    private Fragment fragment;
    private String title;
    private String tag;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        if (selected != this.selected) {
            this.selected = selected;
            setModelChanged();
        }
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
