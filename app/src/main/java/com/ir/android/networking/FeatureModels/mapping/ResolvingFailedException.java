package com.ir.android.networking.FeatureModels.mapping;

import com.ir.android.networking.basicimplementation.exceptions.InvocationFailedException;

/**
 * Created by Henawey on 7/20/16.
 */
public class ResolvingFailedException extends InvocationFailedException {
    public ResolvingFailedException(){
        super();
    }

    public ResolvingFailedException(String message){
        super(message);
    }

    public ResolvingFailedException(Throwable throwable){
        super(throwable);
    }
}
