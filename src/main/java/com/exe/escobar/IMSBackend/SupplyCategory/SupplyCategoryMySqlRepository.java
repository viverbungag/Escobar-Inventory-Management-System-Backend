package com.exe.escobar.IMSBackend.SupplyCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("supplyCategory_mysql")
public interface SupplyCategoryMySqlRepository extends SupplyCategoryDao, JpaRepository<SupplyCategory, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<SupplyCategory> getAllSupplyCategories(Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supply_category_name, is_active) " +
            "VALUES (:supplyCategoryName, :isActive)",
            nativeQuery = true)
    void insertSupplyCategory(@Param("supplyCategoryName") String supplyCategoryName,
                              @Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE supply_category_id = :supplyCategoryId",
            nativeQuery = true)
    Optional<SupplyCategory> getSupplyCategoryById(@Param("supplyCategoryId") Long supplyCategoryId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE supply_category_name = :supplyCategoryName",
            nativeQuery = true)
    Optional<SupplyCategory> getSupplyCategoryByName(@Param("supplyCategoryName") String supplyCategoryName);
}
