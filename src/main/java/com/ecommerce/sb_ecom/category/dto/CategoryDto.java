package com.ecommerce.sb_ecom.category.dto;

import com.ecommerce.sb_ecom.category.model.Category;

public record CategoryDto(Long categoryId, String categoryName) {
    public static CategoryDto fromEntity(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getCategoryName());
    }
}
