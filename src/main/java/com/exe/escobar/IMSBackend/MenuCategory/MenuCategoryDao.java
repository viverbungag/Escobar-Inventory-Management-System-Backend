package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryDao {

    public List<MenuCategory> getAllMenuCategories();

    void insertMenuCategory(String menuCategoryName, Boolean isActive);

    Optional<MenuCategory> getMenuCategoryById(Long menuCategoryId);

    Optional<MenuCategory> getMenuCategoryByName(String menuCategoryName);
}
