package com.fusionsauth.exceptions;

public class UserDuplicateException extends RuntimeException{

    public UserDuplicateException(String message){
        super(message);
    }
}
