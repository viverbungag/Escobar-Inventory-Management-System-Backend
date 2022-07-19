package com.exe.escobar.IMSBackend.Supplier.Exceptions;

public class SupplierNameIsExistingException extends RuntimeException{

    public SupplierNameIsExistingException(String name){
        super(String.format("This name is already existing: %s", name));
    }
}
