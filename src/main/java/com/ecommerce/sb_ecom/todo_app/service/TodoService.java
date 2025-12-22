package com.ecommerce.sb_ecom.todo_app.service;

import java.util.List;

import com.ecommerce.sb_ecom.todo_app.model.Todo;

public interface TodoService {

    List<Todo> getAllTodos(Boolean onlyCompleted);

    Todo createTodo(Todo todo);

    void deleteTodo(Long todoId);

    Todo updateTodo(Long todoId, Todo todo);
}
