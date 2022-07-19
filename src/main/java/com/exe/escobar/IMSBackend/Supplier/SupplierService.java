package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
                supplier.getActive());
    }


    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository
                .getAllSuppliers()
                .stream()
                .map((Supplier supplier)-> convertEntityToDto(supplier))
                .collect(Collectors.toList());
    }

    public void addSupplier(SupplierDto supplierDto) {

        supplierRepository.insertSuppliers(
                supplierDto.getSupplierName(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierContactNumber(),
                supplierDto.getSupplierContactPerson(),
                supplierDto.getActive());
    }

    public void updateSupplier(SupplierDto supplierDto, Long id) {

        supplierRepository.updateSupplier(
                supplierDto.getSupplierName(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierContactNumber(),
                supplierDto.getSupplierContactPerson(),
                supplierDto.getActive(),
                id);
    }

    public List<SupplierDto> getAllActiveSuppliers() {
        return supplierRepository.getAllActiveSuppliers()
                .stream()
                .map((Supplier supplier)-> convertEntityToDto(supplier))
                .collect(Collectors.toList());
    }
}
