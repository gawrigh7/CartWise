package com.cartwise.cartwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSuggestionDto {
    private Long recipeId;
    private String title;
    private String description;
}
