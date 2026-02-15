package com.example.demo.service;

import com.example.demo.dto.LockProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductFilter;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product create(ProductDTO productDTO);

    Product getById(String id);

    List<Product> getAll(Pageable pageable);

    void deleteById(String id);

    void deleteAll();

    List<Product> search(ProductFilter productFilter);

    void lock(LockProductDTO lockProduct);

}
