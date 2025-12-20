package com.ecommerce.sb_ecom.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.sb_ecom.category.model.Category;
import com.ecommerce.sb_ecom.category.repositories.CategoryRepository;
import com.ecommerce.sb_ecom.common.exception.APIException;
import com.ecommerce.sb_ecom.common.exception.ResourceNotFoundException;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found!");
        }
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        Category existing = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existing != null) {
            throw new APIException("Category already exists: " + category.getCategoryName());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        existing.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepository.save(existing);
    }
}
