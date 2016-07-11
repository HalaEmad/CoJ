package com.ir.android.networking.login;

import com.ir.android.networking.basicimplementation.exceptions.RetrivingFailedException;

/**
 * Created by Henawey on 6/26/16.
 */
public class LoginFailedException extends RetrivingFailedException {
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
