package com.ir.android.networking.basicimplementation.exceptions;

/**
 * Created by Henawey on 7/11/16.
 */
public class RetrivingFailedException extends ProcessingFailedException {
    public RetrivingFailedException(){
        super();
    }

    public RetrivingFailedException(String message){
        super(message);
    }

    public RetrivingFailedException(Throwable throwable){
        super(throwable);
    }
}
