package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateProductReq;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApplicationException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductServie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServie {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product create(CreateProductReq createProductReq) {
        var existedCategoryOptional = categoryRepository.findById(createProductReq.getCategoryId());
        if (existedCategoryOptional.isEmpty()) {
            throw new ApplicationException("Category not found");
        }
        Product creatingProduct = productMapper.fromProductRequest(createProductReq);
        creatingProduct.setIsDeleted(false);
        return productRepository.save(creatingProduct);
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Product not found"));
    }

    @Override
    public List<Product> getAll(Pageable pageable) {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Product existedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Product not found"));

        productRepository.delete(existedProduct);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
