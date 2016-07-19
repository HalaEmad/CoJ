package com.ir.android.networking.login;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 6/26/16.
 */
public class LoginFailedException extends InvocationFailedException {
    public LoginFailedException(){
        super();
    }

    public LoginFailedException(String message){
        super(message);
    }

    public LoginFailedException(Throwable throwable){
        super(throwable);
    }
}
