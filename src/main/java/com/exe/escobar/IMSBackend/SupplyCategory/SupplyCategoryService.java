package com.exe.escobar.IMSBackend.SupplyCategory;

import com.exe.escobar.IMSBackend.Pagination.Exceptions.PageOutOfBoundsException;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNameIsExistingException;
import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNameIsNullException;
import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNotFoundException;
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
public class SupplyCategoryService {

    @Autowired
    @Qualifier("supplyCategory_mysql")
    SupplyCategoryDao supplyCategoryRepository;

    private SupplyCategoryDto convertEntityToDto(SupplyCategory supplyCategory){
        return new SupplyCategoryDto(supplyCategory.getSupplyCategoryId(),
                supplyCategory.getSupplyCategoryName(),
                supplyCategory.getIsActive());
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
                return Sort.by("supply_category_name");
            default:
                return Sort.unsorted();
        }
    }

    public Map<String, Object> getAllSupplyCategories(PaginationDto paginationDto){
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        Page<SupplyCategory> supplyCategoryPage = supplyCategoryRepository
                .getAllSupplyCategories(pageable);

        Integer totalPages = supplyCategoryPage.getTotalPages();

        Map<String, Object> supplyCategoryWithPageDetails = new HashMap<>();

        supplyCategoryWithPageDetails.put("contents",
                supplyCategoryPage
                        .getContent()
                        .stream()
                        .map((SupplyCategory supplyCategory) -> convertEntityToDto(supplyCategory))
                        .collect(Collectors.toList()));

        supplyCategoryWithPageDetails.put("totalPages", totalPages);

        if (pageNo < 1 || pageNo > totalPages){
            throw new PageOutOfBoundsException(pageNo);
        }

        return supplyCategoryWithPageDetails;
    }

    public void addSupplyCategory(SupplyCategoryDto supplyCategoryDto) {
        String name = supplyCategoryDto.getSupplyCategoryName();

        Optional<SupplyCategory> supplyCategoryOptional = supplyCategoryRepository
                .getSupplyCategoryByName(name);

        if (supplyCategoryOptional.isPresent()){
            throw new SupplyCategoryNameIsExistingException(name);
        }

        supplyCategoryRepository.insertSupplyCategory(
                supplyCategoryDto.getSupplyCategoryName(),
                supplyCategoryDto.getIsActive());
    }

    public void updateSupplyCategory(SupplyCategoryDto supplyCategoryDto, Long id) {

        SupplyCategory supplyCategory = supplyCategoryRepository.getSupplyCategoryById(id)
                .orElseThrow(() -> new SupplyCategoryNotFoundException(id));

        String name = supplyCategoryDto.getSupplyCategoryName();
        Boolean active = supplyCategoryDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new SupplyCategoryNameIsNullException();
        }

        if (!Objects.equals(supplyCategory.getSupplyCategoryName(), name)){

            Optional<SupplyCategory> supplyCategoryOptional = supplyCategoryRepository
                    .getSupplyCategoryByName(name);

            if (supplyCategoryOptional.isPresent()){
                throw new SupplyCategoryNameIsExistingException(name);
            }

            supplyCategory.setSupplyCategoryName(name);
        }

        supplyCategory.setIsActive(active);
    }
}
