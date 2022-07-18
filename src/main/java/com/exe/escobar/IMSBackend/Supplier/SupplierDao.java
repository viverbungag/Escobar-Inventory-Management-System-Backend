package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierDao {

    List<Supplier> getAllSuppliers();

    void insertSuppliers(String supplierName,
                         String supplierAddress,
                         String supplierContactNumber,
                         String supplierContactPerson,
                         Boolean active);

    void updateSupplier(
            String supplierName,
            String supplierAddress,
            String supplierContactNumber,
            String supplierContactPerson,
            Boolean active,
            Long id);
}
