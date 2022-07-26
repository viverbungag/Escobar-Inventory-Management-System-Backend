package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredients;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @NonNull
    @Column(name = "menu_name")
    private String menuName;

    @NonNull
    @Column(name = "menu_price")
    private BigDecimal menuPrice;

    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @OneToMany
    @JoinTable(name = "menu_ingredients",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_ingredients_id"))
    private List<MenuIngredients> menuIngredients;

    @NonNull
    @Column(name = "number_of_servings_left")
    private Integer numberOfServingsLeft;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
