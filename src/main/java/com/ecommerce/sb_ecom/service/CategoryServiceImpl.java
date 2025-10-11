package com.ecommerce.sb_ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(category);
        return "Category with ID: " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepository.save(existingCategory);
    }
}
