package com.exe.escobar.IMSBackend.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("transaction_mysql")
public interface TransactionMySqlRepository extends TransactionDao, JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<Transaction> getAllPagedTransactions(Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(transact_by, transaction_date, supplier_id, transaction_supply_quantity, supply_id, price_per_unit, expiry_date) " +
            "VALUES (:transactById, :transactionDate, :supplierId, :quantity, :supplyId, :pricePerUnit, :expiryDate)",
            nativeQuery = true)
    void insertTransaction(@Param("transactById") Long transactById,
                           @Param("transactionDate") LocalDateTime transactionDate,
                           @Param("supplierId") Long supplierId,
                           @Param("quantity") Double quantity,
                           @Param("supplyId") Long supplyId,
                           @Param("pricePerUnit") Double pricePerUnit,
                           @Param("expiryDate") LocalDateTime expiryDate);
}
