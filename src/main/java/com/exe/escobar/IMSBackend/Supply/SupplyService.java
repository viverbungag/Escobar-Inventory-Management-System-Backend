package com.exe.escobar.IMSBackend.Supply;

import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.Supply.Exceptions.SupplyNameIsExistingException;
import com.exe.escobar.IMSBackend.Supply.Exceptions.SupplyNameIsNullException;
import com.exe.escobar.IMSBackend.Supply.Exceptions.SupplyNotFoundException;
import com.exe.escobar.IMSBackend.SupplyCategory.SupplyCategory;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


@Service
@Transactional
public class SupplyService {

    @Autowired
    @Qualifier("supply_mysql")
    SupplyDao supplyRepository;

    private SupplyDto convertEntityToDto(Supply supply){
        return new SupplyDto(
                supply.getSupplyId(),
                supply.getSupplyName(),
                supply.getSupplyQuantity(),
                supply.getMinimumQuantity(),
                supply.getInMinimumQuantity(),
                supply.getSupplier(),
                supply.getUnitOfMeasurement(),
                supply.getSupplyCategory(),
                supply.getIsActive()
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
                return Sort.by("supply_name");

            case "Quantity":
                return Sort.by("supply_quantity");

            case "Minimum Quantity":
                return Sort.by("minimum_quantity");

            case "In Minimum Quantity":
                return Sort.by("in_minimum_quantity");

            case "Supplier":
                return Sort.by("supplier.supplier_name");

            case "Unit of Measurement":
                return Sort.by("unit_of_measurement.unit_of_measurement_name");

            case "Supply Category":
                return Sort.by("supply_category.supply_category_name");

            default:
                return Sort.unsorted();
        }
    }

    public Map<String, Object> getAllSupplies(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        Page<Supply> supplyPage = supplyRepository
                .getAllPagedSupplies(pageable);

        Integer totalPages = supplyPage.getTotalPages();

        Map<String, Object> suppliesWithPageDetails = new HashMap<>();

        suppliesWithPageDetails.put("contents",
                supplyPage
                        .getContent()
                        .stream()
                        .map((Supply supply) -> convertEntityToDto(supply))
                        .collect(Collectors.toList()));

        suppliesWithPageDetails.put("totalPages", totalPages);

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        return suppliesWithPageDetails;
    }

    public void addSupply(SupplyDto supplyDto){
        String name = supplyDto.getSupplyName();

        Optional<Supply> supplyOptional = supplyRepository
                .getSupplyByName(name);

        if (supplyOptional.isPresent()){
            throw new SupplyNameIsExistingException(name);
        }

        supplyRepository.insertSupply(
                supplyDto.getSupplyName(),
                supplyDto.getSupplyQuantity(),
                supplyDto.getMinimumQuantity(),
                supplyDto.getInMinimumQuantity(),
                supplyDto.getSupplier().getSupplierId(),
                supplyDto.getUnitOfMeasurement().getUnitOfMeasurementId(),
                supplyDto.getSupplyCategory().getSupplyCategoryId(),
                supplyDto.getIsActive()
        );
    }

    public void updateSupply(SupplyDto supplyDto, Long id) {
        Supply supply = supplyRepository.getSupplyById(id)
                .orElseThrow(() -> new SupplyNotFoundException(id));

        String name = supplyDto.getSupplyName();
        Double quantity = supplyDto.getSupplyQuantity();
        Double minimumQuantity = supplyDto.getMinimumQuantity();
        Boolean inMinimumQuantity = supplyDto.getInMinimumQuantity();
        Supplier supplier = supplyDto.getSupplier();
        UnitOfMeasurement unitOfMeasurement = supplyDto.getUnitOfMeasurement();
        SupplyCategory supplyCategory = supplyDto.getSupplyCategory();
        Boolean active = supplyDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new SupplyNameIsNullException();
        }

        if (!Objects.equals(supply.getSupplyName(), name)){

            Optional<Supply> supplyOptional = supplyRepository
                    .getSupplyByName(name);

            if (supplyOptional.isPresent()){
                throw new SupplyNameIsExistingException(name);
            }

            supply.setSupplyName(name);
        }

        supply.setSupplyQuantity(quantity);
        supply.setMinimumQuantity(minimumQuantity);
        supply.setInMinimumQuantity(inMinimumQuantity);
        supply.setSupplier(supplier);
        supply.setUnitOfMeasurement(unitOfMeasurement);
        supply.setSupplyCategory(supplyCategory);
        supply.setIsActive(active);

    }
}
