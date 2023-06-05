package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@DisplayName("Tests for Product Repository")
class ProductRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Find by name when successful")
    void findByName_WhenSuccessful() {
        Product productToSave = createMockProduct();
        Product productSaved = productRepository.save(productToSave);

        List<Product> productList = productRepository.findByName(productSaved.getName()).get();

        Assertions.assertNotNull(productList);
        Assertions.assertEquals(productList.contains(productSaved), true);
    }

    @Test
    @DisplayName("find all products sorted by discount when successful")
    void findByDiscountSorted_WhenSuccessful() {
        Product productToSave = createMockProduct();
        Product productSaved = productRepository.save(productToSave);

        List<Product> productList = productRepository.findByDiscountSorted().get();

        Assertions.assertNotNull(productList);
        Assertions.assertEquals(productList.contains(productSaved), true);
    }

    @Test
    @DisplayName("find product based on product category when successful")
    void filterProductByCategory_WhenSuccessful() {
        Product productToSave = createMockProduct();
        Product productSaved = productRepository.save(productToSave);

        List<Product> productList = productRepository.filterProductByCategory(productSaved.getName()).get();

        Assertions.assertNotNull(productList);
        Assertions.assertEquals(productList.contains(productSaved), true);
    }

    @Test
    @DisplayName("Finds all products related to the term when successful")
    void searchProduct_WhenSuccessful() {
        Product productToSave = createMockProduct();
        Product productSaved = productRepository.save(productToSave);

        List<Product> productList = productRepository.searchProduct("Processador").get();

        Assertions.assertNotNull(productList);
        Assertions.assertEquals(productList.contains(productSaved), true);
    }

    public Product createMockProduct() {
        Product product = Product.builder()
                .name("Testing")
                .discount(1.0)
                .productCategory(productCategoryRepository.findById(1).get())
                .price(50)
                .stock(1)
                .description("GATICA BULICAS")
                .build();
        return product;
    }
}