package com.example.graymatter.Social;

class UserInfoException extends Exception {
    public UserInfoException(String exceptionLabel){
        super(exceptionLabel);
    }
    public UserInfoException(){
        super();
    }
}