package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.BrandMapper;
import com.ecommerce.backend.models.Brand;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.BrandRequestBody;
import com.ecommerce.backend.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));
    }

    public List<Product> getBrandProducts(int id) {
        Brand brand = this.getBrandById(id);
        return brand.getProductList();
    }

    public Brand createBrand(BrandRequestBody brandRequestBody) {
        Brand brand = brandMapper.mapToBrand(brandRequestBody);
        return brandRepository.save(brand);
    }

    public Brand updateBrand(BrandRequestBody brandRequestBody, int id) {
        Brand newBrand = brandMapper.mapToBrand(brandRequestBody);
        Brand currentBrand = this.getBrandById(id);
        newBrand.setId(currentBrand.getId());
        return brandRepository.save(newBrand);
    }

    public void deleteBrand(int id) {
        Brand brandToDelete = this.getBrandById(id);
        brandRepository.delete(brandToDelete);
    }
}
