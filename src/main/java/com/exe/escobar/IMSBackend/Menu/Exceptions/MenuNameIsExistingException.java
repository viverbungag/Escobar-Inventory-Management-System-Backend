package com.exe.escobar.IMSBackend.Menu.Exceptions;

public class MenuNameIsExistingException extends RuntimeException{

    public MenuNameIsExistingException(String name){
        super(String.format("This Menu Name is already existing: %s", name));
    }
}
