package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitOfMeasurementDao {

    public List<UnitOfMeasurement> getAllUnitOfMeasurements();

    void insertUnitOfMeasurement(String unitOfMeasurementName,
                                 String unitOfMeasurementAbbreviation,
                                 Boolean isActive);

    Optional<UnitOfMeasurement> getUnitOfMeasurementById(Long unitOfMeasurementId);

    Optional<UnitOfMeasurement> getUnitOfMeasurementByName(String unitOfMeasurementName);

    Page<UnitOfMeasurement> getAllPagedUnitOfMeasurement(Pageable pageable);

}
