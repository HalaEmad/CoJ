package com.ir.android.networking.basicimplementation;

import com.ir.android.networking.basicimplementation.exceptions.RetrivingFailedException;
import com.ir.android.networking.basicimplementation.exceptions.SavingFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public interface Resource {
    void save() throws SavingFailedException;

    void retrieve() throws RetrivingFailedException;
}
