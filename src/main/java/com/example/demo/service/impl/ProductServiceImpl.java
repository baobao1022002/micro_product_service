package com.example.demo.service.impl;

import com.example.demo.dto.LockProductDTO;
import com.example.demo.dto.LockProductItemDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductFilter;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApplicationException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public Product create(ProductDTO productDTO) {
        var existedCategoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (existedCategoryOptional.isEmpty()) {
            throw new ApplicationException("Category not found");
        }
        Product creatingProduct = productMapper.fromProductRequest(productDTO);
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

    @Override
    public List<Product> search(ProductFilter productFilter) {
        return productRepository.findByIdIn(productFilter.getIds());
    }

    @Override
    @Transactional
    public void lock(LockProductDTO lockProduct) {
        List<LockProductItemDTO> items = lockProduct.getItems();

        var productQuantityMap = items.stream().collect(
                (Collectors.toMap(LockProductItemDTO::getId, LockProductItemDTO::getQuantity)));

        List<Product> products = productRepository.findByIdInForUpdate(new ArrayList<>(productQuantityMap.keySet()));

        if (products.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        products.forEach(product -> {
            product.setStock(product.getStock() - productQuantityMap.get(product.getId()));
        });

        productRepository.saveAll(products);
    }
}
