package com.exe.escobar.IMSBackend.Supply;

import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8888")
@RequestMapping("api/v1/supply")
public class SupplyController {

    @Autowired
    SupplyService supplyService;

    @GetMapping
    public Map<String, Object> getSuppliers(@RequestBody PaginationDto paginationDto){
        return supplyService.getAllSupplies(paginationDto);
    }

    @PostMapping("/add")
    public void addSupply(@RequestBody SupplyDto supplyDto){
        supplyService.addSupply(supplyDto);
    }

    @PutMapping("/update/{id}")
    public void updateSupply(@RequestBody SupplyDto supplyDto,
                             @PathVariable Long id){
        supplyService.updateSupply(supplyDto, id);
    }
}
