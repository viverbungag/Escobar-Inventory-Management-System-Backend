package com.exe.escobar.IMSBackend.Supplier.Exceptions;

public class SupplierNameIsExistingException extends RuntimeException{

    public SupplierNameIsExistingException(String name){
        super(String.format("This Supplier Name is already existing: %s", name));
    }
}
