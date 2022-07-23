package com.exe.escobar.IMSBackend.MenuCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MenuCategoryDto {

    private Long menuCategoryId;
    private String menuCategoryName;
    private Boolean isActive;

}
