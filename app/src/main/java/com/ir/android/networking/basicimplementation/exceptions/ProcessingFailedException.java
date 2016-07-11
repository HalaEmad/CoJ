package com.ir.android.networking.basicimplementation.exceptions;

/**
 * Created by Henawey on 7/11/16.
 */
public class ProcessingFailedException extends Exception {
    public ProcessingFailedException(){
        super();
    }

    public ProcessingFailedException(String message){
        super(message);
    }

    public ProcessingFailedException(Throwable throwable){
        super(throwable);
    }
}
