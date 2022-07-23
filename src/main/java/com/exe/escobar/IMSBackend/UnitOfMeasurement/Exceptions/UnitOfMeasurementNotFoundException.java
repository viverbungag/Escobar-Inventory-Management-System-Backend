package com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions;

public class UnitOfMeasurementNotFoundException extends RuntimeException{

    public UnitOfMeasurementNotFoundException(Long id){
        super(String.format("Could not find Supplier with id: %s", id));
    }
}
