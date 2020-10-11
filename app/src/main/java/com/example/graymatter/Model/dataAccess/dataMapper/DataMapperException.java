package com.example.graymatter.Model.dataAccess.dataMapper;

public class DataMapperException extends RuntimeException {

    public DataMapperException(){
        super();
    }

    public DataMapperException(String exceptionLabel){
        super(exceptionLabel);
    }
}
