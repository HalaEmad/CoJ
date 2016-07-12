package com.ir.android.networking.IncidentRetrievingResource;

import com.ir.android.networking.basicimplementation.exceptions.RetrivingFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public class IncidentRetrievingFailedException extends RetrivingFailedException{
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
