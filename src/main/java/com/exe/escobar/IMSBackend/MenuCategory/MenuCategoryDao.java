package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuCategoryDao {

    public List<MenuCategory> getAllMenuCategories();

    void insertMenuCategory(String menuCategoryName, Boolean active);
}
