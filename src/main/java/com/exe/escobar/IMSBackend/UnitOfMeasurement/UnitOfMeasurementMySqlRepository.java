package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("unit_of_measurement_mysql")
public interface UnitOfMeasurementMySqlRepository extends UnitOfMeasurementDao, JpaRepository<UnitOfMeasurement, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    public List<UnitOfMeasurement> getAllUnitOfMeasurements();

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(unit_of_measurement_name, unit_of_measurement_abbreviation, active) " +
            "VALUES (:unitOfMeasurementName, :unitOfMeasurementAbbreviation, :active)",
            nativeQuery = true)
    void insertUnitOfMeasurement(@Param("unitOfMeasurementName") String unitOfMeasurementName,
                                 @Param("unitOfMeasurementAbbreviation") String unitOfMeasurementAbbreviation,
                                 @Param("active") Boolean active);

    @Query(value = "SELECT * FROM #{#entityName} WHERE unit_of_measurement_id = :unitOfMeasurementId",
            nativeQuery = true)
    Optional<UnitOfMeasurement> getUnitOfMeasurementById(@Param("unitOfMeasurementId") Long unitOfMeasurementId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE unit_of_measurement_name = :unitOfMeasurementName",
            nativeQuery = true)
    Optional<UnitOfMeasurement> getUnitOfMeasurementByName(@Param("unitOfMeasurementName") String unitOfMeasurementName);
}
