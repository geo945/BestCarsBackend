package com.example.bestcarsbackend.Exception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(String msg){
        super(msg);
    }
}
