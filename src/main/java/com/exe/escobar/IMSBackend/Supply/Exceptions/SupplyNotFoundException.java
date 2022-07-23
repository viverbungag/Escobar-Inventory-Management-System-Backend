package com.exe.escobar.IMSBackend.Supply.Exceptions;

public class SupplyNotFoundException extends RuntimeException {

    public SupplyNotFoundException(Long id){
        super(String.format("Could not find Supply with id: %s", id));
    }
}
