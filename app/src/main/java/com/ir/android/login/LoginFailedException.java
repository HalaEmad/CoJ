package com.ir.android.login;

/**
 * Created by Henawey on 6/26/16.
 */
public class LoginFailedException extends Exception {
    public LoginFailedException(){
        super();
    }

    public LoginFailedException(Throwable throwable){
        super(throwable);
    }
}
