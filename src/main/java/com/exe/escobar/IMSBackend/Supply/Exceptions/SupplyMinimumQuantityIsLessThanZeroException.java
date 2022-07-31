package com.exe.escobar.IMSBackend.Supply.Exceptions;

public class SupplyMinimumQuantityIsLessThanZeroException extends RuntimeException{

    public SupplyMinimumQuantityIsLessThanZeroException(){
        super("Minimum Quantity should not be less than 0");
    }
}
