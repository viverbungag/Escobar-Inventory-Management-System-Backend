package com.exe.escobar.IMSBackend.SupplyCategory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyCategoryDao {

    public List<SupplyCategory> getAllSupplyCategories();

    void insertSupplyCategory(String supplyCategoryName, Boolean isActive);

    Optional<SupplyCategory> getSupplyCategoryById(Long supplyCategoryId);

    Optional<SupplyCategory> getSupplyCategoryByName(String supplyCategoryName);
}
