package com.cartwise.cartwise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroceryItemCreateDto {
    @NotBlank(message = "Name is required")
    private String name;
    private boolean available = true;
}
