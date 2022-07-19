package com.exe.escobar.IMSBackend.Supplier.Exceptions;

public class SupplierNotFoundException extends RuntimeException{

    public SupplierNotFoundException(Long id){
        super(String.format("Could not find supplier with id: %s", id));
    }
}
