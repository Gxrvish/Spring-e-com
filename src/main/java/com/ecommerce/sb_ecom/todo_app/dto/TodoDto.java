package com.ecommerce.sb_ecom.todo_app.dto;

import java.time.Instant;

import com.ecommerce.sb_ecom.todo_app.model.Todo;

public record TodoDto(
        Long todoId,
        String todoName,
        String todoDescription,
        boolean isCompleted,
        Instant createdAt,
        Instant updatedAt
) {
    public static TodoDto fromEntity(Todo todo) {
        return new TodoDto(
                todo.getTodoId(),
                todo.getTodoName(),
                todo.getTodoDescription(),
                todo.getIsCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
