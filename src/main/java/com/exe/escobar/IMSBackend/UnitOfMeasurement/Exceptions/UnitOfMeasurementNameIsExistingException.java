package com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions;

public class UnitOfMeasurementNameIsExistingException extends RuntimeException{

    public UnitOfMeasurementNameIsExistingException(String name){
        super(String.format("This Unit of Measurement Name is already existing: %s", name));
    }
}
