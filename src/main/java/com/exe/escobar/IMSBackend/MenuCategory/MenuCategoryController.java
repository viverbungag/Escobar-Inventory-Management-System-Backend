package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public void addMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto){
        menuCategoryService.addMenuCategory(menuCategoryDto);
    }

    @PutMapping("/update/{id}")
    public void updateMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto, @PathVariable Long id){
        menuCategoryService.updateMenuCategory(menuCategoryDto, id);
    }

}
