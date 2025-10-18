package com.ecommerce.sb_ecom.exceptions;

public class NoCategoriesFoundException extends RuntimeException {
    public NoCategoriesFoundException() {
        super("No categories found");
    }
}
