package com.ecommerce.sb_ecom.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.dto.ApiResponse;
import com.ecommerce.sb_ecom.dto.CategoryDto;
import com.ecommerce.sb_ecom.exceptions.NoCategoriesFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories()
                .stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());

        if (categories.isEmpty()) {
            throw new NoCategoriesFoundException();
        }

        return ResponseEntity.ok(new ApiResponse<>("Categories fetched successfully", categories));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody Category category) {
        try {
            Category created = categoryService.createCategory(category);
            return new ResponseEntity<>(
                    new ApiResponse<>("Category created successfully", CategoryDto.fromEntity(created)),
                    HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Category already exists");
        }
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category category) {

        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse<>("Category updated successfully", CategoryDto.fromEntity(updated)));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
