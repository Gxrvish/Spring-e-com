package com.ecommerce.sb_ecom.todo_app.dto;

import lombok.Data;

@Data
public class TodoSearchRequest {
    private String keyword;
    private String status;
    private String priority;
    private Boolean isCompleted;

    public boolean hasFilters() {
        return keyword != null
                || status != null
                || priority != null
                || isCompleted != null;
    }
}