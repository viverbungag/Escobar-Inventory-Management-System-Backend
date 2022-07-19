package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("menuCategory_mysql")
public interface MenuCategoryRepository extends MenuCategoryDao, JpaRepository<MenuCategory, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    public List<MenuCategory> getAllMenuCategories();
}
