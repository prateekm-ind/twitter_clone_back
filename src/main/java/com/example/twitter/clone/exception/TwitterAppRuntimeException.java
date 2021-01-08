package com.example.twitter.clone.exception;

import java.util.function.Supplier;

public class TwitterAppRuntimeException extends RuntimeException {
    public TwitterAppRuntimeException(String message){
        super(message);
    }
}
