package com.exe.escobar.IMSBackend.Transaction;


import com.exe.escobar.IMSBackend.Employee.Employee;
import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.Supply.Supply;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "transact_by")
    private Employee transactBy;

    @NonNull
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @NonNull
    @Column(name = "transaction_supply_quantity")
    private Double supplyQuantity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @NonNull
    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @NonNull
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
}
