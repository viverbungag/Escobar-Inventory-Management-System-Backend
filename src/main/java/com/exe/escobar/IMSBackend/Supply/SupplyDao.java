package com.exe.escobar.IMSBackend.Supply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyDao {

    Page<Supply> getAllPagedSupplies(Pageable pageable);

    Optional<Supply> getSupplyById(Long supplyId);

    Optional<Supply> getSupplyByName(String supplyName);

    void insertSupply(String supplyName,
                      Double supplyQuantity,
                      Double minimumQuantity,
                      Boolean inMinimumQuantity,
                      Long supplierId,
                      Long unitOfMeasurementId,
                      Long supplyCategoryId,
                      Boolean isActive);
}
