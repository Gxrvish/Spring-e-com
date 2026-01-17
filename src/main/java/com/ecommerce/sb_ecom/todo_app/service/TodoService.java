package com.ecommerce.sb_ecom.todo_app.service;

import java.util.List;

import com.ecommerce.sb_ecom.todo_app.dto.TodoDto;
import com.ecommerce.sb_ecom.todo_app.dto.TodoSearchRequest;
import com.ecommerce.sb_ecom.todo_app.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    List<Todo> getAllTodos(Boolean onlyCompleted);

    Todo createTodo(Todo todo);

    void deleteTodo(Long todoId);

    Todo updateTodo(Long todoId, Todo todo);

    Page<TodoDto> searchTodos(TodoSearchRequest filters, Pageable pageable);
}
