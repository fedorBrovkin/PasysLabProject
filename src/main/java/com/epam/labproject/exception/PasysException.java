package com.epam.labproject.exception;

public class PasysException extends Exception{
    String message;
    public PasysException(String message){
        this.message=message;
    }
    public PasysException(){
        message="This is default Exception message";
    }
    @Override
    public String getMessage(){
        return this.message;
    }
}
