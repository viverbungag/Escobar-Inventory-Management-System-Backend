package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class SupplierService {

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;


    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
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
}
