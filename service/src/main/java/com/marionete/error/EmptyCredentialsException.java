package com.marionete.error;

public class EmptyCredentialsException extends RuntimeException{

    public EmptyCredentialsException(String message){
        super(message);
    }
}
