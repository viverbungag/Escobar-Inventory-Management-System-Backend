package com.exe.escobar.IMSBackend.Transaction.Exceptions;

public class SupplyQuantityIsLessThanZeroException extends RuntimeException{

    public SupplyQuantityIsLessThanZeroException(){
        super("Supply Quantity should not be less than 0");
    }
}
