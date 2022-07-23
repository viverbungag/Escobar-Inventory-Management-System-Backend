package com.exe.escobar.IMSBackend.Supplier;

import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNameIsExistingException;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNameIsNullException;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNotFoundException;
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
public class SupplierService {

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;

    private SupplierDto convertEntityToDto(Supplier supplier){
        return new SupplierDto(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getSupplierAddress(),
                supplier.getSupplierContactNumber(),
                supplier.getSupplierContactPerson(),
                supplier.getIsActive());
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
                return Sort.by("supplier_name");
            case "Address":
                return Sort.by("supplier_address");
            case "Contact Number":
                return Sort.by("supplier_contact_number");
            case "Contact Person":
                return Sort.by("supplier_contact_person");
            default:
                return Sort.unsorted();
        }
    }


    public Map<String, Object> getAllSuppliers(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        Page<Supplier> supplierPage = supplierRepository
                .getAllSuppliers(pageable);

        Integer totalPages = supplierPage.getTotalPages();

        Map<String, Object> suppliersWithPageDetails = new HashMap<>();

        suppliersWithPageDetails.put("contents",
                supplierPage
                        .getContent()
                        .stream()
                        .map((Supplier supplier) -> convertEntityToDto(supplier))
                        .collect(Collectors.toList()));

        suppliersWithPageDetails.put("totalPages", totalPages);

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        return suppliersWithPageDetails;
    }

    public void addSupplier(SupplierDto supplierDto) {
        String name = supplierDto.getSupplierName();

        Optional<Supplier> supplierOptional =  supplierRepository
                .getSupplierByName(name);

        if (supplierOptional.isPresent()){
            throw new SupplierNameIsExistingException(name);
        }

        supplierRepository.insertSupplier(
                supplierDto.getSupplierName(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierContactNumber(),
                supplierDto.getSupplierContactPerson(),
                supplierDto.getIsActive());
    }

    public void updateSupplier(SupplierDto supplierDto, Long id) {

        Supplier supplier = supplierRepository.getSupplierById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));

        String name = supplierDto.getSupplierName();
        String address = supplierDto.getSupplierAddress();
        String contactNumber = supplierDto.getSupplierContactNumber();
        String contactPerson = supplierDto.getSupplierContactPerson();
        Boolean active = supplierDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new SupplierNameIsNullException();
        }


        if (!Objects.equals(supplier.getSupplierName(), name)){

            Optional<Supplier> supplierOptional =  supplierRepository
                    .getSupplierByName(name);

            if (supplierOptional.isPresent()){
                throw new SupplierNameIsExistingException(name);
            }

            supplier.setSupplierName(name);
        }


        supplier.setSupplierAddress(address);
        supplier.setSupplierContactNumber(contactNumber);
        supplier.setSupplierContactPerson(contactPerson);
        supplier.setIsActive(active);

    }

    public List<SupplierDto> getAllActiveSuppliers() {
        return supplierRepository.getAllActiveSuppliers()
                .stream()
                .map((Supplier supplier)-> convertEntityToDto(supplier))
                .collect(Collectors.toList());
    }
}
