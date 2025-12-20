package com.ecommerce.sb_ecom.category.exception;

public class NoCategoriesFoundException extends RuntimeException {
    public NoCategoriesFoundException() {
        super("No categories found");
    }
}
