package com.exe.escobar.IMSBackend.Supplier;


import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public List<String> getAllActiveSupplyCategoryNames(){
        return supplierService.getAllActiveSupplierNames();
    }

    @PostMapping
    public Map<String, Object> getAllSuppliers(@RequestBody PaginationDto paginationDto){
        return supplierService.getAllSuppliers(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveSuppliers(@RequestBody PaginationDto paginationDto){
        return supplierService.getAllActiveSuppliers(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveSuppliers(@RequestBody PaginationDto paginationDto){
        return supplierService.getAllInactiveSuppliers(paginationDto);
    }

    @PostMapping("/activate")
    public void activateSupplier(@RequestBody SupplierListDto supplierListDto){
        supplierService.activateSupplier(supplierListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateSupplier(@RequestBody SupplierListDto supplierListDto){
        supplierService.inactivateSupplier(supplierListDto);
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
