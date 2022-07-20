package com.exe.escobar.IMSBackend.MenuCategory.Exceptions;

public class MenuCategoryNotFoundException extends RuntimeException{

    public MenuCategoryNotFoundException(Long id){
        super(String.format("Could not find Menu Category with id: %s", id));
    }
}
