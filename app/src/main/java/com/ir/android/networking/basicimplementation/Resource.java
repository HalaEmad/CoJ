package com.ir.android.networking.basicimplementation;

import android.content.Context;

import com.ir.android.networking.basicimplementation.exceptions.RetrivingFailedException;
import com.ir.android.networking.basicimplementation.exceptions.SavingFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public interface Resource {
    void save(Context context) throws SavingFailedException;

    void retrieve(Context context) throws RetrivingFailedException;
}
