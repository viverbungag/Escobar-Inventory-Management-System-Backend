package com.exe.escobar.IMSBackend.Supplier.Exceptions;

public class SupplierNameIsNullException extends RuntimeException{

    public SupplierNameIsNullException(){
        super("Supplier name should not be empty");
    }
}
