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
    private String supplierName;
    private String unitOfMeasurementName;
    private String supplyCategoryName;
    private Boolean isActive;

    public Boolean getInMinimumQuantity() {
        return supplyQuantity <= minimumQuantity;
    }

}
