package com.ecommerce.sb_ecom.service;

import java.util.List;

import com.ecommerce.sb_ecom.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category updatedCategory);
}
