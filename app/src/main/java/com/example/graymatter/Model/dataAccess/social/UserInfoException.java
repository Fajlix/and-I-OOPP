package com.example.graymatter.Model.dataAccess.social;

public class UserInfoException extends Exception {
    public UserInfoException(String exceptionLabel){
        super(exceptionLabel);
    }
    public UserInfoException(){
        super();
    }
}