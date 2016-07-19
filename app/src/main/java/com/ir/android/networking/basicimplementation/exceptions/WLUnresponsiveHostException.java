package com.ir.android.networking.basicimplementation.exceptions;

/**
 * Created by Henawey on 7/17/16.
 */
public class WLUnresponsiveHostException extends ProcessingFailedException {
    public WLUnresponsiveHostException(){
        super();
    }

    public WLUnresponsiveHostException(String message){
        super(message);
    }

    public WLUnresponsiveHostException(Throwable throwable){
        super(throwable);
    }
}
