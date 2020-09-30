package com.example.graymatter.Social;

public class UserInfoException extends Exception {
    public UserInfoException(String exceptionLabel){
        super(exceptionLabel);
    }
    public UserInfoException(){
        super();
    }
}