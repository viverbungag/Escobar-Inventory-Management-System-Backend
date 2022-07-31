package com.exe.escobar.IMSBackend.Supplier.Exceptions;

public class SupplierNameIsExistingException extends RuntimeException{

    public SupplierNameIsExistingException(String name){
        super(String.format("This Name %s is already existing", name));
    }
}
