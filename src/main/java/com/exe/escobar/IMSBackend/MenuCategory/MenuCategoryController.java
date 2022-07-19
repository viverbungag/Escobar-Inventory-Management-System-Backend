package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-category")
public class MenuCategoryController {

    @Autowired
    MenuCategoryService menuCategoryService;

    @GetMapping
    public List<MenuCategoryDto> getAllMenuCategories(){
        return menuCategoryService.getAllMenuCategories();
    }

}
