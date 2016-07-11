package com.ir.android.networking.basicImplemenation;

import android.content.Context;

import com.ir.android.networking.exceptions.RetrivingFailedException;
import com.ir.android.networking.exceptions.SavingFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public interface Resource {
    void save(Context context) throws SavingFailedException;

    void retrieve(Context context) throws RetrivingFailedException;
}
