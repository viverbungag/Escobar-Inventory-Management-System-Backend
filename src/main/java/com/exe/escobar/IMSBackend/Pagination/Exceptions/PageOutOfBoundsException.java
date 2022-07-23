package com.exe.escobar.IMSBackend.Pagination.Exceptions;

public class PageOutOfBoundsException extends RuntimeException{

    public PageOutOfBoundsException(Integer currentPageNo){
        super(String.format("You inputted an invalid page number: %s", currentPageNo));
    }
}
