package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsExistingException;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsNullException;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.Exceptions.UnitOfMeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
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
                unitOfMeasurement.getIsActive()
        );
    }

    private Sort getSortingMethod(Boolean isAscending, Sort sort){
        if (isAscending){
            return sort.ascending();
        }
        return sort.descending();
    }

    private Sort getSortingValue(String sortedBy){

        switch(sortedBy){
            case "Name":
                return Sort.by("unit_of_measurement_name");
            case "Abbreviation":
                return Sort.by("unit_of_measurement_abbreviation");
            default:
                return Sort.unsorted();
        }
    }

    public Map<String, Object> getAllUnitOfMeasurements(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        Page<UnitOfMeasurement> unitOfMeasurementPage = unitOfMeasurementRepository
                .getAllPagedUnitOfMeasurement(pageable);

        Integer totalPages = unitOfMeasurementPage.getTotalPages();

        Map<String, Object> unitOfMeasurementsWithPageDetails = new HashMap<>();

        unitOfMeasurementsWithPageDetails.put("contents",
                unitOfMeasurementPage
                .getContent()
                .stream()
                .map((UnitOfMeasurement unitOfMeasurement) -> convertEntityToDto(unitOfMeasurement))
                .collect(Collectors.toList()));

        unitOfMeasurementsWithPageDetails.put("totalPages", totalPages);

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        return unitOfMeasurementsWithPageDetails;
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
                unitOfMeasurementDto.getIsActive());
    }

    public void updateUnitOfMeasurement(UnitOfMeasurementDto unitOfMeasurementDto, Long id) {

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository.getUnitOfMeasurementById(id)
                .orElseThrow(() -> new UnitOfMeasurementNotFoundException(id));

        String name = unitOfMeasurementDto.getUnitOfMeasurementName();
        String abbreviation = unitOfMeasurementDto.getUnitOfMeasurementAbbreviation();
        Boolean active = unitOfMeasurementDto.getIsActive();

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
        unitOfMeasurement.setIsActive(active);
    }
}
