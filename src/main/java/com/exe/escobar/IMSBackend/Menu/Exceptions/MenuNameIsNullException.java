package com.exe.escobar.IMSBackend.Menu.Exceptions;

public class MenuNameIsNullException extends RuntimeException{

    public MenuNameIsNullException(){
        super("Menu name should not be null");
    }
}
