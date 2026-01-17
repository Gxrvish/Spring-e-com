package com.ecommerce.sb_ecom.todo_app.service;

import java.time.Instant;
import java.util.List;

import com.ecommerce.sb_ecom.todo_app.dto.TodoDto;
import com.ecommerce.sb_ecom.todo_app.dto.TodoSearchRequest;
import com.ecommerce.sb_ecom.todo_app.specification.TodoSpecifications;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.sb_ecom.common.exception.APIException;
import com.ecommerce.sb_ecom.common.exception.ResourceNotFoundException;
import com.ecommerce.sb_ecom.todo_app.model.Todo;
import com.ecommerce.sb_ecom.todo_app.repositories.TodoRepository;


@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> getAllTodos(Boolean onlyCompleted) {
        List<Todo> todos;

        if (onlyCompleted == null) {
            todos = todoRepository.findAll();
        } else {
            todos = todoRepository.findByIsCompleted(onlyCompleted);
        }

        if (todos.isEmpty()) {
            throw new APIException("No todos found!");
        }

        return todos;
    }

    @Transactional
    @Override
    public Todo createTodo(Todo todo) {
        try {
            return todoRepository.save(todo);
        } catch (DataIntegrityViolationException e) {
            throw new APIException("Todo already exists: " + todo.getTodoName());
        }
    }

    @Transactional
    @Override
    public Todo updateTodo(Long todoId, Todo updatedTodo) {
        Todo existing = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "todoId", todoId));
        existing.setTodoName(updatedTodo.getTodoName());
        existing.setTodoDescription(updatedTodo.getTodoDescription());
        existing.setCompleted(updatedTodo.getIsCompleted());
        existing.setUpdatedAt(Instant.now());
        return todoRepository.save(existing);
    }

    @Transactional
    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "todoId", todoId));
        todoRepository.delete(todo);
    }

    @Override
    public Page<TodoDto> searchTodos(TodoSearchRequest filters, Pageable pageable) {
        // Start with empty specification
        Specification<Todo> spec =
                (root, query, cb) -> cb.conjunction();

        // Add filters one by one
        if (filters.getKeyword() != null && !filters.getKeyword().isBlank()) {
            spec = spec.and(TodoSpecifications.searchKeyword(filters.getKeyword()));
        }

        if (filters.getIsCompleted() != null) {
            spec = spec.and(TodoSpecifications.isCompleted(filters.getIsCompleted()));
        }

        // Execute query with pagination
        Page<Todo> todos = todoRepository.findAll(spec, pageable);

        // Convert to DTOs
        return todos.map(TodoDto::fromEntity);
    }
}
