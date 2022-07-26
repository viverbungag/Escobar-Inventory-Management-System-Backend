package com.exe.escobar.IMSBackend.MenuIngredients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MenuIngredientsDao {

    Optional<MenuIngredients> getMenuIngredientsById(Long menuIngredientsId);
}
