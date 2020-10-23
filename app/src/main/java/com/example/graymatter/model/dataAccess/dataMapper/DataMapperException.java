package com.example.graymatter.model.dataAccess.dataMapper;

//TODO do not know if this should be RuntimeException or checked Exception
/**
 * A RuntimeException thrown if exception occurs during DataMapper<T> implementation operation.
 */
public class DataMapperException extends RuntimeException {

    /**
     * Constructor for DataMapperException with null as detail message.
     */
    public DataMapperException(){
        super();
    }

    /**
     * Constructor for DataMapperException with exceptionLabel as detail message.
     * @param exceptionLabel label explaining the cause of the exception being thrown.
     */
    public DataMapperException(String exceptionLabel){
        super(exceptionLabel);
    }
}
