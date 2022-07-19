package com.exe.escobar.IMSBackend.MenuCategory;

import com.exe.escobar.IMSBackend.Supplier.Supplier;
import com.exe.escobar.IMSBackend.Supplier.SupplierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuCategoryService {

    @Autowired
    @Qualifier("menuCategory_mysql")
    MenuCategoryDao menuCategoryRepository;

    private MenuCategoryDto convertEntityToDto(MenuCategory menuCategory){
        return new MenuCategoryDto(
                menuCategory.getMenuCategoryId(),
                menuCategory.getMenuCategoryName(),
                menuCategory.getActive());
    }

    public List<MenuCategoryDto> getAllMenuCategories() {
        return menuCategoryRepository
                .getAllMenuCategories()
                .stream()
                .map((MenuCategory menuCategory)-> convertEntityToDto(menuCategory))
                .collect(Collectors.toList());
    }
}
