package com.exe.escobar.IMSBackend.Menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuDao {

    Page<Menu> getAllMenu(Pageable pageable);

    void insertMenu(String menuName,
                    BigDecimal menuPrice,
                    Long menuCategoryId,
                    Boolean isActive);

    void insertIngredient(Long menuId, Long supplyId, Double quantity);

    Optional<Menu> getMenuById(Long menuId);

    Optional<Menu> getMenuByName(String menuName);

    Page<Menu> getAllActiveMenu(Pageable pageable);

    Page<Menu> getAllInactiveMenu(Pageable pageable);

    void inactivateMenu(List<String> menuNames);

    void activateMenu(List<String> menuNames);
}
