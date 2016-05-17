package com.phoenixnap.oss.sample.server.exceptions;

/**
 * Custom exception for use with sample services
 * 
 * @author kristiang
 *
 */
public class DrinkNotFoundException extends Exception{
    
    public DrinkNotFoundException(){
        super();
    }
    
    public DrinkNotFoundException(String message){
        super(message);
    }
    
    public DrinkNotFoundException(String message, Throwable e){
        super(message,e);
    }
    
}
