package com.ecommerce.sb_ecom.todo_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.sb_ecom.common.dto.ApiResponse;
import com.ecommerce.sb_ecom.todo_app.dto.TodoDto;
import com.ecommerce.sb_ecom.todo_app.exception.NoTodosFoundException;
import com.ecommerce.sb_ecom.todo_app.model.Todo;
import com.ecommerce.sb_ecom.todo_app.service.TodoService;

import jakarta.validation.Valid;


@RestController
@ControllerAdvice
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/public/todos")
    public ResponseEntity<ApiResponse<List<TodoDto>>> getAllTodos(
            @RequestParam(required = false) Boolean onlyCompleted
        ) {
        List<TodoDto> todos = todoService.getAllTodos(onlyCompleted)
                                            .stream()
                                            .map(TodoDto::fromEntity)
                                            .collect(Collectors.toList());

        if (todos.isEmpty()) {
            throw new NoTodosFoundException();
        }

        return ResponseEntity.ok(new ApiResponse<>("Todos fetch successfully", todos));
    }

    @PostMapping("/admin/todos")
    public ResponseEntity<ApiResponse<TodoDto>> createTodo(@Valid @RequestBody Todo todo) {
        Todo created = todoService.createTodo(todo);
        return new ResponseEntity<>(
                new ApiResponse<>("Todo created successfully", TodoDto.fromEntity(created)),
                HttpStatus.CREATED);
    }

    @PutMapping("/admin/todos/{id}")
    public ResponseEntity<ApiResponse<TodoDto>> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody Todo todo) {

        Todo updated = todoService.updateTodo(id, todo);
        return ResponseEntity.ok(new ApiResponse<>("Todo updated successfully", TodoDto.fromEntity(updated)));
    }

    @DeleteMapping("/admin/todos/{id}")
    public ResponseEntity<ApiResponse<TodoDto>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
