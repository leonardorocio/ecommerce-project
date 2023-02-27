package com.ecommerce.hardware.repository;

import com.ecommerce.hardware.models.Product;
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
}
