package com.ecommerce.sb_ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.repositories.CategoryRepository;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
        categories.add(category);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getCategoryName());
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setCategoryName("Books");

        when(categoryRepository.findByCategoryName("Books")).thenReturn(null);
        when(categoryRepository.save(category)).thenReturn(category);

        Category created = categoryService.createCategory(category);

        assertEquals("Books", created.getCategoryName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory_Success() {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Fashion");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(99L));

        assertEquals("Category not found with categoryId: 99", exception.getMessage());
    }

    @Test
    void testUpdateCategory_Success() {
        Category existingCategory = new Category();
        existingCategory.setCategoryId(1L);
        existingCategory.setCategoryName("Old Name");

        Category updatedCategory = new Category();
        updatedCategory.setCategoryName("New Name");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(1L, updatedCategory);

        assertEquals("New Name", result.getCategoryName());
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateCategory_NotFound() {
        Category updatedCategory = new Category();
        updatedCategory.setCategoryName("Nonexistent");

        when(categoryRepository.findById(123L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.updateCategory(123L, updatedCategory));

        assertEquals("Category not found with categoryId: 123", exception.getMessage());
    }
}
