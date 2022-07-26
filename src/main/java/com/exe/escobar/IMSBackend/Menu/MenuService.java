package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNameIsExistingException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNameIsNullException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNotFoundException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuPriceIsNullException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNameIsNullException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNotFoundException;
import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import com.exe.escobar.IMSBackend.MenuCategory.MenuCategoryDao;
import com.exe.escobar.IMSBackend.MenuIngredients.Exceptions.MenuIngredientsNotFoundException;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredients;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredientsDao;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredientsDto;
import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.Supply.Exceptions.SupplyNotFoundException;
import com.exe.escobar.IMSBackend.Supply.Supply;
import com.exe.escobar.IMSBackend.Supply.SupplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {

    @Autowired
    @Qualifier("menu_mysql")
    MenuDao menuRepository;

    @Autowired
    @Qualifier("menuCategory_mysql")
    MenuCategoryDao menuCategoryRepository;

    @Autowired
    @Qualifier("supply_mysql")
    SupplyDao supplyRepository;

    @Autowired
    @Qualifier("menuIngredients_mysql")
    MenuIngredientsDao menuIngredientsRepository;

    private MenuDto convertEntityToDto(Menu menu){
        return new MenuDto(
            menu.getMenuId(),
            menu.getMenuName(),
            menu.getMenuPrice(),
            menu.getMenuCategory().getMenuCategoryName(),

            menu.getMenuIngredients()
                    .stream()
                    .map((MenuIngredients menuIngredients) ->
                            new MenuIngredientsDto(
                                    menuIngredients.getMenuIngredientsId(),
                                    menuIngredients.getSupply().getSupplyName(),
                                    menuIngredients.getQuantity()))
                    .collect(Collectors.toList()),

            menu.getNumberOfServingsLeft(),
            menu.getIsActive()
        );
    }

    private Sort getSortingMethod(Boolean isAscending, Sort sort){
        if (isAscending){
            return sort.ascending();
        }
        return sort.descending();
    }

    private Sort getSortingValue(String sortedBy){

        switch(sortedBy){
            case "Name":
                return Sort.by("menu_name");

            case "Price":
                return Sort.by("menu_price");

            case "Number of Servings left":
                return Sort.by("number_of_servings_left");

            case "Menu Category":
                return Sort.by("menu_category.menu_category_name");

            default:
                return Sort.unsorted();
        }
    }

    public Map<String, Object> getAllMenu(PaginationDto paginationDto){
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        Page<Menu> menuPage = menuRepository
                .getAllMenu(pageable);

        Integer totalPages = menuPage.getTotalPages();

        Map<String, Object> menuWithPageDetails = new HashMap<>();

        menuWithPageDetails.put("contents",
                menuPage
                        .getContent()
                        .stream()
                        .map((Menu menu) -> convertEntityToDto(menu))
                        .collect(Collectors.toList()));

        menuWithPageDetails.put("totalPages", totalPages);

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        return menuWithPageDetails;
    }

    public void addMenu(MenuDto menuDto){
        String name = menuDto.getMenuName();

        Optional<Menu> menuOptional = menuRepository
                .getMenuByName(name);

        if (menuOptional.isPresent()){
            throw new MenuNameIsExistingException(name);
        }

        MenuCategory menuCategory = menuCategoryRepository
                .getMenuCategoryByName(menuDto.getMenuCategoryName())
                .orElseThrow(() -> new IllegalStateException());

        menuRepository.insertMenu(
                name,
                menuDto.getMenuPrice(),
                menuCategory.getMenuCategoryId(),
                menuDto.getNumberOfServingsLeft(),
                menuDto.getIsActive());

        Menu currentMenu = menuRepository
                .getMenuByName(name)
                .orElseThrow(() -> new MenuNotFoundException(name));

        menuDto
            .getIngredients()
            .stream()
            .forEach((MenuIngredientsDto menuIngredientsDto) ->
                    menuRepository.insertIngredient(
                            currentMenu.getMenuId(),
                            supplyRepository.getSupplyByName(menuIngredientsDto.getSupplyName())
                                    .orElseThrow(() -> new SupplyNotFoundException(menuIngredientsDto.getSupplyName())).getSupplyId(),
                            menuIngredientsDto.getQuantity()));
    }

    public void updateMenu(MenuDto menuDto, Long id){
        Menu menu = menuRepository.getMenuById(id)
                .orElseThrow(() -> new MenuNotFoundException(id));

        String name = menuDto.getMenuName();
        BigDecimal price = menuDto.getMenuPrice();

        MenuCategory menuCategory = menuCategoryRepository
                .getMenuCategoryByName(menuDto.getMenuCategoryName())
                .orElseThrow(() -> new MenuCategoryNotFoundException(name));

        menuDto
            .getIngredients()
            .stream()
            .forEach((MenuIngredientsDto menuIngredientsDto) -> {
                Long currentId = menuIngredientsDto.getMenuIngredientsId();
                Integer updatedQuantity = menuIngredientsDto.getQuantity();

                MenuIngredients menuIngredients = menuIngredientsRepository.getMenuIngredientsById(currentId)
                        .orElseThrow(()-> new MenuIngredientsNotFoundException(currentId));

                Supply updatedSupply = supplyRepository.getSupplyByName(menuIngredientsDto
                                .getSupplyName())
                        .orElseThrow(() -> new SupplyNotFoundException(menuIngredientsDto.getSupplyName()));

                menuIngredients.setSupply(updatedSupply);
                menuIngredients.setQuantity(updatedQuantity);
            });

        Integer numberOfServingsLeft = menuDto.getNumberOfServingsLeft();
        Boolean isActive = menuDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new MenuNameIsNullException();
        }

        if (price == null){
            throw new MenuPriceIsNullException();
        }

        if (!Objects.equals(menu.getMenuName(), name)){

            Optional<Menu> menuOptional = menuRepository
                    .getMenuByName(name);

            if (menuOptional.isPresent()){
                throw new MenuNameIsExistingException(name);
            }

            menu.setMenuName(name);
        }

        menu.setMenuPrice(price);
        menu.setMenuCategory(menuCategory);
//        menu.setMenuIngredients(ingredients);
        menu.setNumberOfServingsLeft(numberOfServingsLeft);
        menu.setIsActive(isActive);
    }
}
