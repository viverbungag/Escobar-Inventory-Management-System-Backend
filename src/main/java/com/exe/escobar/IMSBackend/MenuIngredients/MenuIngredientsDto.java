package com.exe.escobar.IMSBackend.MenuIngredients;

import com.exe.escobar.IMSBackend.Supply.Supply;
import com.exe.escobar.IMSBackend.UnitOfMeasurement.UnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuIngredientsDto {

    private Long menuIngredientsId;
    private String supplyName;
    private Double quantity;
    private String unitOfMeasurementName;


}
