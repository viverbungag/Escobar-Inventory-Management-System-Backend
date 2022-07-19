package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierDao {

    List<Supplier> getAllSuppliers();

    void insertSupplier(String supplierName,
                        String supplierAddress,
                        String supplierContactNumber,
                        String supplierContactPerson,
                        Boolean active);

    List<Supplier> getAllActiveSuppliers();

    Optional<Supplier> getSupplierById(Long supplierId);

    Optional<Supplier> getSupplierByName(String supplierName);
}
