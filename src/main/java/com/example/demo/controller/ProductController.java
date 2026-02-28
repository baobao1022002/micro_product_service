package com.example.demo.controller;

import com.example.demo.common.BaseResponse;
import com.example.demo.dto.LockProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductFilter;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
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
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<Product>> create(@RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(new BaseResponse<>(productService.create(productDTO), "Success"));
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

    @PostMapping("/search")
    public ResponseEntity<BaseResponse<List<Product>>> search(@RequestBody ProductFilter productFilter) {
        List<Product> products = productService.search(productFilter);

        return ResponseEntity.ok(
                new BaseResponse<>(products, "Get all products by ids successfully")
        );
    }

    @PostMapping("/lock")
    public ResponseEntity<BaseResponse<Boolean>> lock(@RequestBody LockProductDTO lockProduct) {
        productService.lock(lockProduct);
        return ResponseEntity.ok(
                new BaseResponse<>(true, "Success")
        );
    }


}
