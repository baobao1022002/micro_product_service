package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LockProductItemDTO {
    @NotEmpty
    private String id;
    @Positive
    @NotNull
    private Integer quantity;
}
