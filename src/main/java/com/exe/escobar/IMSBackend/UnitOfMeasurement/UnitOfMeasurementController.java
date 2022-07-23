package com.exe.escobar.IMSBackend.UnitOfMeasurement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8888")
@RequestMapping("api/v1/unit-of-measurement")
public class UnitOfMeasurementController {

    @Autowired
    UnitOfMeasurementService unitOfMeasurementService;

    @GetMapping
    public List<UnitOfMeasurementDto> getAllUnitOfMeasurement(){
        return unitOfMeasurementService.getAllUnitOfMeasurements();
    }

    @PostMapping("/add")
    public void addUnitOfMeasurement(@RequestBody UnitOfMeasurementDto unitOfMeasurementDto){
        unitOfMeasurementService.addUnitOfMeasurement(unitOfMeasurementDto);
    }

    @PutMapping("/update/{id}")
    public void updateUnitOfMeasurement(@RequestBody UnitOfMeasurementDto unitOfMeasurementDto, @PathVariable Long id){
        unitOfMeasurementService.updateUnitOfMeasurement(unitOfMeasurementDto, id);
    }

}
