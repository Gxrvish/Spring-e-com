package com.ecommerce.sb_ecom.category.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.sb_ecom.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
