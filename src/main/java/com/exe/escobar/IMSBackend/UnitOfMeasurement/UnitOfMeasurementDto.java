package com.exe.escobar.IMSBackend.UnitOfMeasurement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Getter
@Setter
public class UnitOfMeasurementDto {

    private Long unitOfMeasurementId;
    private String unitOfMeasurementName;
    private String unitOfMeasurementAbbreviation;
    private Boolean active;
}
