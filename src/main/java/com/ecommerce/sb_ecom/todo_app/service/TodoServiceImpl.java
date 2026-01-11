package com.ecommerce.sb_ecom.todo_app.service;

import java.time.Instant;
import java.util.List;

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
        Todo existing = todoRepository.findByTodoName(todo.getTodoName());
        if (existing != null) {
            throw new APIException("Todo already exists: " + todo.getTodoName());
        }
        return todoRepository.save(todo);
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
}
