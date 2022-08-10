package com.exe.escobar.IMSBackend.Transaction;

import com.exe.escobar.IMSBackend.Employee.Employee;
import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.Supply.Supply;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDto {

    private Long transactionId;
    private String transactByName;
    private LocalDateTime transactionDate;
    private String supplierName;
    private Double supplyQuantity;
    private String supplyName;
    private String unitOfMeasurementAbbreviation;
    private Double pricePerUnit;
    private LocalDateTime expiryDate;
}
