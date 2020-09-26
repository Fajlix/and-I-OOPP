package com.example.graymatter.Social;

class MissingAccessException extends Exception {
    public MissingAccessException(String exceptionLabel){
        super(exceptionLabel);
    }
    public MissingAccessException(){
        super();
    }
}