package com.ir.android.networking.basicimplementation.exceptions;

/**
 * Created by Henawey on 7/11/16.
 */
public class InvocationFailedException extends ProcessingFailedException {
    public InvocationFailedException(){
        super();
    }

    public InvocationFailedException(String message){
        super(message);
    }

    public InvocationFailedException(Throwable throwable){
        super(throwable);
    }
}
