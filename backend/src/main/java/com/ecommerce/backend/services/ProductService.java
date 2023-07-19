package com.ecommerce.backend.services;


import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsWithDiscount() {

        return productRepository.findByDiscountSorted().orElseThrow(
                () -> new ResourceNotFoundException("Nenhum produto na base de dados")
        );
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não existente"));
    }

    public Product createProduct(ProductPostRequestBody productPostRequestBody) {
        Product product = productMapper.mapToProduct(productPostRequestBody);
        return productRepository.save(product);
    }

    public Product updateProduct(ProductPostRequestBody productPostRequestBody, Integer id) {
        Product currentProduct = getProductById(id);
        Product newProduct = productMapper.mapToProduct(productPostRequestBody);
        newProduct.setId(currentProduct.getId());
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Integer id) {
        Product toBeDeleted = getProductById(id);
        productRepository.delete(toBeDeleted);
    }

    public List<Comment> getCommentsByProduct(Integer id) {
        Product product = this.getProductById(id);
        return product.getComments();
    }

    public List<Product> searchProduct(String name) {
        return productRepository.searchProduct(name).orElseThrow(
                () -> new ResourceNotFoundException("Nenhum produto relacionado a esse nome"));
    }

    public List<Product> filterProductByCategory(ProductCategoryRequestBody productCategoryRequestBody) {
        String categoryName = productCategoryRequestBody.getName();
        return productRepository.filterProductByCategory(categoryName).orElseThrow(
                () -> new ResourceNotFoundException("Nenhum produto pertencente a essa categoria")
        );
    }
}
