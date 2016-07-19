package com.ir.android.networking.IncidentRetrievingResource;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public class IncidentRetrievingFailedException extends InvocationFailedException {
    public IncidentRetrievingFailedException(){
        super();
    }

    public IncidentRetrievingFailedException(String message){
        super(message);
    }

    public IncidentRetrievingFailedException(Throwable throwable){
        super(throwable);
    }
}
