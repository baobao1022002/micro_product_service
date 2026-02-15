package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product fromProductRequest(ProductDTO productDTO);


}
