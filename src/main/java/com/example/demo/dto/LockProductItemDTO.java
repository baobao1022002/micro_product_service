package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockProductItemDTO {
    @NotEmpty
    private String id;
    @Positive
    @NotNull
    private Integer quantity;
}
