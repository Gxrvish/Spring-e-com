package com.ecommerce.sb_ecom.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.exceptions.EcomGlobalExceptionHandler;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.service.CategoryService;

class CategoryControllerTest {

        private MockMvc mockMvc;

        @Mock
        private CategoryService categoryService;

        @InjectMocks
        private CategoryController categoryController;

        @BeforeEach
        @SuppressWarnings("unused")
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                                .setControllerAdvice(new EcomGlobalExceptionHandler())
                                .build();
        }

        // ---------------- GET ALL ----------------
        @Test
        void testGetAllCategories_Success() throws Exception {
                Category category = new Category();
                category.setCategoryId(1L);
                category.setCategoryName("Electronics");

                when(categoryService.getAllCategories()).thenReturn(List.of(category));

                mockMvc.perform(MockMvcRequestBuilders.get("/api/public/categories")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(categoryService, times(1)).getAllCategories();
        }

        @Test
        void testGetAllCategories_Empty() throws Exception {
                when(categoryService.getAllCategories()).thenReturn(List.of());

                mockMvc.perform(MockMvcRequestBuilders.get("/api/public/categories")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isInternalServerError());

                verify(categoryService, times(1)).getAllCategories();
        }

        // ---------------- CREATE ----------------
        @Test
        void testCreateCategory_Success() throws Exception {
                Category category = new Category();
                category.setCategoryId(1L);
                category.setCategoryName("Books");

                when(categoryService.createCategory(any(Category.class))).thenReturn(category);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"Books\"}"))
                                .andExpect(status().isCreated());

                verify(categoryService, times(1)).createCategory(any(Category.class));
        }

        @Test
        void testCreateCategory_AlreadyExists() throws Exception {
                when(categoryService.createCategory(any(Category.class)))
                                .thenThrow(new APIException("Category with name Books already exists!"));

                mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"Books\"}"))
                                .andExpect(status().isInternalServerError());

                verify(categoryService, times(1)).createCategory(any(Category.class));
        }

        @Test
        void testCreateCategory_ValidationFail() throws Exception {
                // Empty categoryName triggers @Valid validation error
                mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"\"}"))
                                .andExpect(status().isBadRequest());
        }

        // ---------------- UPDATE ----------------
        @Test
        void testUpdateCategory_Success() throws Exception {
                Category updated = new Category();
                updated.setCategoryId(1L);
                updated.setCategoryName("New Name");

                when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(updated);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"New Name\"}"))
                                .andExpect(status().isOk());

                verify(categoryService, times(1)).updateCategory(eq(1L), any(Category.class));
        }

        @Test
        void testUpdateCategory_NotFound() throws Exception {
                when(categoryService.updateCategory(eq(99L), any(Category.class)))
                                .thenThrow(new ResourceNotFoundException("Category", "categoryId", 99L));

                mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/categories/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"New Name\"}"))
                                .andExpect(status().isNotFound());

                verify(categoryService, times(1)).updateCategory(eq(99L), any(Category.class));
        }

        @Test
        void testUpdateCategory_ValidationFail() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryName\": \"\"}"))
                                .andExpect(status().isBadRequest());
        }

        // ---------------- DELETE ----------------
        @Test
        void testDeleteCategory_Success() throws Exception {
                doNothing().when(categoryService).deleteCategory(1L);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/categories/1"))
                                .andExpect(status().isNoContent());

                verify(categoryService, times(1)).deleteCategory(1L);
        }

        @Test
        void testDeleteCategory_NotFound() throws Exception {
                doThrow(new ResourceNotFoundException("Category", "categoryId", 99L))
                                .when(categoryService).deleteCategory(99L);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/categories/99"))
                                .andExpect(status().isNotFound());

                verify(categoryService, times(1)).deleteCategory(99L);
        }
}
