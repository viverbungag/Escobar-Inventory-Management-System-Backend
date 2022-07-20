package com.exe.escobar.IMSBackend.SupplyCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/supply-category")
public class SupplyCategoryController {

    @Autowired
    SupplyCategoryService supplyCategoryService;

    @GetMapping
    public List<SupplyCategoryDto> getAllSupplyCategories(){
        return supplyCategoryService.getAllSupplyCategories();
    }

    @PostMapping("/add")
    public void addSupplyCategory(@RequestBody SupplyCategoryDto supplyCategoryDto){
        supplyCategoryService.addSupplyCategory(supplyCategoryDto);
    }

    @PutMapping("/update/{id}")
    public void updateSupplyCategory(@RequestBody SupplyCategoryDto supplyCategoryDto, @PathVariable Long id){
        supplyCategoryService.updateSupplyCategory(supplyCategoryDto, id);
    }


}
