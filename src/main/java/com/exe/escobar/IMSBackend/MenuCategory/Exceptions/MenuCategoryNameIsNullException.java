package com.exe.escobar.IMSBackend.MenuCategory.Exceptions;

public class MenuCategoryNameIsNullException extends RuntimeException{

    public MenuCategoryNameIsNullException(){
        super("Menu Category Name should not be empty");
    }
}
