package com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions;

public class UnitOfMeasurementNameIsNullException extends RuntimeException{

    public UnitOfMeasurementNameIsNullException(){
        super("Unit of Measurement name should not be empty");
    }
}
