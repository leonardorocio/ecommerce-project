package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
