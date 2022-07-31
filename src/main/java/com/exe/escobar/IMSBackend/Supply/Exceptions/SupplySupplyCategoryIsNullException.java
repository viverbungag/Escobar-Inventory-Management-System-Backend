package com.exe.escobar.IMSBackend.Supply.Exceptions;

public class SupplySupplyCategoryIsNullException extends RuntimeException{

    public SupplySupplyCategoryIsNullException(){
        super("Supply Category should not be empty");
    }
}
