package com.ir.android.incidents;

import android.content.Intent;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;

/**
 * Created by emanhassan on 6/10/16.
 */
public class IncidentCtrl extends Controller{


    @Override
    public ViewModel initViewModel() {
        return new IncidentViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);


    }

}
