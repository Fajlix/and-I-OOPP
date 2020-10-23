package com.example.graymatter.model.dataAccess.social;

public class UserInfoException extends Exception {
    public UserInfoException(String exceptionLabel){
        super(exceptionLabel);
    }
    public UserInfoException(){
        super();
    }
}