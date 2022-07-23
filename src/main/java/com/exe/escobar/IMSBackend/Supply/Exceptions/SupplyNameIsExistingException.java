package com.exe.escobar.IMSBackend.Supply.Exceptions;

public class SupplyNameIsExistingException extends RuntimeException{

    public SupplyNameIsExistingException(String name){
        super(String.format("This Supply Name is already existing: %s", name));
    }
}
