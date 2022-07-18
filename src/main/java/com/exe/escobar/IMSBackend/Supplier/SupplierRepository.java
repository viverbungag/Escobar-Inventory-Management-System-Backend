package com.exe.escobar.IMSBackend.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;


@Repository("supplier_mysql")
public interface SupplierRepository extends SupplierDao, JpaRepository<Supplier, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    List<Supplier> getAllSuppliers();
}
