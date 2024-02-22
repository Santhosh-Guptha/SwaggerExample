package com.fusionsauth.exceptions;

public class UserDetailsIncorrectException extends RuntimeException{
    public UserDetailsIncorrectException(String message){
        super(message);
    }
}
