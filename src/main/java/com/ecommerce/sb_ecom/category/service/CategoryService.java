package com.ecommerce.sb_ecom.category.service;

import java.util.List;

import com.ecommerce.sb_ecom.category.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    Category createCategory(Category category);

    void deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category updatedCategory);
}
