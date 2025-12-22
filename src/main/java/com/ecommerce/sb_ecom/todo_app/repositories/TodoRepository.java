package com.ecommerce.sb_ecom.todo_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.sb_ecom.todo_app.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByTodoName(String todoName);

    List<Todo> findByIsCompleted(boolean isCompleted);
}
