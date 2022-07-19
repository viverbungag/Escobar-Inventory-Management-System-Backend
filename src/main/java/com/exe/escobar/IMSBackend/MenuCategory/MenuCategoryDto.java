package com.exe.escobar.IMSBackend.MenuCategory;

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
public class MenuCategoryDto {

    private Long menuCategoryId;
    private String menuCategoryName;
    private Boolean active;

}
