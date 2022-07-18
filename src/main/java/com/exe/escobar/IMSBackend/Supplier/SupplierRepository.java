package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;


@Repository("supplier_mysql")
public interface SupplierRepository extends SupplierDao, JpaRepository<Supplier, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE active = true",
            nativeQuery = true)
    List<Supplier> getAllSuppliers();

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supplier_name, supplier_address, supplier_contact_number, supplier_contact_person, active) " +
            "VALUES (:supplierName, :supplierAddress, :supplierContactNumber, :supplierContactPerson, :active)",
            nativeQuery = true)
    void insertSuppliers(@Param("supplierName") String supplierName,
                         @Param("supplierAddress") String supplierAddress,
                         @Param("supplierContactNumber") String supplierContactNumber,
                         @Param("supplierContactPerson") String supplierContactPerson,
                         @Param("active") Boolean active);

    @Modifying
    @Query(value = "UPDATE #{#entityName} " +
            "SET supplier_name = :supplierName, " +
                "supplier_address = :supplierAddress, " +
                "supplier_contact_number = :supplierContactNumber, " +
                "supplier_contact_person = :supplierContactPerson, " +
                "active = :active " +
            "WHERE supplier_id = :supplierId",
            nativeQuery = true)
    void updateSupplier(@Param("supplierName") String supplierName,
                        @Param("supplierAddress") String supplierAddress,
                        @Param("supplierContactNumber") String supplierContactNumber,
                        @Param("supplierContactPerson") String supplierContactPerson,
                        @Param("active") Boolean active,
                        @Param("supplierId") Long id);




}
