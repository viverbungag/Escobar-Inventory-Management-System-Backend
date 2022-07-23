package com.exe.escobar.IMSBackend.MenuCategory;

import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNameIsExistingException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNameIsNullException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuCategoryService {

    @Autowired
    @Qualifier("menuCategory_mysql")
    MenuCategoryDao menuCategoryRepository;

    private MenuCategoryDto convertEntityToDto(MenuCategory menuCategory){
        return new MenuCategoryDto(
                menuCategory.getMenuCategoryId(),
                menuCategory.getMenuCategoryName(),
                menuCategory.getIsActive());
    }

    public List<MenuCategoryDto> getAllMenuCategories() {
        return menuCategoryRepository
                .getAllMenuCategories()
                .stream()
                .map((MenuCategory menuCategory)-> convertEntityToDto(menuCategory))
                .collect(Collectors.toList());
    }

    public void addMenuCategory(MenuCategoryDto menuCategoryDto) {
        String name = menuCategoryDto.getMenuCategoryName();

        Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository
                .getMenuCategoryByName(name);

        if (menuCategoryOptional.isPresent()){
            throw new MenuCategoryNameIsExistingException(name);
        }

        menuCategoryRepository.insertMenuCategory(
                menuCategoryDto.getMenuCategoryName(),
                menuCategoryDto.getIsActive()
        );
    }

    public void updateMenuCategory(MenuCategoryDto menuCategoryDto, Long id) {

        MenuCategory menuCategory = menuCategoryRepository.getMenuCategoryById(id)
                .orElseThrow(() -> new MenuCategoryNotFoundException(id));

        String name = menuCategoryDto.getMenuCategoryName();
        Boolean active = menuCategoryDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new MenuCategoryNameIsNullException();
        }

        if (!Objects.equals(menuCategory.getMenuCategoryName(), name)){

            Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository
                    .getMenuCategoryByName(name);

            if (menuCategoryOptional.isPresent()){
                throw new MenuCategoryNameIsExistingException(name);
            }

            menuCategory.setMenuCategoryName(name);
        }

        menuCategory.setIsActive(active);
    }
}
