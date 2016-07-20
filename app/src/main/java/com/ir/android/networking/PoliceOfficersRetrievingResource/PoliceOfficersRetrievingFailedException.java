package com.ir.android.networking.PoliceOfficersRetrievingResource;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/11/16.
 */
public class PoliceOfficersRetrievingFailedException extends InvocationFailedException {
    public PoliceOfficersRetrievingFailedException(){
        super();
    }

    public PoliceOfficersRetrievingFailedException(String message){
        super(message);
    }

    public PoliceOfficersRetrievingFailedException(Throwable throwable){
        super(throwable);
    }
}
