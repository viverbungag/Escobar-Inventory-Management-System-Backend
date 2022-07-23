package com.exe.escobar.IMSBackend.SupplyCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyCategoryDao {

    Page<SupplyCategory> getAllSupplyCategories(Pageable pageable);

    void insertSupplyCategory(String supplyCategoryName, Boolean isActive);

    Optional<SupplyCategory> getSupplyCategoryById(Long supplyCategoryId);

    Optional<SupplyCategory> getSupplyCategoryByName(String supplyCategoryName);
}
