package com.exe.escobar.IMSBackend.SupplyCategory.Exceptions;

public class SupplyCategoryNameIsExistingException extends RuntimeException{

    public SupplyCategoryNameIsExistingException(String name){
        super(String.format("This Supply Category Name is already existing: %s", name));
    }
}
