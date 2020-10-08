package com.example.graymatter.Social;

public class DataMapperException extends RuntimeException {

    public DataMapperException(){
        super();
    }

    public DataMapperException(String exceptionLabel){
        super(exceptionLabel);
    }
}
