package com.ir.android.networking.login;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Hala on 7/19/16.
 */
public class LogoutFailedException extends InvocationFailedException {
    public LogoutFailedException(){
        super();
    }

    public LogoutFailedException(String message){
        super(message);
    }

    public LogoutFailedException(Throwable throwable){
        super(throwable);
    }
}
