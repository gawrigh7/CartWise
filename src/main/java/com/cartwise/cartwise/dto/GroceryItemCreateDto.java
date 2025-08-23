package com.cartwise.cartwise.dto;

import jakarta.validation.constraints.NotBlank;

public class GroceryItemCreateDto {
    @NotBlank(message = "Name is required")
    private String name;
    private Boolean available; // optional; default handled in controller/service

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}

