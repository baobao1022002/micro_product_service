package com.example.demo.controller;

import com.example.demo.dto.request.BaseResponse;
import com.example.demo.dto.request.CreateProductReq;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductServie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductServie productService;

    @PostMapping
    public ResponseEntity<BaseResponse<Product>> create(@RequestBody @Valid CreateProductReq createProductReq) {
        return ResponseEntity.ok(new BaseResponse<>(productService.create(createProductReq), "Success"));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Product>> getById(@PathVariable String id) {

        return ResponseEntity.ok(
                new BaseResponse<>(productService.getById(id), "Success")
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<BaseResponse<List<Product>>> getAll(@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
                                                                  Pageable pageable) {

        return ResponseEntity.ok(
                new BaseResponse<>(productService.getAll(pageable), "Success")
        );
    }

    // DELETE BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteById(@PathVariable String id) {

        productService.deleteById(id);

        return ResponseEntity.ok(
                new BaseResponse<>(null, "Deleted successfully")
        );
    }

    // DELETE ALL
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteAll() {

        productService.deleteAll();

        return ResponseEntity.ok(
                new BaseResponse<>(null, "Deleted all products successfully")
        );
    }


}
