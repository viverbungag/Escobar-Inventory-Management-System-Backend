package com.exe.escobar.IMSBackend.SupplyCategory;

import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNameIsExistingException;
import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNameIsNullException;
import com.exe.escobar.IMSBackend.SupplyCategory.Exceptions.SupplyCategoryNotFoundException;
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
public class SupplyCategoryService {

    @Autowired
    @Qualifier("supplyCategory_mysql")
    SupplyCategoryDao supplyCategoryRepository;

    private SupplyCategoryDto convertEntityToDto(SupplyCategory supplyCategory){
        return new SupplyCategoryDto(supplyCategory.getSupplyCategoryId(),
                supplyCategory.getSupplyCategoryName(),
                supplyCategory.getActive());
    }

    public List<SupplyCategoryDto> getAllSupplyCategories(){
        return supplyCategoryRepository
                .getAllSupplyCategories()
                .stream()
                .map((SupplyCategory supplyCategory) -> convertEntityToDto(supplyCategory))
                .collect(Collectors.toList());
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
                supplyCategoryDto.getActive());
    }

    public void updateSupplyCategory(SupplyCategoryDto supplyCategoryDto, Long id) {

        SupplyCategory supplyCategory = supplyCategoryRepository.getSupplyCategoryById(id)
                .orElseThrow(() -> new SupplyCategoryNotFoundException(id));

        String name = supplyCategoryDto.getSupplyCategoryName();
        Boolean active = supplyCategoryDto.getActive();

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

        supplyCategory.setActive(active);
    }
}
