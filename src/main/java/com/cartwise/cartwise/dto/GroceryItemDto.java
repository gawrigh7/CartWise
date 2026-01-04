package com.cartwise.cartwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItemDto {
    private Long groceryId;
    private String name;
    private boolean available;
}
