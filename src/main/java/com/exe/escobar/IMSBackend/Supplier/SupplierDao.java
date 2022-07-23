package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierDao {

    Page<Supplier> getAllSuppliers(Pageable pageable);

    void insertSupplier(String supplierName,
                        String supplierAddress,
                        String supplierContactNumber,
                        String supplierContactPerson,
                        Boolean isActive);

    List<Supplier> getAllActiveSuppliers();

    Optional<Supplier> getSupplierById(Long supplierId);

    Optional<Supplier> getSupplierByName(String supplierName);
}
