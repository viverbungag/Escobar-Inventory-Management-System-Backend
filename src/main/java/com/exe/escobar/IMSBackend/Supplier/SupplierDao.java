package com.exe.escobar.IMSBackend.Supplier;

import com.exe.escobar.IMSBackend.SupplyCategory.SupplyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    Optional<Supplier> getSupplierById(Long supplierId);

    Optional<Supplier> getSupplierByName(String supplierName);

    Page<Supplier> getAllActiveSuppliers(Pageable pageable);

    Page<Supplier> getAllInactiveSuppliers(Pageable pageable);

    void inactivateSupplier(List<String> supplierNames);

    void activateSupplier(List<String> supplierNames);

    List<Supplier> getAllActiveSupplierList();

}
