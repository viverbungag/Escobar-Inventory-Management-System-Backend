package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsExistingException;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsNullException;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnitOfMeasurementService {

    @Autowired
    @Qualifier("unit_of_measurement_mysql")
    UnitOfMeasurementDao unitOfMeasurementRepository;

    private UnitOfMeasurementDto convertEntityToDto(UnitOfMeasurement unitOfMeasurement){
        return new UnitOfMeasurementDto(
                unitOfMeasurement.getUnitOfMeasurementId(),
                unitOfMeasurement.getUnitOfMeasurementName(),
                unitOfMeasurement.getUnitOfMeasurementAbbreviation(),
                unitOfMeasurement.getActive()
        );
    }

    public List<UnitOfMeasurementDto> getAllUnitOfMeasurements() {
        return unitOfMeasurementRepository
                .getAllUnitOfMeasurements()
                .stream()
                .map((UnitOfMeasurement unitOfMeasurement) -> convertEntityToDto(unitOfMeasurement))
                .collect(Collectors.toList());
    }

    public void addUnitOfMeasurement(UnitOfMeasurementDto unitOfMeasurementDto) {
        String name = unitOfMeasurementDto.getUnitOfMeasurementName();

        Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository
                .getUnitOfMeasurementByName(name);

        if (unitOfMeasurementOptional.isPresent()){
            throw new UnitOfMeasurementNameIsExistingException(name);
        }

        unitOfMeasurementRepository.insertUnitOfMeasurement(
                unitOfMeasurementDto.getUnitOfMeasurementName(),
                unitOfMeasurementDto.getUnitOfMeasurementAbbreviation(),
                unitOfMeasurementDto.getActive());
    }

    public void updateUnitOfMeasurement(UnitOfMeasurementDto unitOfMeasurementDto, Long id) {

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository.getUnitOfMeasurementById(id)
                .orElseThrow(() -> new UnitOfMeasurementNotFoundException(id));

        String name = unitOfMeasurementDto.getUnitOfMeasurementName();
        String abbreviation = unitOfMeasurementDto.getUnitOfMeasurementAbbreviation();
        Boolean active = unitOfMeasurementDto.getActive();

        if (name == null || name.length() <= 0){
            throw new UnitOfMeasurementNameIsNullException();
        }

        if (!Objects.equals(unitOfMeasurement.getUnitOfMeasurementName(), name)){

            Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository
                    .getUnitOfMeasurementByName(name);

            if (unitOfMeasurementOptional.isPresent()){
                throw new UnitOfMeasurementNameIsExistingException(name);
            }

            unitOfMeasurement.setUnitOfMeasurementName(name);
        }

        unitOfMeasurement.setUnitOfMeasurementAbbreviation(abbreviation);
        unitOfMeasurement.setActive(active);
    }
}
