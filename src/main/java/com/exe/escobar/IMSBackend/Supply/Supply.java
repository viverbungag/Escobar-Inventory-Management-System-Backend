package com.exe.escobar.IMSBackend.Supply;

import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.SupplyCategory.SupplyCategory;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.UnitOfMeasurement;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "supply")
public class Supply {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "supply_id")
    private Long supplyId;

    @NonNull
    @Column(name = "supply_name")
    private String supplyName;

    @NonNull
    @Column(name = "supply_quantity")
    private Double supplyQuantity;

    @NonNull
    @Column(name = "minimum_quantity")
    private Double minimumQuantity;

    @Transient
    private Boolean inMinimumQuantity;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "unit_of_measurement_id")
    private UnitOfMeasurement unitOfMeasurement;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "supply_category_id")
    private SupplyCategory supplyCategory;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

    public Boolean getInMinimumQuantity() {
        return supplyQuantity <= minimumQuantity;
    }

}
