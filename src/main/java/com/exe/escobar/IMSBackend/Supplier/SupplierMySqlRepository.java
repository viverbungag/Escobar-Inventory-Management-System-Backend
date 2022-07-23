package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("supplier_mysql")
public interface SupplierMySqlRepository extends SupplierDao, JpaRepository<Supplier, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    List<Supplier> getAllSuppliers();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active = true",
            nativeQuery = true)
    List<Supplier> getAllActiveSuppliers();

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supplier_name, supplier_address, supplier_contact_number, supplier_contact_person, is_active) " +
            "VALUES (:supplierName, :supplierAddress, :supplierContactNumber, :supplierContactPerson, :isActive)",
            nativeQuery = true)
    void insertSupplier(@Param("supplierName") String supplierName,
                        @Param("supplierAddress") String supplierAddress,
                        @Param("supplierContactNumber") String supplierContactNumber,
                        @Param("supplierContactPerson") String supplierContactPerson,
                        @Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supplier_id = :supplierId",
            nativeQuery = true)
    Optional<Supplier> getSupplierById(@Param("supplierId") Long supplierId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE supplier_name = :supplierName",
            nativeQuery = true)
    Optional<Supplier> getSupplierByName(@Param("supplierName") String supplierName);




}
