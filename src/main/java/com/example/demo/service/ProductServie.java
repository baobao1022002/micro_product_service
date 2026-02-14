package com.example.demo.service;

import com.example.demo.dto.request.CreateProductReq;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductServie {
    Product create(CreateProductReq createProductReq);

    Product getById(String id);

    List<Product> getAll(Pageable pageable);

    void deleteById(String id);

    void deleteAll();

}
