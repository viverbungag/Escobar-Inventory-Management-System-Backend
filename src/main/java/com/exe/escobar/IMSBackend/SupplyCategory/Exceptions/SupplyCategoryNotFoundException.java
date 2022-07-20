package com.exe.escobar.IMSBackend.SupplyCategory.Exceptions;

public class SupplyCategoryNotFoundException extends RuntimeException{

    public SupplyCategoryNotFoundException(Long id){
        super(String.format("Could not find Supply Category with id: %s", id));
    }
}
