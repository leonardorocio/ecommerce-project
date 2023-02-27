package com.ecommerce.hardware.services;


import com.ecommerce.hardware.mapper.ProductMapper;
import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.repository.ProductRepository;
import com.ecommerce.hardware.request.ProductPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsWithDiscount() {

        return productRepository.findByDiscountSorted().orElseThrow(
                () -> new IllegalArgumentException("No products on the database")
        );
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such product"));
    }

    public Product createProduct(ProductPostRequestBody productPostRequestBody) {
        Product product = productMapper.mapToProduct(productPostRequestBody);
        return productRepository.save(product);
    }

    public Product updateProduct(ProductPostRequestBody productPostRequestBody) {
        Product currentProduct = getProductById(productPostRequestBody.getId());
        Product newProduct = productMapper.mapToProduct(productPostRequestBody);
        newProduct.setProduct_id(currentProduct.getProduct_id());
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Integer id) {
        Product toBeDeleted = getProductById(id);
        productRepository.delete(toBeDeleted);
    }

//    public Product setProductDiscount(Integer id) {
//        Product product = getProductById(id);
//        product.setDiscount();
//    }
}
