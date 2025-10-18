package com.ecommerce.sb_ecom.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.service.CategoryService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private CategoryService categoryService;

        @Test
        void testGetAllCategories() throws Exception {
                List<Category> mockCategories = Arrays.asList(
                                createCategory(1L, "Electronics"),
                                createCategory(2L, "Books"));

                when(categoryService.getAllCategories()).thenReturn(mockCategories);

                mockMvc.perform(get("/api/public/categories"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
                                .andExpect(jsonPath("$[1].categoryName").value("Books"));

                verify(categoryService, times(1)).getAllCategories();
        }

        @Test
        void testCreateCategory() throws Exception {
                mockMvc.perform(post("/api/public/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"Fashion\"}"))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Category added successfully!"));

                verify(categoryService, times(1)).createCategory(any(Category.class));
        }

        @Test
        void testDeleteCategory_Success() throws Exception {
                when(categoryService.deleteCategory(1L))
                                .thenReturn("Category with ID: 1 deleted successfully");

                mockMvc.perform(delete("/api/admin/categories/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Category with ID: 1 deleted successfully"));

                verify(categoryService, times(1)).deleteCategory(1L);
        }

        @Test
        void testDeleteCategory_NotFound() throws Exception {
                when(categoryService.deleteCategory(99L))
                                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                                                "Resource not found"));

                mockMvc.perform(delete("/api/admin/categories/99"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Resource not found"));
        }

        @Test
        void testUpdateCategory_Success() throws Exception {
                Category updated = createCategory(1L, "Updated Name");
                when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(updated);

                mockMvc.perform(put("/api/public/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"Updated Name\"}"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Category with categoryId: 1 is updated"));
                verify(categoryService, times(1)).updateCategory(eq(1L), any(Category.class));
        }

        @Test
        void testUpdateCategory_NotFound() throws Exception {
                when(categoryService.updateCategory(eq(5L), any(Category.class)))
                                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                                                "Resource not found"));

                mockMvc.perform(put("/api/public/categories/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"Unknown\"}"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Resource not found"));
        }

        // Utility method to reduce repetition
        private Category createCategory(Long id, String name) {
                Category category = new Category();
                category.setCategoryId(id);
                category.setCategoryName(name);
                return category;
        }
}
