package com.example.bestcarsbackend.Exception;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String message){
        super(message);
    }
}
