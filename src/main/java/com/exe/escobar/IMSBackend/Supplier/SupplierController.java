package com.exe.escobar.IMSBackend.Supplier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers(){
        return supplierService.getAllSuppliers();
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
