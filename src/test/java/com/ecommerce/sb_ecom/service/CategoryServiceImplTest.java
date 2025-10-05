package com.ecommerce.sb_ecom.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.model.Category;

class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    void testCreateCategoryAndGetAll() {
        Category category = new Category();
        category.setCategoryName("Electronics");

        categoryService.createCategory(category);

        List<Category> categories = categoryService.getAllCategories();
        assertEquals(1, categories.size());
        assertEquals("Electronics", categories.get(0).getCategoryName());
        assertNotNull(categories.get(0).getCategoryId());
    }

    @Test
    void testDeleteCategory_Success() {
        Category category = new Category();
        category.setCategoryName("Books");
        categoryService.createCategory(category);

        Long id = categoryService.getAllCategories().get(0).getCategoryId();
        String message = categoryService.deleteCategory(id);

        assertEquals("Category with categoryId: " + id + " deleted successfully", message);
        assertTrue(categoryService.getAllCategories().isEmpty());
    }

    @Test
    void testDeleteCategory_NotFound() {
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> categoryService.deleteCategory(99L)
        );
        assertEquals("404 NOT_FOUND \"Resource not found\"", exception.getMessage());
    }

    @Test
    void testUpdateCategory_Success() {
        Category category = new Category();
        category.setCategoryName("Old Name");
        categoryService.createCategory(category);

        Long id = categoryService.getAllCategories().get(0).getCategoryId();
        Category updated = new Category();
        updated.setCategoryName("New Name");

        Category result = categoryService.updateCategory(id, updated);

        assertEquals("New Name", result.getCategoryName());
        assertEquals(id, result.getCategoryId());
    }

    @Test
    void testUpdateCategory_NotFound() {
        Category updated = new Category();
        updated.setCategoryName("Nonexistent");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> categoryService.updateCategory(123L, updated)
        );
        assertEquals("404 NOT_FOUND \"Resource not found\"", exception.getMessage());
    }
}
