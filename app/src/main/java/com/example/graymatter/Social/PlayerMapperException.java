package com.example.graymatter.Social;

public class PlayerMapperException extends RuntimeException {

    public PlayerMapperException(){
        super();
    }

    public PlayerMapperException(String exceptionLabel){
        super(exceptionLabel);
    }
}
