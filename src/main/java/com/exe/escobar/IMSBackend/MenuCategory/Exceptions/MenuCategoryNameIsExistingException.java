package com.exe.escobar.IMSBackend.MenuCategory.Exceptions;

public class MenuCategoryNameIsExistingException extends RuntimeException{

    public MenuCategoryNameIsExistingException(String name){
        super(String.format("This Name %s is already existing", name));
    }
}
