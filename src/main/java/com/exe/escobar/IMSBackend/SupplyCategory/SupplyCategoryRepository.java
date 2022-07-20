package com.exe.escobar.IMSBackend.SupplyCategory;

import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("supplyCategory_mysql")
public interface SupplyCategoryRepository extends SupplyCategoryDao, JpaRepository<SupplyCategory, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    public List<SupplyCategory> getAllSupplyCategories();

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(supply_category_name, active) " +
            "VALUES (:supplyCategoryName, :active)",
            nativeQuery = true)
    void insertSupplyCategory(@Param("supplyCategoryName") String supplyCategoryName,
                              @Param("active") Boolean active);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE supply_category_id = :supplyCategoryId",
            nativeQuery = true)
    Optional<SupplyCategory> getSupplyCategoryById(@Param("supplyCategoryId") Long supplyCategoryId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE supply_category_name = :supplyCategoryName",
            nativeQuery = true)
    Optional<SupplyCategory> getSupplyCategoryByName(@Param("supplyCategoryName") String supplyCategoryName);
}
