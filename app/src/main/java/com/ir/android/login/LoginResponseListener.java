package com.ir.android.login;

import com.worklight.wlclient.api.WLResponse;

/**
 * Created by Henawey on 6/24/16.
 */

public class LoginResponseListener extends BasicWLResponseListener{

    public void getLoginResponse() throws LoginFailedException{
        try {
            WLResponse response = getResponseSync();
            if (status == ResponseStatus.FAIL) {
//            return new Result(1);
            } else {
//            return new Result(0);
            }
        }catch(InterruptedException e){
            throw new LoginFailedException(e);
        }
    }
}
