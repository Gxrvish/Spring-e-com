package com.ecommerce.sb_ecom.todo_app.specification;

import com.ecommerce.sb_ecom.todo_app.model.Todo;
import org.springframework.data.jpa.domain.Specification;

public class TodoSpecifications {

    // Search in name or description
    public static Specification<Todo> searchKeyword(String keyword) {
        return (root, query, cb) -> {
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("todoName")), pattern),
                    cb.like(cb.lower(root.get("todoDescription")), pattern)
            );
        };
    }

    // Filter by completion status
    public static Specification<Todo> isCompleted(Boolean completed) {
        return (root, query, cb) -> {
            return cb.equal(root.get("isCompleted"), completed);
        };
    }
}