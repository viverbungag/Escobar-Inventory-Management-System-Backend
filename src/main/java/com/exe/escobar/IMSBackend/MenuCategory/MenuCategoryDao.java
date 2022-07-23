package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryDao {

    void insertMenuCategory(String menuCategoryName, Boolean isActive);

    Optional<MenuCategory> getMenuCategoryById(Long menuCategoryId);

    Optional<MenuCategory> getMenuCategoryByName(String menuCategoryName);

    Page<MenuCategory> getAllMenuCategories(Pageable pageable);
}
