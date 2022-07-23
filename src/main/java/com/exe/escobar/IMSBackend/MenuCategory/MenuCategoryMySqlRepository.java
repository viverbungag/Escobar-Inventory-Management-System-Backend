package com.exe.escobar.IMSBackend.MenuCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("menuCategory_mysql")
public interface MenuCategoryMySqlRepository extends MenuCategoryDao, JpaRepository<MenuCategory, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<MenuCategory> getAllMenuCategories(Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(menu_category_name, is_active) " +
            "VALUES (:menuCategoryName, :isActive)",
            nativeQuery = true)
    void insertMenuCategory(@Param("menuCategoryName") String menuCategoryName,
                            @Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE menu_category_id = :menuCategoryId",
            nativeQuery = true)
    Optional<MenuCategory> getMenuCategoryById(@Param("menuCategoryId") Long menuCategoryId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE menu_category_name = :menuCategoryName",
            nativeQuery = true)
    Optional<MenuCategory> getMenuCategoryByName(@Param("menuCategoryName") String menuCategoryName);
}
