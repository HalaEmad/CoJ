package com.ir.android.networking.basicimplementation.exceptions;

/**
 * Created by Henawey on 7/11/16.
 */
public class SavingFailedException extends ProcessingFailedException {
    public SavingFailedException(){
        super();
    }

    public SavingFailedException(String message){super(message);}

    public SavingFailedException(Throwable throwable){
        super(throwable);
    }
}