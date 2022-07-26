package com.exe.escobar.IMSBackend.MenuIngredients;


import com.exe.escobar.IMSBackend.MenuCategory.MenuCategoryDao;
import com.exe.escobar.IMSBackend.Supply.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("menuIngredients_mysql")
public interface MenuIngredientsMySqlRepository extends MenuIngredientsDao, JpaRepository<MenuIngredients, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE menu_ingredients_id = :menuIngredientsId",
            nativeQuery = true)
    Optional<MenuIngredients> getMenuIngredientsById(@Param("menuIngredientsId") Long menuIngredientsId);
}
