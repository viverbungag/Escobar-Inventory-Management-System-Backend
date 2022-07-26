package com.exe.escobar.IMSBackend.Menu;


import com.exe.escobar.IMSBackend.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8888")
@RequestMapping("api/v1/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping
    public Map<String, Object> getAllMenu(@RequestBody PaginationDto paginationDto){
        return menuService.getAllMenu(paginationDto);
    }

    @PostMapping("/add")
    public void menuSupply(@RequestBody MenuDto menuDto){
        menuService.addMenu(menuDto);
    }

    @PutMapping("/update/{id}")
    public void updateMenu(@RequestBody MenuDto menuDto,
                           @PathVariable Long id){
        menuService.updateMenu(menuDto, id);
    }
}
