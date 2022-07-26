package com.exe.escobar.IMSBackend.Menu.Exceptions;

public class MenuPriceIsNullException extends RuntimeException{

    public MenuPriceIsNullException(){
        super("Menu price should not be null");
    }
}
