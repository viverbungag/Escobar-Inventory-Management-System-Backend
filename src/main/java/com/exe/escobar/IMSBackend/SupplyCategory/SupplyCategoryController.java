package com.exe.escobar.IMSBackend.SupplyCategory;

import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8888")
@RequestMapping("api/v1/supply-category")
public class SupplyCategoryController {

    @Autowired
    SupplyCategoryService supplyCategoryService;

    @GetMapping
    public Map<String, Object> getAllSupplyCategories(@RequestBody PaginationDto paginationDto){
        return supplyCategoryService.getAllSupplyCategories(paginationDto);
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
