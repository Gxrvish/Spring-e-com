package com.ecommerce.sb_ecom.todo_app.exception;

public class NoTodosFoundException extends RuntimeException {
    public NoTodosFoundException() {
        super("No categories found");
    }
}
