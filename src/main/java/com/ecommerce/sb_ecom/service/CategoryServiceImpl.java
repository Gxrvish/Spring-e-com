package com.ecommerce.sb_ecom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.sb_ecom.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
            .filter(c -> c.getCategoryId().equals(categoryId))
            .findFirst()
            .orElse(null);
        if (category != null) {
            categories.remove(category);
            return "Category with categoryId: " + categoryId + " deleted successfully";
        }
        return "Deleting categoryId: " + categoryId + " Failed.";
    }

}
