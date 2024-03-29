package com.exe.escobar.IMSBackend.Security.Exceptions;

public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(){
        super("The user does not exist");
    }

    public UserDoesNotExistException(Throwable cause){
        super("The user does not exist", cause);
    }
}
