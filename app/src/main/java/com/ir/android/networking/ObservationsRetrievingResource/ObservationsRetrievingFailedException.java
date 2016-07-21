package com.ir.android.networking.ObservationsRetrievingResource;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public class ObservationsRetrievingFailedException extends InvocationFailedException {
    public ObservationsRetrievingFailedException(){
        super();
    }

    public ObservationsRetrievingFailedException(String message){
        super(message);
    }

    public ObservationsRetrievingFailedException(Throwable throwable){
        super(throwable);
    }
}
