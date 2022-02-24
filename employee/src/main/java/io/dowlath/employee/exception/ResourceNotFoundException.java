package io.dowlath.employee.exception;

/***
 * Author : Dowlath Basha G
 * Date   : 2/22/2022
 * Time   : 10:21 PM
 ***/
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    ResourceNotFoundException(String message,Throwable cause){
        super(message,cause);
    }

}
