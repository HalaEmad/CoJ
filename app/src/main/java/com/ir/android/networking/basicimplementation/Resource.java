package com.ir.android.networking.basicimplementation;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public interface Resource {

    void invoke() throws InvocationFailedException;
}
