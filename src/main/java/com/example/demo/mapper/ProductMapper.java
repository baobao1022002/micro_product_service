package com.example.demo.mapper;

import com.example.demo.dto.request.CreateProductReq;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product fromProductRequest(CreateProductReq createProductReq);


}
