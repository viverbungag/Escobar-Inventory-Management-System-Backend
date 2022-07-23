package com.exe.escobar.IMSBackend.Supplier;

import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNameIsExistingException;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNameIsNullException;
import com.exe.escobar.IMSBackend.Supplier.Exceptions.SupplierNotFoundException;
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


    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository
                .getAllSuppliers()
                .stream()
                .map((Supplier supplier)-> convertEntityToDto(supplier))
                .collect(Collectors.toList());
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
