package com.exe.escobar.IMSBackend.Supply;

import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.SupplyCategory.SupplyCategory;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.UnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplyDto {

    private Long supplyId;
    private String supplyName;
    private Double supplyQuantity;
    private Double minimumQuantity;
    private Boolean inMinimumQuantity;
    private Supplier supplier;
    private UnitOfMeasurement unitOfMeasurement;
    private SupplyCategory supplyCategory;
    private Boolean isActive;
}
