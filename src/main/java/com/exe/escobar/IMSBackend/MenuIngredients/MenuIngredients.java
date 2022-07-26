package com.exe.escobar.IMSBackend.MenuIngredients;


import com.exe.escobar.IMSBackend.Menu.Menu;
import com.exe.escobar.IMSBackend.Supply.Supply;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "menu_ingredients")
public class MenuIngredients {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_ingredients_id")
    private Long menuIngredientsId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

}
