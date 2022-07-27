package com.exe.escobar.IMSBackend.MenuCategory;

import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNameIsExistingException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNameIsNullException;
import com.exe.escobar.IMSBackend.MenuCategory.Exceptions.MenuCategoryNotFoundException;
import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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

    private Sort getSortingMethod(Boolean isAscending, Sort sort){
        if (isAscending){
            return sort.ascending();
        }
        return sort.descending();
    }

    private Sort getSortingValue(String sortedBy){

        switch(sortedBy){
            case "Name":
                return Sort.by("menu_category_name");
            case "None":
                return Sort.by("menu_category_id");
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

    private Map<String, Object> initializeMenuCategoryWithPageDetails(Page<MenuCategory> menuCategoryPage, int pageNo){
        Integer totalPages = menuCategoryPage.getTotalPages();
        Long totalCount = menuCategoryPage.getTotalElements();

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        Map<String, Object> menuCategoryWithPageDetails = new HashMap<>();

        menuCategoryWithPageDetails.put("contents",
                menuCategoryPage
                        .getContent()
                        .stream()
                        .map((MenuCategory menuCategory)-> convertEntityToDto(menuCategory))
                        .collect(Collectors.toList()));

        menuCategoryWithPageDetails.put("totalPages", totalPages);
        menuCategoryWithPageDetails.put("totalCount", totalCount);



        return menuCategoryWithPageDetails;
    }

    public Map<String, Object> getAllMenuCategories(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        Pageable pageable = initializePageable(paginationDto);

        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, pageNo);
    }

    public Map<String, Object> getAllActiveMenuCategories(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        Pageable pageable = initializePageable(paginationDto);

        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllActiveMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, pageNo);
    }

    public Map<String, Object> getAllInactiveMenuCategories(PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        Pageable pageable = initializePageable(paginationDto);

        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllInactiveMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, pageNo);
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

    public void inactivateMenuCategory(MenuCategoryListDto menuCategoryListDto){
        List<String> menuCategoryNames = menuCategoryListDto
                .getMenuCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuCategoryName())
                .collect(Collectors.toList());

        menuCategoryRepository.inactivateMenuCategory(menuCategoryNames);
    }

    public void activateMenuCategory(MenuCategoryListDto menuCategoryListDto){
        List<String> menuCategoryNames = menuCategoryListDto
                .getMenuCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuCategoryName())
                .collect(Collectors.toList());

        menuCategoryRepository.activateMenuCategory(menuCategoryNames);
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
