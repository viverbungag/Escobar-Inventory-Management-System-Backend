package com.exe.escobar.IMSBackend.UnitOfMeasurement;


import com.exe.escobar.IMSBackend.MenuCategory.MenuCategoryListDto;
import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/unit-of-measurement")
public class UnitOfMeasurementController {

    @Autowired
    UnitOfMeasurementService unitOfMeasurementService;

    @PostMapping
    public Map<String, Object> getAllUnitOfMeasurement(@RequestBody PaginationDto paginationDto){
        return unitOfMeasurementService.getAllUnitOfMeasurements(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveUnitOfMeasurement(@RequestBody PaginationDto paginationDto){
        return unitOfMeasurementService.getAllActiveUnitOfMeasurements(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveUnitOfMeasurement(@RequestBody PaginationDto paginationDto){
        return unitOfMeasurementService.getAllInactiveUnitOfMeasurements(paginationDto);
    }

    @PostMapping("/activate")
    public void activateUnitOfMeasurement(@RequestBody UnitOfMeasurementListDto unitOfMeasurementListDto){
        unitOfMeasurementService.activateUnitOfMeasurement(unitOfMeasurementListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateUnitOfMeasurement(@RequestBody UnitOfMeasurementListDto unitOfMeasurementListDto){
        unitOfMeasurementService.inactivateUnitOfMeasurement(unitOfMeasurementListDto);
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
