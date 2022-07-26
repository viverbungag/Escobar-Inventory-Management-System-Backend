package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.Supply.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository("menu_mysql")
public interface MenuMySqlRepository extends MenuDao, JpaRepository<Menu, Long> {

    @Query(value = "SELECT * FROM #{#entityName} menu" +
            " INNER JOIN menu_category AS menu_category ON menu.menu_category_id = menu_category.menu_category_id",
            countQuery = "SELECT COUNT(*) FROM #{#entityName}",
            nativeQuery = true)
    Page<Menu> getAllMenu(Pageable pageable);

//    @Query(value = "SELECT * FROM #{#entityName} menu",
//            countQuery = "SELECT COUNT(*) FROM #{#entityName}",
//            nativeQuery = true)
//    Page<Menu> getAllMenu(Pageable pageable);

    @Modifying
    @Query(value ="INSERT INTO #{#entityName} " +
            "(menu_name, menu_price, menu_category_id, number_of_servings_left, is_active) " +
            "VALUES (:menuName, :menuPrice, :menuCategoryId, :numberOfServingsLeft, :isActive)",
            nativeQuery = true)
    void insertMenu(@Param("menuName")String menuName,
                    @Param("menuPrice") BigDecimal menuPrice,
                    @Param("menuCategoryId")Long menuCategoryId,
                    @Param("numberOfServingsLeft")Integer numberOfServingsLeft,
                    @Param("isActive")Boolean isActive);

    @Modifying
    @Query(value ="INSERT INTO menu_ingredients " +
            "(menu_id, supply_id, quantity) " +
            "VALUES (:menuId, :supplyId, :quantity)",
            nativeQuery = true)
    void insertIngredient(@Param("menuId")Long menuId,
                          @Param("supplyId")Long supplyId,
                          @Param("quantity")Integer quantity);


    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_id = :menuId",
            nativeQuery = true)
    Optional<Menu> getMenuById(@Param("menuId") Long menuId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_name = :menuName",
            nativeQuery = true)
    Optional<Menu> getMenuByName(@Param("menuName") String menuName);
}
