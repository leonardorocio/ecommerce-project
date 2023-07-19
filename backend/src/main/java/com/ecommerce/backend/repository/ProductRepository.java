package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<List<Product>> findByName(String name);

    @Query("select product from Product product order by product.discount desc")
    Optional<List<Product>> findByDiscountSorted();

    @Query("select product from Product product where product.productCategory.name = ?1")
    Optional<List<Product>> filterProductByCategory(String categoryName);

    @Query("select product from Product product " +
            "left join product.productCategory category " +
            "where product.shortDescription like CONCAT('%', ?1, '%') " +
            "or product.name like CONCAT('%', ?1, '%') or category.name like CONCAT('%', ?1, '%')" +
            "or product.detailedDescription like CONCAT('%', ?1, '%')")
    Optional<List<Product>> searchProduct(String name);
}
