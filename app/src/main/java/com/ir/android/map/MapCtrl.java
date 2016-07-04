package com.ir.android.map;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;

import android.widget.Toast;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.Result;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.tasks.Task;

import com.ibm.android.kit.utils.LocationUtility;
import com.ir.android.model.Incident;
import com.ir.android.service.LocationService;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by emanhassan on 6/10/16.
 */
public class MapCtrl extends Controller{


    @Override
    public ViewModel initViewModel() {
        return new MapViewModel();
    }

    @Override
    public void init(Intent intent) {
        super.init(intent);


    }

}
