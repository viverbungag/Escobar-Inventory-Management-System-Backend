package com.exe.escobar.IMSBackend.SupplyCategory.Exceptions;

public class SupplyCategoryNameIsExistingException extends RuntimeException{

    public SupplyCategoryNameIsExistingException(String name){
        super(String.format("The Name %s is already existing", name));
    }
}
