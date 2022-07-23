package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitOfMeasurementDao {

    public List<UnitOfMeasurement> getAllUnitOfMeasurements();

    void insertUnitOfMeasurement(String unitOfMeasurementName,
                                 String unitOfMeasurementAbbreviation,
                                 Boolean active);

    Optional<UnitOfMeasurement> getUnitOfMeasurementById(Long unitOfMeasurementId);

    Optional<UnitOfMeasurement> getUnitOfMeasurementByName(String unitOfMeasurementName);
}
