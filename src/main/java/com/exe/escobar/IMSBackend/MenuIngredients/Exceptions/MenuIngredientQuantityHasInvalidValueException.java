package com.exe.escobar.IMSBackend.MenuIngredients.Exceptions;

public class MenuIngredientQuantityHasInvalidValueException extends RuntimeException{

    public MenuIngredientQuantityHasInvalidValueException(String name){
        super(String.format("The %s ingredient has an invalid quantity", name));
    }
}
