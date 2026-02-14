package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductReq {
    @NotEmpty
    private String name;
    @NotNull
    @Positive
    private Integer price;
    @NotNull
    @Positive
    private Integer stock;
    @NotEmpty
    private String categoryId;
}
