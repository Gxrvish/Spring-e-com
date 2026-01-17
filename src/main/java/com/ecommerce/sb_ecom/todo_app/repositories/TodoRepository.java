package com.ecommerce.sb_ecom.todo_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.sb_ecom.todo_app.model.Todo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoRepository
        extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
    Todo findByTodoName(String todoName);

    List<Todo> findByIsCompleted(boolean isCompleted);
}
