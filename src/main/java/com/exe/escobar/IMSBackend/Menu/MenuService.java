package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNameIsExistingException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNameIsNullException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuNotFoundException;
import com.exe.escobar.IMSBackend.Menu.Exceptions.MenuPriceIsNotValidException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNotFoundException;
import com.exe.escobar.IMSBackend.MenuCategory.MenuCategory;
import com.exe.escobar.IMSBackend.MenuCategory.MenuCategoryDao;
import com.exe.escobar.IMSBackend.MenuIngredients.Exceptions.MenuIngredientQuantityHasInvalidValueException;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredients;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredientsDao;
import com.exe.escobar.IMSBackend.MenuIngredients.MenuIngredientsDto;
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
import java.util.concurrent.atomic.AtomicReference;
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
                                    menuIngredients.getQuantity(),
                                    menuIngredients.getSupply().getUnitOfMeasurement().getUnitOfMeasurementAbbreviation()))
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

            case "Menu Category":
                return Sort.by("menu_category.menu_category_name");

            case "None":
                return Sort.by("menu_id");

            default:
                return Sort.unsorted();
        }
    }

    private Pageable initializePageable(PaginationDto paginationDto){
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        return pageable;
    }

    private Integer calculateNumberOfServingsLeft (Menu menu) {
        Integer currentNumberOfServings = null;

        if (menu.getMenuIngredients().size() == 0){
            return 0;
        }

        for (MenuIngredients ingredient: menu.getMenuIngredients()){
            Double ingredientQuantity = ingredient.getQuantity();
            Double supplyQuantity = ingredient.getSupply().getSupplyQuantity();

            Integer ingredientAvailableServings = Integer.valueOf((int)Math.floor(supplyQuantity / ingredientQuantity));

            if (currentNumberOfServings == null || ingredientAvailableServings < currentNumberOfServings){
                currentNumberOfServings = ingredientAvailableServings;
            }

            if (ingredientAvailableServings <= 0){
                currentNumberOfServings = 0;
            }
        }

        return currentNumberOfServings;
    }

    private Map<String, Object> initializeMenuWithPageDetails(Page<Menu> menuPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = menuPage.getTotalPages();
        Long totalCount = menuPage.getTotalElements();

        Map<String, Object> menuWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            menuWithPageDetails.put("contents", new ArrayList<>());
            menuWithPageDetails.put("totalPages", 0);
            menuWithPageDetails.put("totalCount", 0);
            return menuWithPageDetails;
        }

        menuWithPageDetails.put("contents",
                menuPage
                        .getContent()
                        .stream()
                        .map((Menu menu)-> {
                            menu.setNumberOfServingsLeft(calculateNumberOfServingsLeft(menu));
                            return convertEntityToDto(menu);
                        })
                        .collect(Collectors.toList()));



        menuWithPageDetails.put("totalPages", totalPages);
        menuWithPageDetails.put("totalCount", totalCount);
        return menuWithPageDetails;
    }


    public Map<String, Object> getAllMenu(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<Menu> menuPage = menuRepository
                .getAllMenu(pageable);

        return initializeMenuWithPageDetails(menuPage, paginationDto);
    }

    public Map<String, Object> getAllActiveMenu(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<Menu> menuPage = menuRepository
                .getAllActiveMenu(pageable);

        return initializeMenuWithPageDetails(menuPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveMenu(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<Menu> menuPage = menuRepository
                .getAllInactiveMenu(pageable);

        return initializeMenuWithPageDetails(menuPage, paginationDto);
    }

    public void addMenu(MenuDto menuDto){
        String name = menuDto.getMenuName();
        BigDecimal price = menuDto.getMenuPrice();

        if (name == null || name.length() <= 0){
            throw new MenuNameIsNullException();
        }

        if (price == null || price.compareTo(new BigDecimal(0)) < 0){
            throw new MenuPriceIsNotValidException();
        }

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

    public void inactivateMenu(MenuListDto menuListDto){
        List<String> menuNames = menuListDto
                .getMenuListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuName())
                .collect(Collectors.toList());

        menuRepository.inactivateMenu(menuNames);
    }

    public void activateMenu(MenuListDto menuListDto){
        List<String> menuNames = menuListDto
                .getMenuListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuName())
                .collect(Collectors.toList());

        menuRepository.activateMenu(menuNames);
    }

    public void updateMenu(MenuDto menuDto, Long id){
        Menu menu = menuRepository.getMenuById(id)
                .orElseThrow(() -> new MenuNotFoundException(id));

        String name = menuDto.getMenuName();
        BigDecimal price = menuDto.getMenuPrice();

        if (name == null || name.length() <= 0){
            throw new MenuNameIsNullException();
        }

        if (price == null || price.compareTo(new BigDecimal(0)) < 0){
            throw new MenuPriceIsNotValidException();
        }

        if (!Objects.equals(menu.getMenuName(), name)){

            Optional<Menu> menuOptional = menuRepository
                    .getMenuByName(name);

            if (menuOptional.isPresent()){
                throw new MenuNameIsExistingException(name);
            }

            menu.setMenuName(name);
        }

        MenuCategory menuCategory = menuCategoryRepository
                .getMenuCategoryByName(menuDto.getMenuCategoryName())
                .orElseThrow(() -> new MenuCategoryNotFoundException(name));

        List<MenuIngredients> ingredients = menuDto
                .getIngredients()
                .stream()
                .map((ingredient)-> {

                    if (ingredient.getQuantity() <= 0){
                        throw new MenuIngredientQuantityHasInvalidValueException(ingredient.getSupplyName());
                    }

                    Supply currentSupply = supplyRepository.getSupplyByName(ingredient.getSupplyName())
                            .orElseThrow(() -> new SupplyNotFoundException(ingredient.getSupplyName()));


                    MenuIngredients newIngredient = new MenuIngredients(menu, ingredient.getQuantity(), currentSupply);


                    return newIngredient;

                })
                .collect(Collectors.toList());

        menuIngredientsRepository.deleteAllMenuIngredientsByMenuId(id);

        Boolean isActive = menuDto.getIsActive();

        menu.setMenuPrice(price);
        menu.setMenuCategory(menuCategory);
        menu.setMenuIngredients(ingredients);
        menu.setIsActive(isActive);
    }
}
