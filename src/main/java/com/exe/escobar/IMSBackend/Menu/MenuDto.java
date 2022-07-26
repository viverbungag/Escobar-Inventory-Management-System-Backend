package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredients;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredientsDto;
import com.exe.escobar.IMSBackend.Supply.Supply;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuDto {

    private Long menuId;
    private String menuName;
    private BigDecimal menuPrice;
    private String menuCategoryName;
    private List<MenuIngredientsDto> ingredients;
    private Integer numberOfServingsLeft;
    private Boolean isActive;
}
