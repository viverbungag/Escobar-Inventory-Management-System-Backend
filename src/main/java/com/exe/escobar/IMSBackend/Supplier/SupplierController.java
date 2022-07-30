package com.exe.escobar.IMSBackend.Supplier;


import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public Map<String, Object> getAllSuppliers(@RequestBody PaginationDto paginationDto){
        return supplierService.getAllSuppliers(paginationDto);
    }

    @GetMapping("/active")
    public List<SupplierDto> getAllActiveSuppliers(){
        return supplierService.getAllActiveSuppliers();
    }

    @PostMapping("/add")
    public void addSupplier(@RequestBody SupplierDto supplierDto){
        supplierService.addSupplier(supplierDto);
    }

    @PutMapping("/update/{id}")
    public void updateSupplier(@RequestBody SupplierDto supplierDto, @PathVariable Long id){
        supplierService.updateSupplier(supplierDto, id);
    }

}
