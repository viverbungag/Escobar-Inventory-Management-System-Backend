package com.exe.escobar.IMSBackend.UnitOfMeasurement;


import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8888")
@RequestMapping("api/v1/unit-of-measurement")
public class UnitOfMeasurementController {

    @Autowired
    UnitOfMeasurementService unitOfMeasurementService;

    @GetMapping
    public Map<String, Object> getAllUnitOfMeasurement(@RequestBody PaginationDto paginationDto){
        return unitOfMeasurementService.getAllUnitOfMeasurements(paginationDto);
    }

    @PostMapping("/add")
    public void addUnitOfMeasurement(@RequestBody UnitOfMeasurementDto unitOfMeasurementDto){
        unitOfMeasurementService.addUnitOfMeasurement(unitOfMeasurementDto);
    }

    @PutMapping("/update/{id}")
    public void updateUnitOfMeasurement(@RequestBody UnitOfMeasurementDto unitOfMeasurementDto,
                                        @PathVariable Long id){

        unitOfMeasurementService.updateUnitOfMeasurement(unitOfMeasurementDto, id);
    }

}
