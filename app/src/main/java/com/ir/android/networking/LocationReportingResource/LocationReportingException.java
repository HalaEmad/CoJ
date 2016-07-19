package com.ir.android.networking.LocationReportingResource;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public class LocationReportingException extends InvocationFailedException {
    public LocationReportingException(){
        super();
    }

    public LocationReportingException(String message){
        super(message);
    }

    public LocationReportingException(Throwable throwable){
        super(throwable);
    }
}
