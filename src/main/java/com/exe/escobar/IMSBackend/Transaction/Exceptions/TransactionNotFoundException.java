package com.exe.escobar.IMSBackend.Transaction.Exceptions;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(Long id){
        super(String.format("Could not find Transaction with id: %s", id));
    }
}
